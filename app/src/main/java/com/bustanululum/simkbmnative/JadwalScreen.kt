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
import com.bustanululum.simkbmnative.data.local.JadwalEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

private val hariList = listOf(
    "Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu", "Minggu"
)

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
            .padding(16.dp)
    ) {
        Text("Jadwal Mengajar", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${jadwalList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (jadwalList.isEmpty()) {
            Text("Belum ada jadwal mengajar.")
        }

        hariList.forEach { hari ->
            val jadwalHari = jadwalList.filter { it.hari == hari }
            if (jadwalHari.isNotEmpty()) {
                Text(
                    hari,
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(vertical = 4.dp)
                )
                jadwalHari.forEach { jadwal ->
                    val kelas = kelasList.firstOrNull { it.localId == jadwal.kelasLocalId }
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                "${jadwal.jamMulai} - ${jadwal.jamSelesai}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text("Kelas: ${kelas?.nama ?: "Tidak diketahui"}")
                            Text("Mapel: ${jadwal.mataPelajaran}")
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
                                Text("Hapus", color = MaterialTheme.colorScheme.error)
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
            confirmButton = {
                TextButton(onClick = { pesan = null }) { Text("Tutup") }
            }
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
        title = { Text("Tambah Jadwal") },
        text = {
            Column {
                Text("Pilih kelas:")
                kelasList.forEach { (id, nama) ->
                    TextButton(onClick = { kelasId = id }) {
                        Text(if (kelasId == id) "✓ $nama" else nama)
                    }
                }
                Spacer(Modifier.height(8.dp))
                Text("Hari:")
                hariList.forEach { h ->
                    TextButton(onClick = { hari = h }) {
                        Text(if (hari == h) "✓ $h" else h)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = jamMulai,
                    onValueChange = { jamMulai = it },
                    label = { Text("Jam mulai (HH:mm)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = jamSelesai,
                    onValueChange = { jamSelesai = it },
                    label = { Text("Jam selesai (HH:mm)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = mapel,
                    onValueChange = { mapel = it },
                    label = { Text("Mata pelajaran") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = kelasId != null &&
                    jamMulai.isNotBlank() &&
                    jamSelesai.isNotBlank() &&
                    mapel.isNotBlank(),
                onClick = {
                    onSimpan(kelasId!!, hari, jamMulai.trim(), jamSelesai.trim(), mapel.trim())
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
