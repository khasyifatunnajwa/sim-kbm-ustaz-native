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
import com.bustanululum.simkbmnative.data.local.IzinMengajarEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

private val statusIzin = listOf("DIAJUKAN", "DISETUJUI", "DITOLAK")

@Composable
fun IzinMengajarScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val izinList by db.izinMengajarDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val tanggalHariIni = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Izin Mengajar", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${izinList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (izinList.isEmpty()) {
            Text("Belum ada catatan izin mengajar.")
        }

        izinList.forEach { izin ->
            val kelas = kelasList.firstOrNull { it.localId == izin.kelasLocalId }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        izin.tanggal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Kelas: ${kelas?.nama ?: "Tidak diketahui"}")
                    Text("Alasan: ${izin.alasan}")
                    izin.pengganti?.let { Text("Pengganti: $it") }
                    Text("Status: ${izin.status}")
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                db.withTransaction {
                                    db.syncQueueDao().enqueue(
                                        SyncQueueEntity(
                                            entityType = "izin_mengajar",
                                            localId = izin.localId,
                                            operation = "DELETE",
                                            payloadJson = JSONObject()
                                                .put("localId", izin.localId)
                                                .put("serverId", izin.serverId)
                                                .toString()
                                        )
                                    )
                                    db.izinMengajarDao().delete(izin)
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
        DialogTambahIzin(
            kelasList = kelasList.map { it.localId to it.nama },
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { kelasId, tanggal, alasan, pengganti ->
                scope.launch {
                    try {
                        val izin = IzinMengajarEntity(
                            kelasLocalId = kelasId,
                            tanggal = tanggal,
                            alasan = alasan,
                            pengganti = pengganti.ifBlank { null }
                        )
                        db.withTransaction {
                            db.izinMengajarDao().upsert(izin)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "izin_mengajar",
                                    localId = izin.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", izin.localId)
                                        .put("kelasLocalId", izin.kelasLocalId)
                                        .put("tanggal", izin.tanggal)
                                        .put("alasan", izin.alasan)
                                        .put("pengganti", izin.pengganti)
                                        .put("status", izin.status)
                                        .put("updatedAt", izin.updatedAt)
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
private fun DialogTambahIzin(
    kelasList: List<Pair<String, String>>,
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String) -> Unit
) {
    var kelasId by remember { mutableStateOf<String?>(null) }
    var tanggal by remember { mutableStateOf(tanggalDefault) }
    var alasan by remember { mutableStateOf("") }
    var pengganti by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Izin Mengajar") },
        text = {
            Column {
                Text("Pilih kelas:")
                kelasList.forEach { (id, nama) ->
                    TextButton(onClick = { kelasId = id }) {
                        Text(if (kelasId == id) "✓ $nama" else nama)
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
                OutlinedTextField(
                    value = alasan,
                    onValueChange = { alasan = it },
                    label = { Text("Alasan izin") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = pengganti,
                    onValueChange = { pengganti = it },
                    label = { Text("Pengganti (opsional)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = kelasId != null &&
                    tanggal.isNotBlank() &&
                    alasan.isNotBlank(),
                onClick = {
                    onSimpan(kelasId!!, tanggal.trim(), alasan.trim(), pengganti.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
