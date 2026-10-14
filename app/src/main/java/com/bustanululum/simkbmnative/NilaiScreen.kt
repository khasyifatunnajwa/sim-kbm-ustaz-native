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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.bustanululum.simkbmnative.data.local.NilaiEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MenuIcons
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.MenuIcons
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(GradientHeader)
                .padding(24.dp)
        ) {
            Column {
                Text("Nilai", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Input dan kelola nilai murid", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pilih kelas:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
            Spacer(Modifier.height(8.dp))
            kelasList.forEach { kelas ->
                val selected = kelasTerpilih == kelas.localId
                Card(
                    modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (selected) MintSurface else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    onClick = { kelasTerpilih = kelas.localId }
                ) {
                    Row(modifier = Modifier.padding(12.dp), verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier.size(36.dp).clip(RoundedCornerShape(10.dp)).background(MintSurface),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(MenuIcons.kelas, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                        Spacer(Modifier.width(10.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(kelas.nama, fontWeight = FontWeight.SemiBold, color = SlateDark, fontSize = 15.sp)
                            Text(kelas.lembaga, fontSize = 12.sp, color = SlateMedium)
                        }
                        if (selected) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (kelasAktif != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("${muridKelas.size} murid", fontSize = 14.sp, color = SlateMedium)
                    Button(
                        onClick = { tampilDialog = true },
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Tambah Nilai")
                    }
                }

                Spacer(Modifier.height(16.dp))

                muridKelas.forEach { murid ->
                    val nilaiMurid = nilaiList.filter { it.muridLocalId == murid.localId }
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
                                    Icon(MenuIcons.profil, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                                }
                                Spacer(Modifier.width(10.dp))
                                Text(murid.nama, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                            }
                            if (nilaiMurid.isEmpty()) {
                                Spacer(Modifier.height(6.dp))
                                Text("Belum ada nilai.", fontSize = 13.sp, color = SlateMedium)
                            } else {
                                nilaiMurid.forEach { nilai ->
                                    Row(
                                        modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Column(modifier = Modifier.weight(1f)) {
                                            Text("${nilai.mataPelajaran} - ${nilai.jenisNilai}", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                                            Text("Tanggal: ${nilai.tanggal}", fontSize = 12.sp, color = SlateMedium)
                                            nilai.keterangan?.let { Text(it, fontSize = 12.sp, color = SlateMedium) }
                                        }
                                        Text("${nilai.nilai}", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                                        IconButton(onClick = {
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
                                            Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(18.dp))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else {
                EmptyState("Pilih kelas untuk melihat nilai murid.")
            }
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
            confirmButton = { TextButton(onClick = { pesan = null }) { Text("Tutup") } }
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
        title = { Text("Tambah Nilai", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Pilih murid:", fontSize = 13.sp, color = SlateMedium)
                muridList.forEach { (id, nama) ->
                    TextButton(onClick = { muridId = id }) {
                        if (muridId == id) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(nama, color = if (muridId == id) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = mapel, onValueChange = { mapel = it }, label = { Text("Mata pelajaran") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                Text("Jenis nilai:", fontSize = 13.sp, color = SlateMedium)
                jenisNilaiList.forEach { j ->
                    TextButton(onClick = { jenis = j }) {
                        if (jenis == j) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(j, color = if (jenis == j) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = nilaiStr, onValueChange = { nilaiStr = it.filter { c -> c.isDigit() || c == '.' } }, label = { Text("Nilai (0-100)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tanggal, onValueChange = { tanggal = it }, label = { Text("Tanggal YYYY-MM-DD") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = ket, onValueChange = { ket = it }, label = { Text("Keterangan (opsional)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = muridId != null && mapel.isNotBlank() && nilaiStr.isNotBlank() && tanggal.isNotBlank(),
                onClick = {
                    val nilaiVal = nilaiStr.toDoubleOrNull() ?: 0.0
                    onSimpan(muridId!!, mapel.trim(), jenis, nilaiVal, tanggal.trim(), ket.trim())
                },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
