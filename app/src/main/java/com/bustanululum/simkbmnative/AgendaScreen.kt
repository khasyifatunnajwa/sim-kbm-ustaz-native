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
import androidx.compose.material.icons.filled.Event
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
import com.bustanululum.simkbmnative.data.local.AgendaEntity
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.ModernCard
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun AgendaScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val agendaList by db.agendaDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()
    val tanggalHariIni = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date())

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
                Text("Agenda", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Agenda kegiatan dan acara", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${agendaList.size} agenda", fontSize = 14.sp, color = SlateMedium)
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

            if (agendaList.isEmpty()) {
                EmptyState("Belum ada agenda.")
            }

            agendaList.forEach { agenda ->
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
                                Icon(Icons.Filled.Event, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Text(agenda.judul, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                        }
                        Spacer(Modifier.height(6.dp))
                        Text("Tanggal: ${agenda.tanggal}", fontSize = 13.sp, color = SlateMedium)
                        agenda.waktu?.let { Text("Waktu: $it", fontSize = 13.sp, color = SlateMedium) }
                        agenda.lokasi?.let { Text("Lokasi: $it", fontSize = 13.sp, color = SlateMedium) }
                        agenda.catatan?.let { Text("Catatan: $it", fontSize = 13.sp, color = SlateMedium) }
                        TextButton(onClick = {
                            scope.launch {
                                try {
                                    db.withTransaction {
                                        db.syncQueueDao().enqueue(
                                            SyncQueueEntity(
                                                entityType = "agenda",
                                                localId = agenda.localId,
                                                operation = "DELETE",
                                                payloadJson = JSONObject()
                                                    .put("localId", agenda.localId)
                                                    .put("serverId", agenda.serverId)
                                                    .toString()
                                            )
                                        )
                                        db.agendaDao().delete(agenda)
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
        DialogTambahAgenda(
            tanggalDefault = tanggalHariIni,
            onTutup = { tampilDialog = false },
            onSimpan = { judul, tanggal, waktu, lokasi, catatan ->
                scope.launch {
                    try {
                        val agenda = AgendaEntity(
                            judul = judul,
                            tanggal = tanggal,
                            waktu = waktu.ifBlank { null },
                            lokasi = lokasi.ifBlank { null },
                            catatan = catatan.ifBlank { null }
                        )
                        db.withTransaction {
                            db.agendaDao().upsert(agenda)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "agenda",
                                    localId = agenda.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", agenda.localId)
                                        .put("judul", agenda.judul)
                                        .put("tanggal", agenda.tanggal)
                                        .put("waktu", agenda.waktu)
                                        .put("lokasi", agenda.lokasi)
                                        .put("catatan", agenda.catatan)
                                        .put("updatedAt", agenda.updatedAt)
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
private fun DialogTambahAgenda(
    tanggalDefault: String,
    onTutup: () -> Unit,
    onSimpan: (String, String, String, String, String) -> Unit
) {
    var judul by remember { mutableStateOf("") }
    var tanggal by remember { mutableStateOf(tanggalDefault) }
    var waktu by remember { mutableStateOf("") }
    var lokasi by remember { mutableStateOf("") }
    var catatan by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Agenda", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(value = judul, onValueChange = { judul = it }, label = { Text("Judul agenda") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = tanggal, onValueChange = { tanggal = it }, label = { Text("Tanggal YYYY-MM-DD") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = waktu, onValueChange = { waktu = it }, label = { Text("Waktu (opsional)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = lokasi, onValueChange = { lokasi = it }, label = { Text("Lokasi (opsional)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = catatan, onValueChange = { catatan = it }, label = { Text("Catatan (opsional)") }, singleLine = true, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = judul.isNotBlank() && tanggal.isNotBlank(),
                onClick = { onSimpan(judul.trim(), tanggal.trim(), waktu.trim(), lokasi.trim(), catatan.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
