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
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.data.local.TahunAjaranEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

private val semesterList = listOf("Ganjil", "Genap")

@Composable
fun TahunAjaranScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val tahunList by db.tahunAjaranDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Tahun Ajaran & Semester", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${tahunList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (tahunList.isEmpty()) {
            Text("Belum ada tahun ajaran.")
        }

        tahunList.forEach { tahun ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "${tahun.namaTahunAjaran} - ${tahun.semester}",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Mulai: ${tahun.tanggalMulai}")
                    tahun.tanggalSelesai?.let { Text("Selesai: $it") }
                    Text(if (tahun.aktif) "Status: Aktif" else "Status: Tidak aktif")
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                db.withTransaction {
                                    db.syncQueueDao().enqueue(
                                        SyncQueueEntity(
                                            entityType = "tahun_ajaran",
                                            localId = tahun.localId,
                                            operation = "DELETE",
                                            payloadJson = JSONObject()
                                                .put("localId", tahun.localId)
                                                .put("serverId", tahun.serverId)
                                                .toString()
                                        )
                                    )
                                    db.tahunAjaranDao().delete(tahun)
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

    if (tampilDialog) {
        DialogTambahTahunAjaran(
            onTutup = { tampilDialog = false },
            onSimpan = { nama, semester, mulai, selesai, aktif ->
                scope.launch {
                    try {
                        val tahun = TahunAjaranEntity(
                            namaTahunAjaran = nama,
                            semester = semester,
                            tanggalMulai = mulai,
                            tanggalSelesai = selesai.ifBlank { null },
                            aktif = aktif
                        )
                        db.withTransaction {
                            db.tahunAjaranDao().upsert(tahun)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "tahun_ajaran",
                                    localId = tahun.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", tahun.localId)
                                        .put("namaTahunAjaran", tahun.namaTahunAjaran)
                                        .put("semester", tahun.semester)
                                        .put("tanggalMulai", tahun.tanggalMulai)
                                        .put("tanggalSelesai", tahun.tanggalSelesai)
                                        .put("aktif", tahun.aktif)
                                        .put("updatedAt", tahun.updatedAt)
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
private fun DialogTambahTahunAjaran(
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, Boolean) -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var semester by remember { mutableStateOf("Ganjil") }
    var mulai by remember { mutableStateOf("") }
    var selesai by remember { mutableStateOf("") }
    var aktif by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Tahun Ajaran") },
        text = {
            Column {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama tahun ajaran (cth: 2025/2026)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                Text("Semester:")
                semesterList.forEach { s ->
                    TextButton(onClick = { semester = s }) {
                        Text(if (semester == s) "✓ $s" else s)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = mulai,
                    onValueChange = { mulai = it },
                    label = { Text("Tanggal mulai YYYY-MM-DD") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = selesai,
                    onValueChange = { selesai = it },
                    label = { Text("Tanggal selesai (opsional)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { aktif = !aktif }) {
                    Text(if (aktif) "✓ Aktif" else "Tandai sebagai aktif")
                }
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank() && mulai.isNotBlank(),
                onClick = {
                    onSimpan(nama.trim(), semester, mulai.trim(), selesai.trim(), aktif)
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
