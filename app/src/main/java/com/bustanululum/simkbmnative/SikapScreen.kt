package com.bustanululum.simkbmnative

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.room.withTransaction
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.SikapEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
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
            .padding(16.dp)
    ) {
        Text("Sikap", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Text("Pilih kelas:")
        kelasList.forEach { kelas ->
            TextButton(onClick = { kelasTerpilih = kelas.localId }) {
                Text(
                    if (kelasTerpilih == kelas.localId) "✓ ${kelas.nama}" else kelas.nama
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        if (kelasAktif != null) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Murid: ${muridKelas.size}")
                Button(onClick = { tampilDialog = true }) {
                    Text("+ Tambah Penilaian")
                }
            }

            Spacer(Modifier.height(12.dp))

            muridKelas.forEach { murid ->
                val sikapMurid = sikapList.filter { it.muridLocalId == murid.localId }
                if (sikapMurid.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 5.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                murid.nama,
                                style = MaterialTheme.typography.titleMedium
                            )
                            sikapMurid.forEach { sikap ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text("${sikap.kategori} - ${sikap.penilaian}")
                                        Text(sikap.deskripsi)
                                        Text(
                                            "Tanggal: ${sikap.tanggal}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
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
                                        Text("Hapus", color = MaterialTheme.colorScheme.error)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            Text("Pilih kelas untuk melihat penilaian sikap.")
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
            confirmButton = {
                TextButton(onClick = { pesan = null }) { Text("Tutup") }
            }
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
        title = { Text("Tambah Penilaian Sikap") },
        text = {
            Column {
                Text("Pilih murid:")
                muridList.forEach { (id, nama) ->
                    TextButton(onClick = { muridId = id }) {
                        Text(if (muridId == id) "✓ $nama" else nama)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tanggal,
                    onValueChange = { tanggal = it },
                    label = { Text("Tanggal YYYY-MM-DD") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text("Kategori:")
                kategoriSikap.forEach { k ->
                    TextButton(onClick = { kategori = k }) {
                        Text(if (kategori == k) "✓ $k" else k)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = deskripsi,
                    onValueChange = { deskripsi = it },
                    label = { Text("Deskripsi") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text("Penilaian:")
                penilaianSikap.forEach { p ->
                    TextButton(onClick = { penilaian = p }) {
                        Text(if (penilaian == p) "✓ $p" else p)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = muridId != null &&
                    tanggal.isNotBlank() &&
                    deskripsi.isNotBlank(),
                onClick = {
                    onSimpan(muridId!!, tanggal.trim(), kategori, deskripsi.trim(), penilaian)
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
