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
import com.bustanululum.simkbmnative.data.local.NilaiEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

private val jenisNilaiList = listOf("Tugas", "Ulangan", "UTS", "UAS", "Harian")

@Composable
fun NilaiScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val semuaMurid by db.muridDao().observeAll()
        .collectAsState(initial = emptyList())
    val nilaiList by db.nilaiDao().observeAll()
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
        Text("Nilai", style = MaterialTheme.typography.headlineSmall)
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
                    Text("+ Tambah Nilai")
                }
            }

            Spacer(Modifier.height(12.dp))

            muridKelas.forEach { murid ->
                val nilaiMurid = nilaiList.filter { it.muridLocalId == murid.localId }
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
                        if (nilaiMurid.isEmpty()) {
                            Text("Belum ada nilai.")
                        } else {
                            nilaiMurid.forEach { nilai ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column {
                                        Text("${nilai.mataPelajaran} - ${nilai.jenisNilai}")
                                        Text(
                                            "Tanggal: ${nilai.tanggal}",
                                            style = MaterialTheme.typography.bodySmall
                                        )
                                        nilai.keterangan?.let { Text(it) }
                                    }
                                    Text(
                                        "${nilai.nilai}",
                                        style = MaterialTheme.typography.headlineSmall,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                    TextButton(onClick = {
                                        scope.launch {
                                            try {
                                                db.withTransaction {
                                                    db.syncQueueDao().enqueue(
                                                        SyncQueueEntity(
                                                            entityType = "nilai",
                                                            localId = nilai.localId,
                                                            operation = "DELETE",
                                                            payloadJson = JSONObject()
                                                                .put("localId", nilai.localId)
                                                                .put("serverId", nilai.serverId)
                                                                .toString()
                                                        )
                                                    )
                                                    db.nilaiDao().delete(nilai)
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
            Text("Pilih kelas untuk melihat nilai murid.")
        }
    }

    if (tampilDialog && kelasAktif != null) {
        DialogTambahNilai(
            muridList = muridKelas.map { it.localId to it.nama },
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { muridId, mapel, jenis, nilaiVal, tanggal, ket ->
                scope.launch {
                    try {
                        val nilai = NilaiEntity(
                            muridLocalId = muridId,
                            mataPelajaran = mapel,
                            jenisNilai = jenis,
                            nilai = nilaiVal,
                            tanggal = tanggal,
                            keterangan = ket.ifBlank { null }
                        )
                        db.withTransaction {
                            db.nilaiDao().upsert(nilai)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "nilai",
                                    localId = nilai.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", nilai.localId)
                                        .put("muridLocalId", nilai.muridLocalId)
                                        .put("mataPelajaran", nilai.mataPelajaran)
                                        .put("jenisNilai", nilai.jenisNilai)
                                        .put("nilai", nilai.nilai)
                                        .put("tanggal", nilai.tanggal)
                                        .put("keterangan", nilai.keterangan)
                                        .put("updatedAt", nilai.updatedAt)
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
private fun DialogTambahNilai(
    muridList: List<Pair<String, String>>,
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, Double, String, String) -> Unit
) {
    var muridId by remember { mutableStateOf<String?>(null) }
    var mapel by remember { mutableStateOf("") }
    var jenis by remember { mutableStateOf("Tugas") }
    var nilaiStr by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf(tanggalDefault) }
    var ket by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Nilai") },
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
                    value = mapel,
                    onValueChange = { mapel = it },
                    label = { Text("Mata pelajaran") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text("Jenis nilai:")
                jenisNilaiList.forEach { j ->
                    TextButton(onClick = { jenis = j }) {
                        Text(if (jenis == j) "✓ $j" else j)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = nilaiStr,
                    onValueChange = { nilaiStr = it.filter { c -> c.isDigit() || c == '.' } },
                    label = { Text("Nilai (0-100)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tanggal,
                    onValueChange = { tanggal = it },
                    label = { Text("Tanggal YYYY-MM-DD") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = ket,
                    onValueChange = { ket = it },
                    label = { Text("Keterangan (opsional)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = muridId != null &&
                    mapel.isNotBlank() &&
                    nilaiStr.isNotBlank() &&
                    tanggal.isNotBlank(),
                onClick = {
                    val nilaiVal = nilaiStr.toDoubleOrNull() ?: 0.0
                    onSimpan(muridId!!, mapel.trim(), jenis, nilaiVal, tanggal.trim(), ket.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
