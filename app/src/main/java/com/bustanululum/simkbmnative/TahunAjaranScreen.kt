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
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Delete
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
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.data.local.TahunAjaranEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(GradientHeader)
                .padding(24.dp)
        ) {
            Column {
                Text("Tahun Ajaran & Semester", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Kelola periode tahun ajaran", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${tahunList.size} periode", fontSize = 14.sp, color = SlateMedium)
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

            if (tahunList.isEmpty()) {
                EmptyState("Belum ada tahun ajaran.")
            }

            tahunList.forEach { tahun ->
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
                                Icon(Icons.Filled.CalendarMonth, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text("${tahun.namaTahunAjaran} - ${tahun.semester}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                                Text("Mulai: ${tahun.tanggalMulai}", fontSize = 13.sp, color = SlateMedium)
                                tahun.tanggalSelesai?.let { Text("Selesai: $it", fontSize = 13.sp, color = SlateMedium) }
                            }
                            if (tahun.aktif) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                                    Spacer(Modifier.width(4.dp))
                                    Text("Aktif", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = EmeraldPrimary)
                                }
                            }
                        }
                        Spacer(Modifier.height(8.dp))
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
        title = { Text("Tambah Tahun Ajaran", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama tahun ajaran (cth: 2025/2026)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text("Semester:", fontSize = 13.sp, color = SlateMedium)
                semesterList.forEach { s ->
                    TextButton(onClick = { semester = s }) {
                        if (semester == s) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                        }
                        Text(s, color = if (semester == s) EmeraldPrimary else SlateDark)
                    }
                }
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = mulai,
                    onValueChange = { mulai = it },
                    label = { Text("Tanggal mulai YYYY-MM-DD") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = selesai,
                    onValueChange = { selesai = it },
                    label = { Text("Tanggal selesai (opsional)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                TextButton(onClick = { aktif = !aktif }) {
                    if (aktif) {
                        Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Aktif", color = EmeraldPrimary)
                    } else {
                        Text("Tandai sebagai aktif", color = SlateDark)
                    }
                }
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank() && mulai.isNotBlank(),
                onClick = { onSimpan(nama.trim(), semester, mulai.trim(), selesai.trim(), aktif) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
