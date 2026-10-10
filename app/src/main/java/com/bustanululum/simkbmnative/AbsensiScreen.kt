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
import com.bustanululum.simkbmnative.data.local.AbsensiEntity
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

private val statusAbsensi = listOf(
    "HADIR" to "Hadir",
    "LAMBAT" to "Lambat",
    "IZIN" to "Izin",
    "BELUM_HADIR" to "Belum Hadir",
    "PENGGANTI" to "Pengganti"
)

@Composable
fun AbsensiScreen(db: AppDatabase) {
    val kelasList by db.kelasDao()
        .observeAll()
        .collectAsState(initial = emptyList())

    val semuaMurid by db.muridDao()
        .observeAll()
        .collectAsState(initial = emptyList())

    var kelasId by remember { mutableStateOf<String?>(null) }

    var tanggal by remember {
        mutableStateOf(
            SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.ROOT
            ).format(Date())
        )
    }

    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialogTanggal by remember { mutableStateOf(false) }

    val absensiList by db.absensiDao()
        .observeByTanggal(tanggal)
        .collectAsState(initial = emptyList())

    val scope = rememberCoroutineScope()
    val kelasAktif = kelasList.firstOrNull { it.localId == kelasId }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text(
            "Absensi Harian",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(Modifier.height(8.dp))

        Text("Tanggal: $tanggal")

        TextButton(onClick = { tampilDialogTanggal = true }) {
            Text("Ganti tanggal")
        }

        Spacer(Modifier.height(8.dp))

        Text("Pilih kelas:")

        kelasList.forEach { kelas ->
            val dipilih = kelas.localId == kelasId

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(
                    modifier = Modifier.padding(10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(kelas.nama)
                        Text(
                            kelas.lembaga,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }

                    TextButton(onClick = {
                        kelasId = kelas.localId
                    }) {
                        Text(if (dipilih) "Dipilih ✓" else "Pilih")
                    }
                }
            }
        }

        Spacer(Modifier.height(12.dp))

        if (kelasList.isEmpty()) {
            Text("Belum ada kelas. Tambahkan kelas terlebih dahulu.")
        } else if (kelasAktif == null) {
            Text("Pilih kelas untuk mulai mengisi absensi.")
        } else {
            val muridKelas = semuaMurid.filter {
                it.kelasLocalId == kelasAktif.localId
            }

            Text(
                "Kelas ${kelasAktif.nama}",
                style = MaterialTheme.typography.titleMedium
            )

            Text("Jumlah murid: ${muridKelas.size}")

            val rekap = statusAbsensi.map { (kode, label) ->
                label to muridKelas.count { murid ->
                    absensiList.firstOrNull {
                        it.muridLocalId == murid.localId
                    }?.status == kode
                }
            }

            Spacer(Modifier.height(8.dp))

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        "Rekap absensi",
                        style = MaterialTheme.typography.titleSmall
                    )
                    rekap.forEach { (label, jumlah) ->
                        Text("$label: $jumlah")
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            if (muridKelas.isEmpty()) {
                Text("Belum ada murid di kelas ini.")
            }

            muridKelas.forEach { murid ->
                val catatan = absensiList.firstOrNull {
                    it.muridLocalId == murid.localId
                }

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

                        Text(
                            "Status: " + (
                                statusAbsensi.firstOrNull {
                                    it.first == catatan?.status
                                }?.second ?: "Belum diisi"
                            )
                        )

                        Spacer(Modifier.height(8.dp))

                        statusAbsensi.chunked(2).forEach { baris ->
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                baris.forEach { (kode, label) ->
                                    Button(
                                        modifier = Modifier.weight(1f),
                                        onClick = {
                                            scope.launch {
                                                try {
                                                    db.withTransaction {
                                                        val lama = db.absensiDao()
                                                            .getByMuridTanggal(
                                                                murid.localId,
                                                                tanggal
                                                            )

                                                        val baru = AbsensiEntity(
                                                            localId = lama?.localId
                                                                ?: java.util.UUID
                                                                    .randomUUID()
                                                                    .toString(),
                                                            serverId = lama?.serverId,
                                                            muridLocalId = murid.localId,
                                                            tanggal = tanggal,
                                                            status = kode,
                                                            catatan = lama?.catatan,
                                                            updatedAt = System.currentTimeMillis(),
                                                            syncStatus = "PENDING"
                                                        )

                                                        db.absensiDao().upsert(baru)

                                                        db.syncQueueDao().enqueue(
                                                            SyncQueueEntity(
                                                                entityType = "absensi",
                                                                localId = baru.localId,
                                                                operation = "UPSERT",
                                                                payloadJson = JSONObject()
                                                                    .put("localId", baru.localId)
                                                                    .put("serverId", baru.serverId)
                                                                    .put("muridLocalId", baru.muridLocalId)
                                                                    .put("tanggal", baru.tanggal)
                                                                    .put("status", baru.status)
                                                                    .put("updatedAt", baru.updatedAt)
                                                                    .toString()
                                                            )
                                                        )
                                                    }
                                                    pesan = "${murid.nama}: $label tersimpan."
                                                } catch (e: Exception) {
                                                    pesan =
                                                        "Gagal menyimpan absensi: ${e.message}"
                                                }
                                            }
                                        }
                                    ) {
                                        Text(label)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    if (tampilDialogTanggal) {
        AlertDialog(
            onDismissRequest = { tampilDialogTanggal = false },
            title = { Text("Pilih tanggal") },
            text = {
                OutlinedTextField(
                    value = tanggal,
                    onValueChange = { tanggal = it },
                    label = { Text("Tanggal YYYY-MM-DD") },
                    singleLine = true
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val valid = try {
                        val format = SimpleDateFormat(
                            "yyyy-MM-dd",
                            Locale.ROOT
                        )
                        format.isLenient = false
                        val hasil = format.parse(tanggal)
                        hasil != null && format.format(hasil) == tanggal
                    } catch (_: Exception) {
                        false
                    }

                    if (valid) {
                        tampilDialogTanggal = false
                    } else {
                        pesan = "Format tanggal tidak valid. Gunakan YYYY-MM-DD."
                    }
                }) {
                    Text("Terapkan")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tampilDialogTanggal = false
                }) {
                    Text("Batal")
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
                TextButton(onClick = { pesan = null }) {
                    Text("Tutup")
                }
            }
        )
    }
}
