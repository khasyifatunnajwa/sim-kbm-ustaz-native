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
import com.bustanululum.simkbmnative.data.local.PengumumanEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun PengumumanScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val pengumumanList by db.pengumumanDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val tanggalHariIni = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Pengumuman", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${pengumumanList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (pengumumanList.isEmpty()) {
            Text("Belum ada pengumuman.")
        }

        pengumumanList.forEach { pengumuman ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        pengumuman.judul,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Tanggal: ${pengumuman.tanggal}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(pengumuman.isi)
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                db.withTransaction {
                                    db.syncQueueDao().enqueue(
                                        SyncQueueEntity(
                                            entityType = "pengumuman",
                                            localId = pengumuman.localId,
                                            operation = "DELETE",
                                            payloadJson = JSONObject()
                                                .put("localId", pengumuman.localId)
                                                .put("serverId", pengumuman.serverId)
                                                .toString()
                                        )
                                    )
                                    db.pengumumanDao().delete(pengumuman)
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
        DialogTambahPengumuman(
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { judul, isi, tanggal ->
                scope.launch {
                    try {
                        val pengumuman = PengumumanEntity(
                            judul = judul,
                            isi = isi,
                            tanggal = tanggal
                        )
                        db.withTransaction {
                            db.pengumumanDao().upsert(pengumuman)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "pengumuman",
                                    localId = pengumuman.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", pengumuman.localId)
                                        .put("judul", pengumuman.judul)
                                        .put("isi", pengumuman.isi)
                                        .put("tanggal", pengumuman.tanggal)
                                        .put("updatedAt", pengumuman.updatedAt)
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
private fun DialogTambahPengumuman(
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String) -> Unit
) {
    var judul by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf(tanggalDefault) }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Pengumuman") },
        text = {
            Column {
                OutlinedTextField(
                    value = judul,
                    onValueChange = { judul = it },
                    label = { Text("Judul") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = isi,
                    onValueChange = { isi = it },
                    label = { Text("Isi pengumuman") },
                    minLines = 3
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tanggal,
                    onValueChange = { tanggal = it },
                    label = { Text("Tanggal YYYY-MM-DD") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = judul.isNotBlank() && isi.isNotBlank() && tanggal.isNotBlank(),
                onClick = {
                    onSimpan(judul.trim(), isi.trim(), tanggal.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
