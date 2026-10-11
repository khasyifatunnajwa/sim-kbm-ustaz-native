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
import androidx.compose.material.icons.filled.Schedule
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
import com.bustanululum.simkbmnative.data.local.JadwalEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium
import kotlinx.coroutines.launch
import org.json.JSONObject

private val hariList = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu")

@Composable
fun JadwalScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val jadwalList by db.jadwalDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

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
                Text("Jadwal Mengajar", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Jadwal mengajar per kelas", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${jadwalList.size} jadwal", fontSize = 14.sp, color = SlateMedium)
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

            if (jadwalList.isEmpty()) {
                EmptyState("Belum ada jadwal mengajar.")
            }

            hariList.forEach { hari ->
                val jadwalHari = jadwalList.filter { it.hari == hari }
                if (jadwalHari.isNotEmpty()) {
                    Text(hari, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary, modifier = Modifier.padding(vertical = 6.dp))
                    jadwalHari.forEach { jadwal ->
                        val kelas = kelasList.firstOrNull { it.localId == jadwal.kelasLocalId }
                        Card(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
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
                                        Icon(Icons.Filled.Schedule, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("${jadwal.jamMulai} - ${jadwal.jamSelesai}", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                                        Text("Kelas: ${kelas?.nama ?: "Tidak diketahui"}", fontSize = 13.sp, color = SlateMedium)
                                        Text("Mapel: ${jadwal.mataPelajaran}", fontSize = 13.sp, color = SlateMedium)
                                    }
                                    TextButton(onClick = {
                                        scope.launch {
                                            try {
                                                db.withTransaction {
                                                    db.syncQueueDao().enqueue(
                                                        SyncQueueEntity(
                                                            entityType = "jadwal",
                                                            localId = jadwal.localId,
                                                            operation = "DELETE",
                                                            payloadJson = JSONObject()
                                                                .put("localId", jadwal.localId)
                                                                .put("serverId", jadwal.serverId)
                                                                .toString()
                                                        )
                                                    )
                                                    db.jadwalDao().delete(jadwal)
                                                }
                                            } catch (e: Exception) {
                                                pesan = "Gagal menghapus: ${e.message}"
                                            }
                                        }
                                    }) {
                                        Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (tampilDialog) {
        DialogTambahJadwal(
            kelasList = kelasList.map { it.localId to it.nama },
            onTutup = { tampilDialog = false },
            onSimpan = { kelasId, hari, jamMulai, jamSelesai, mapel ->
                scope.launch {
                    try {
                        val jadwal = JadwalEntity(
                            kelasLocalId = kelasId,
                            hari = hari,
                            jamMulai = jamMulai,
                            jamSelesai = jamSelesai,
                            mataPelajaran = mapel
                        )
                        db.withTransaction {
                            db.jadwalDao().upsert(jadwal)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "jadwal",
                                    localId = jadwal.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", jadwal.localId)
                                        .put("kelasLocalId", jadwal.kelasLocalId)
                                        .put("hari", jadwal.hari)
                                        .put("jamMulai", jadwal.jamMulai)
                                        .put("jamSelesai", jadwal.jamSelesai)
                                        .put("mataPelajaran", jadwal.mataPelajaran)
                                        .put("updatedAt", jadwal.updatedAt)
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
private fun DialogTambahJadwal(
    kelasList: List<Pair<String, String>>,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, String) -> Unit
) {
    var kelasId by remember { mutableStateOf<String?>(null) }
    var hari by remember { mutableStateOf("Senin") }
    var jamMulai by remember { mutableStateOf("") }
    var jamSelesai by remember { mutableStateOf("") }
    var mapel by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Jadwal", fontWeight = FontWeight.Bold) },
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
                Text("Hari:", fontSize = 13.sp, color = SlateMedium)
                hariList.forEach { h ->
                    TextButton(onClick = { hari = h }) {
                        if (hari == h) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(h, color = if (hari == h) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = jamMulai, onValueChange = { jamMulai = it }, label = { Text("Jam mulai (HH:mm)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = jamSelesai, onValueChange = { jamSelesai = it }, label = { Text("Jam selesai (HH:mm)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = mapel, onValueChange = { mapel = it }, label = { Text("Mata pelajaran") }, singleLine = true, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = kelasId != null && jamMulai.isNotBlank() && jamSelesai.isNotBlank() && mapel.isNotBlank(),
                onClick = { onSimpan(kelasId!!, hari, jamMulai.trim(), jamSelesai.trim(), mapel.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
