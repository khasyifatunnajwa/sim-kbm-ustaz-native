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
import com.bustanululum.simkbmnative.data.local.JurnalKBMEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
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
            .padding(16.dp)
    ) {
        Text("Jurnal KBM", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${jurnalList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (jurnalList.isEmpty()) {
            Text("Belum ada jurnal KBM.")
        }

        jurnalList.forEach { jurnal ->
            val kelas = kelasList.firstOrNull { it.localId == jurnal.kelasLocalId }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        jurnal.tanggal,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Text("Kelas: ${kelas?.nama ?: "Tidak diketahui"}")
                    Text("Materi: ${jurnal.materi}")
                    Text("Kegiatan: ${jurnal.kegiatan}")
                    jurnal.kendala?.let { Text("Kendala: $it") }
                    Text(
                        "Status: ${jurnal.syncStatus}",
                        style = MaterialTheme.typography.bodySmall
                    )
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
                        Text("Hapus", color = MaterialTheme.colorScheme.error)
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
            confirmButton = {
                TextButton(onClick = { pesan = null }) { Text("Tutup") }
            }
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
        title = { Text("Tambah Jurnal KBM") },
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
                    value = materi,
                    onValueChange = { materi = it },
                    label = { Text("Materi") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = kegiatan,
                    onValueChange = { kegiatan = it },
                    label = { Text("Kegiatan") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = kendala,
                    onValueChange = { kendala = it },
                    label = { Text("Kendala (opsional)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = kelasId != null &&
                    tanggal.isNotBlank() &&
                    materi.isNotBlank() &&
                    kegiatan.isNotBlank(),
                onClick = {
                    onSimpan(kelasId!!, tanggal.trim(), materi.trim(), kegiatan.trim(), kendala.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
