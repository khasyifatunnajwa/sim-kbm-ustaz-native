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
import com.bustanululum.simkbmnative.data.local.BukuSakuEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun BukuSakuScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val bukuList by db.bukuSakuDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Buku Saku", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${bukuList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (bukuList.isEmpty()) {
            Text("Belum ada catatan buku saku.")
        }

        bukuList.forEach { buku ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        buku.judul,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text(
                        "Kategori: ${buku.kategori}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Spacer(Modifier.height(4.dp))
                    Text(buku.isi)
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                db.withTransaction {
                                    db.syncQueueDao().enqueue(
                                        SyncQueueEntity(
                                            entityType = "buku_saku",
                                            localId = buku.localId,
                                            operation = "DELETE",
                                            payloadJson = JSONObject()
                                                .put("localId", buku.localId)
                                                .put("serverId", buku.serverId)
                                                .toString()
                                        )
                                    )
                                    db.bukuSakuDao().delete(buku)
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
        DialogTambahBukuSaku(
            onTutup = { tampilDialog = false },
            onSimpan = { judul, kategori, isi ->
                scope.launch {
                    try {
                        val buku = BukuSakuEntity(
                            judul = judul,
                            kategori = kategori,
                            isi = isi
                        )
                        db.withTransaction {
                            db.bukuSakuDao().upsert(buku)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "buku_saku",
                                    localId = buku.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", buku.localId)
                                        .put("judul", buku.judul)
                                        .put("kategori", buku.kategori)
                                        .put("isi", buku.isi)
                                        .put("updatedAt", buku.updatedAt)
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
private fun DialogTambahBukuSaku(
    onTutup: () -> Unit,
    onSimpan: (String, String, String) -> Unit
) {
    var judul by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Buku Saku") },
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
                    value = kategori,
                    onValueChange = { kategori = it },
                    label = { Text("Kategori") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = isi,
                    onValueChange = { isi = it },
                    label = { Text("Isi catatan") },
                    minLines = 3
                )
            }
        },
        confirmButton = {
            Button(
                enabled = judul.isNotBlank() && kategori.isNotBlank() && isi.isNotBlank(),
                onClick = {
                    onSimpan(judul.trim(), kategori.trim(), isi.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
