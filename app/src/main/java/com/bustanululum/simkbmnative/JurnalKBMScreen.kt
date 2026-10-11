package com.bustanululum.simkbmnative

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.withTransaction
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.JurnalKBMEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun JurnalKBMScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val jurnalList by db.jurnalKBMDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val tanggalHariIni = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(GradientHeader)
                .padding(24.dp)
        ) {
            Column {
                Text("Jurnal KBM", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Catatan kegiatan belajar mengajar", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${jurnalList.size} jurnal", fontSize = 14.sp, color = SlateMedium)
                Button(
                    onClick = { tampilDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Tambah")
                }
            }

            Spacer(Modifier.height(16.dp))

            if (jurnalList.isEmpty()) {
                EmptyState("Belum ada jurnal KBM.")
            }

            jurnalList.forEach { jurnal ->
                val kelas = kelasList.firstOrNull { it.localId == jurnal.kelasLocalId }
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(MintSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.MenuBook, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(jurnal.tanggal, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text("Kelas: ${kelas?.nama ?: "Tidak diketahui"}", fontSize = 13.sp, color = SlateMedium)
                        Text("Materi: ${jurnal.materi}", fontSize = 13.sp, color = SlateDark)
                        Text("Kegiatan: ${jurnal.kegiatan}", fontSize = 13.sp, color = SlateDark)
                        jurnal.kendala?.let { Text("Kendala: $it", fontSize = 13.sp, color = SlateMedium) }
                        Text("Status: ${jurnal.syncStatus}", fontSize = 11.sp, color = SlateLight)
                        TextButton(onClick = {
                            scope.launch {
                                try {
                                    db.withTransaction {
                                        db.syncQueueDao().enqueue(
                                            SyncQueueEntity(
                                                entityType = "jurnal_kbm",
                                                localId = jurnal.localId,
                                                operation = "DELETE",
                                                payloadJson = JSONObject()
                                                    .put("localId", jurnal.localId)
                                                    .put("serverId", jurnal.serverId)
                                                    .toString()
                                            )
                                        )
                                        db.jurnalKBMDao().delete(jurnal)
                                    }
                                } catch (e: Exception) {
                                    pesan = "Gagal menghapus: ${e.message}"
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Hapus", color = Color(0xFFDC2626))
                        }
                    }
                }
            }
        }
    }

    if (tampilDialog) {
        DialogTambahJurnal(
            kelasList = kelasList.map { it.localId to it.nama },
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { kelasId, tanggal, materi, kegiatan, kendala ->
                scope.launch {
                    try {
                        val jurnal = JurnalKBMEntity(
                            kelasLocalId = kelasId,
                            tanggal = tanggal,
                            materi = materi,
                            kegiatan = kegiatan,
                            kendala = kendala.ifBlank { null }
                        )
                        db.withTransaction {
                            db.jurnalKBMDao().upsert(jurnal)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "jurnal_kbm",
                                    localId = jurnal.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", jurnal.localId)
                                        .put("kelasLocalId", jurnal.kelasLocalId)
                                        .put("tanggal", jurnal.tanggal)
                                        .put("materi", jurnal.materi)
                                        .put("kegiatan", jurnal.kegiatan)
                                        .put("kendala", jurnal.kendala)
                                        .put("updatedAt", jurnal.updatedAt)
                                        .toString()
                                )
                            )
                        }
                        tampilDialog = false
                    } catch (e: Exception) {
                        pesan = "Gagal menyimpan: ${e.message}"
                    }
                }
            }
        )
    }

    pesan?.let { teks ->
        AlertDialog(
            onDismissRequest = { pesan = null },
            title = { Text("Informasi") },
            text = { Text(teks) },
            confirmButton = { TextButton(onClick = { pesan = null }) { Text("Tutup") } }
        )
    }
}

@Composable
private fun DialogTambahJurnal(
    kelasList: List<Pair<String, String>>,
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, String) -> Unit
) {
    var kelasId by remember { mutableStateOf<String?>(null) }
    var tanggal by remember { mutableStateOf(tanggalDefault) }
    var materi by remember { mutableStateOf("") }
    var kegiatan by remember { mutableStateOf("") }
    var kendala by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Jurnal KBM", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Pilih kelas:", fontSize = 13.sp, color = SlateMedium)
                kelasList.forEach { (id, nama) ->
                    TextButton(onClick = { kelasId = id }) {
                        if (kelasId == id) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(nama, color = if (kelasId == id) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tanggal, onValueChange = { tanggal = it }, label = { Text("Tanggal YYYY-MM-DD") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = materi, onValueChange = { materi = it }, label = { Text("Materi") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = kegiatan, onValueChange = { kegiatan = it }, label = { Text("Kegiatan") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = kendala, onValueChange = { kendala = it }, label = { Text("Kendala (opsional)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = kelasId != null && tanggal.isNotBlank() && materi.isNotBlank() && kegiatan.isNotBlank(),
                onClick = { onSimpan(kelasId!!, tanggal.trim(), materi.trim(), kegiatan.trim(), kendala.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
