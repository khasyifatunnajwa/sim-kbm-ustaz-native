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
import androidx.compose.material.icons.filled.Tune
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
import com.bustanululum.simkbmnative.data.local.SikapEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

private val kategoriSikap = listOf("Spiritual", "Sosial", "Disiplin", "Tanggung Jawab")
private val penilaianSikap = listOf("Sangat Baik", "Baik", "Cukup", "Perlu Bimbingan")

@Composable
fun SikapScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val semuaMurid by db.muridDao().observeAll()
        .collectAsState(initial = emptyList())
    val sikapList by db.sikapDao().observeAll()
        .collectAsState(initial = emptyList())
    var kelasTerpilih by remember { mutableStateOf<String?>(null) }
    val scope = rememberCoroutineScope()
    val tanggalHariIni = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())

    val kelasAktif = kelasList.firstOrNull { it.localId == kelasTerpilih }
    val muridKelas = if (kelasAktif != null) {
        semuaMurid.filter { it.kelasLocalId == kelasAktif.localId }
    } else emptyList()

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
                Text("Sikap", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Penilaian sikap dan karakter murid", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pilih kelas:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
            Spacer(Modifier.height(8.dp))
            kelasList.forEach { kelas ->
                val selected = kelasTerpilih == kelas.localId
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (selected) MintSurface else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    onClick = { kelasTerpilih = kelas.localId }
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(MintSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(MenuIcons.kelas, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(kelas.nama, fontWeight = FontWeight.SemiBold, color = SlateDark, fontSize = 15.sp)
                            Text(kelas.lembaga, fontSize = 12.sp, color = SlateMedium)
                        }
                        if (selected) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (kelasAktif != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${muridKelas.size} murid", fontSize = 14.sp, color = SlateMedium)
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

                muridKelas.forEach { murid ->
                    val sikapMurid = sikapList.filter { it.muridLocalId == murid.localId }
                    if (sikapMurid.isNotEmpty()) {
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
                                        Icon(Icons.Filled.Tune, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Text(murid.nama, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                                }
                                sikapMurid.forEach { sikap ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("${sikap.kategori} - ${sikap.penilaian}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                                            Text(sikap.deskripsi, fontSize = 13.sp, color = SlateMedium)
                                            Text("Tanggal: ${sikap.tanggal}", fontSize = 12.sp, color = SlateMedium)
                                        }
                                        TextButton(onClick = {
                                            scope.launch {
                                                try {
                                                    db.withTransaction {
                                                        db.syncQueueDao().enqueue(
                                                            SyncQueueEntity(
                                                                entityType = "sikap",
                                                                localId = sikap.localId,
                                                                operation = "DELETE",
                                                                payloadJson = JSONObject()
                                                                    .put("localId", sikap.localId)
                                                                    .put("serverId", sikap.serverId)
                                                                    .toString()
                                                            )
                                                        )
                                                        db.sikapDao().delete(sikap)
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
            } else {
                EmptyState("Pilih kelas untuk melihat penilaian sikap.")
            }
        }
    }

    if (tampilDialog && kelasAktif != null) {
        DialogTambahSikap(
            muridList = muridKelas.map { it.localId to it.nama },
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { muridId, tanggal, kategori, deskripsi, penilaian ->
                scope.launch {
                    try {
                        val sikap = SikapEntity(
                            muridLocalId = muridId,
                            tanggal = tanggal,
                            kategori = kategori,
                            deskripsi = deskripsi,
                            penilaian = penilaian
                        )
                        db.withTransaction {
                            db.sikapDao().upsert(sikap)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "sikap",
                                    localId = sikap.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", sikap.localId)
                                        .put("muridLocalId", sikap.muridLocalId)
                                        .put("tanggal", sikap.tanggal)
                                        .put("kategori", sikap.kategori)
                                        .put("deskripsi", sikap.deskripsi)
                                        .put("penilaian", sikap.penilaian)
                                        .put("updatedAt", sikap.updatedAt)
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
private fun DialogTambahSikap(
    muridList: List<Pair<String, String>>,
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, String) -> Unit
) {
    var muridId by remember { mutableStateOf<String?>(null) }
    var tanggal by remember { mutableStateOf(tanggalDefault) }
    var kategori by remember { mutableStateOf("Spiritual") }
    var deskripsi by remember { mutableStateOf("") }
    var penilaian by remember { mutableStateOf("Baik") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Penilaian Sikap", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Pilih murid:", fontSize = 13.sp, color = SlateMedium)
                muridList.forEach { (id, nama) ->
                    TextButton(onClick = { muridId = id }) {
                        if (muridId == id) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(nama, color = if (muridId == id) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tanggal, onValueChange = { tanggal = it }, label = { Text("Tanggal YYYY-MM-DD") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                Text("Kategori:", fontSize = 13.sp, color = SlateMedium)
                kategoriSikap.forEach { k ->
                    TextButton(onClick = { kategori = k }) {
                        if (kategori == k) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(k, color = if (kategori == k) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = deskripsi, onValueChange = { deskripsi = it }, label = { Text("Deskripsi") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                Text("Penilaian:", fontSize = 13.sp, color = SlateMedium)
                penilaianSikap.forEach { p ->
                    TextButton(onClick = { penilaian = p }) {
                        if (penilaian == p) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(p, color = if (penilaian == p) EmeraldPrimary else SlateDark)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = muridId != null && tanggal.isNotBlank() && deskripsi.isNotBlank(),
                onClick = { onSimpan(muridId!!, tanggal.trim(), kategori, deskripsi.trim(), penilaian) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
