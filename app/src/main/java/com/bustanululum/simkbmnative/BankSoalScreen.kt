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
import com.bustanululum.simkbmnative.data.local.BankSoalEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun BankSoalScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val soalList by db.bankSoalDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Bank Soal", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Jumlah: ${soalList.size}")
            Button(onClick = { tampilDialog = true }) {
                Text("+ Tambah")
            }
        }

        Spacer(Modifier.height(12.dp))

        if (soalList.isEmpty()) {
            Text("Belum ada soal.")
        }

        soalList.forEach { soal ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp)
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Text(
                        soal.mataPelajaran,
                        style = MaterialTheme.typography.titleSmall
                    )
                    Text(soal.pertanyaan)
                    soal.pilihanA?.let { Text("A. $it") }
                    soal.pilihanB?.let { Text("B. $it") }
                    soal.pilihanC?.let { Text("C. $it") }
                    soal.pilihanD?.let { Text("D. $it") }
                    soal.jawabanBenar?.let { Text("Jawaban: $it") }
                    soal.tingkat?.let { Text("Tingkat: $it") }
                    TextButton(onClick = {
                        scope.launch {
                            try {
                                db.withTransaction {
                                    db.syncQueueDao().enqueue(
                                        SyncQueueEntity(
                                            entityType = "bank_soal",
                                            localId = soal.localId,
                                            operation = "DELETE",
                                            payloadJson = JSONObject()
                                                .put("localId", soal.localId)
                                                .put("serverId", soal.serverId)
                                                .toString()
                                        )
                                    )
                                    db.bankSoalDao().delete(soal)
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
        DialogTambahSoal(
            onTutup = { tampilDialog = false },
            onSimpan = { mapel, pertanyaan, a, b, c, d, jawaban, tingkat ->
                scope.launch {
                    try {
                        val soal = BankSoalEntity(
                            mataPelajaran = mapel,
                            pertanyaan = pertanyaan,
                            pilihanA = a.ifBlank { null },
                            pilihanB = b.ifBlank { null },
                            pilihanC = c.ifBlank { null },
                            pilihanD = d.ifBlank { null },
                            jawabanBenar = jawaban.ifBlank { null },
                            tingkat = tingkat.ifBlank { null }
                        )
                        db.withTransaction {
                            db.bankSoalDao().upsert(soal)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "bank_soal",
                                    localId = soal.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", soal.localId)
                                        .put("mataPelajaran", soal.mataPelajaran)
                                        .put("pertanyaan", soal.pertanyaan)
                                        .put("pilihanA", soal.pilihanA)
                                        .put("pilihanB", soal.pilihanB)
                                        .put("pilihanC", soal.pilihanC)
                                        .put("pilihanD", soal.pilihanD)
                                        .put("jawabanBenar", soal.jawabanBenar)
                                        .put("tingkat", soal.tingkat)
                                        .put("updatedAt", soal.updatedAt)
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
private fun DialogTambahSoal(
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, String, String, String, String) -> Unit
) {
    var mapel by remember { mutableStateOf("") }
    var pertanyaan by remember { mutableStateOf("") }
    var a by remember { mutableStateOf("") }
    var b by remember { mutableStateOf("") }
    var c by remember { mutableStateOf("") }
    var d by remember { mutableStateOf("") }
    var jawaban by remember { mutableStateOf("") }
    var tingkat by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Soal") },
        text = {
            Column {
                OutlinedTextField(
                    value = mapel,
                    onValueChange = { mapel = it },
                    label = { Text("Mata pelajaran") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = pertanyaan,
                    onValueChange = { pertanyaan = it },
                    label = { Text("Pertanyaan") },
                    minLines = 2
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = a,
                    onValueChange = { a = it },
                    label = { Text("Pilihan A") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = b,
                    onValueChange = { b = it },
                    label = { Text("Pilihan B") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = c,
                    onValueChange = { c = it },
                    label = { Text("Pilihan C") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = d,
                    onValueChange = { d = it },
                    label = { Text("Pilihan D") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = jawaban,
                    onValueChange = { jawaban = it },
                    label = { Text("Jawaban benar (A/B/C/D)") },
                    singleLine = true
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = tingkat,
                    onValueChange = { tingkat = it },
                    label = { Text("Tingkat (Mudah/Sedang/Sulit)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = mapel.isNotBlank() && pertanyaan.isNotBlank(),
                onClick = {
                    onSimpan(
                        mapel.trim(), pertanyaan.trim(),
                        a.trim(), b.trim(), c.trim(), d.trim(),
                        jawaban.trim(), tingkat.trim()
                    )
                }
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
