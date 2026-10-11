package com.bustanululum.simkbmnative

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.withTransaction
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.BankSoalEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(GradientHeader)
                .padding(24.dp)
        ) {
            Column {
                Text("Bank Soal", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Kumpulan soal untuk evaluasi", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${soalList.size} soal", fontSize = 14.sp, color = SlateMedium)
                Button(
                    onClick = { tampilDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Tambah")
                }
            }

            Spacer(Modifier.height(16.dp))

            if (soalList.isEmpty()) {
                EmptyState("Belum ada soal.")
            }

            soalList.forEach { soal ->
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(MintSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(Icons.Filled.LibraryBooks, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(soal.mataPelajaran, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(soal.pertanyaan, fontSize = 14.sp, color = SlateDark)
                        soal.pilihanA?.let { Text("A. $it", fontSize = 13.sp, color = SlateMedium) }
                        soal.pilihanB?.let { Text("B. $it", fontSize = 13.sp, color = SlateMedium) }
                        soal.pilihanC?.let { Text("C. $it", fontSize = 13.sp, color = SlateMedium) }
                        soal.pilihanD?.let { Text("D. $it", fontSize = 13.sp, color = SlateMedium) }
                        soal.jawabanBenar?.let { Text("Jawaban: $it", fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = EmeraldPrimary) }
                        soal.tingkat?.let { Text("Tingkat: $it", fontSize = 12.sp, color = SlateMedium) }
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
                            Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Hapus", color = Color(0xFFDC2626))
                        }
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
            confirmButton = { TextButton(onClick = { pesan = null }) { Text("Tutup") } }
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
        title = { Text("Tambah Soal", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(value = mapel, onValueChange = { mapel = it }, label = { Text("Mata pelajaran") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = pertanyaan, onValueChange = { pertanyaan = it }, label = { Text("Pertanyaan") }, minLines = 2, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = a, onValueChange = { a = it }, label = { Text("Pilihan A") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = b, onValueChange = { b = it }, label = { Text("Pilihan B") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = c, onValueChange = { c = it }, label = { Text("Pilihan C") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = d, onValueChange = { d = it }, label = { Text("Pilihan D") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = jawaban, onValueChange = { jawaban = it }, label = { Text("Jawaban benar (A/B/C/D)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tingkat, onValueChange = { tingkat = it }, label = { Text("Tingkat (Mudah/Sedang/Sulit)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = mapel.isNotBlank() && pertanyaan.isNotBlank(),
                onClick = { onSimpan(mapel.trim(), pertanyaan.trim(), a.trim(), b.trim(), c.trim(), d.trim(), jawaban.trim(), tingkat.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
