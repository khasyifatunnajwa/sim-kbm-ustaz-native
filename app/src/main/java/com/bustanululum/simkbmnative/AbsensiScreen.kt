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
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.withTransaction
import com.bustanululum.simkbmnative.data.local.AbsensiEntity
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MenuIcons
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

private val statusAbsensi = listOf(
    "HADIR" to "Hadir",
    "LAMBAT" to "Lambat",
    "IZIN" to "Izin",
    "BELUM_HADIR" to "Belum Hadir",
    "PENGGANTI" to "Pengganti"
)

@Composable
fun AbsensiScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    val kelasList by db.kelasDao()
        .observeAll()
        .collectAsState(initial = emptyList())

    val semuaMurid by db.muridDao()
        .observeAll()
        .collectAsState(initial = emptyList())

    var kelasId by remember { mutableStateOf<String?>(null) }

    var tanggal by remember {
        mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).format(Date()))
    }
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
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
                .background(GradientHeader)
                .padding(24.dp)
        ) {
            Column {
                Text("Absensi Harian", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Catat kehadiran murid setiap hari", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.CalendarToday, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(22.dp))
                    Spacer(Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text("Tanggal", fontSize = 12.sp, color = SlateMedium)
                        Text(tanggal, fontSize = 16.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                    }
                    TextButton(onClick = { tampilDialogTanggal = true }) {
                        Text("Ganti", color = EmeraldPrimary, fontWeight = FontWeight.SemiBold)
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            Text("Pilih Kelas", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
            Spacer(Modifier.height(8.dp))

            kelasList.forEach { kelas ->
                val dipilih = kelas.localId == kelasId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (dipilih) MintSurface else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
                    onClick = { kelasId = kelas.localId }
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
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
                        if (dipilih) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (kelasList.isEmpty()) {
                EmptyState("Belum ada kelas. Tambahkan kelas terlebih dahulu.")
            } else if (kelasAktif == null) {
                EmptyState("Pilih kelas untuk mulai mengisi absensi.")
            } else {
                val muridKelas = semuaMurid.filter { it.kelasLocalId == kelasAktif.localId }

                Text("Kelas ${kelasAktif.nama}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                Text("Jumlah murid: ${muridKelas.size}", fontSize = 13.sp, color = SlateMedium)

                val rekap = statusAbsensi.map { (kode, label) ->
                    label to muridKelas.count { murid ->
                        absensiList.firstOrNull { it.muridLocalId == murid.localId }?.status == kode
                    }
                }

                Spacer(Modifier.height(12.dp))

                ModernCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(14.dp)) {
                        Text("Rekap Absensi", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                        Spacer(Modifier.height(8.dp))
                        rekap.forEach { (label, jumlah) ->
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(vertical = 2.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(label, fontSize = 13.sp, color = SlateMedium)
                                Text("$jumlah", fontSize = 13.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                            }
                        }
                    }
                }

                Spacer(Modifier.height(16.dp))

                if (muridKelas.isEmpty()) {
                    EmptyState("Belum ada murid di kelas ini.")
                }

                muridKelas.forEach { murid ->
                    val catatan = absensiList.firstOrNull { it.muridLocalId == murid.localId }

                    Card(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Text(murid.nama, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                            Text(
                                "Status: " + (statusAbsensi.firstOrNull { it.first == catatan?.status }?.second ?: "Belum diisi"),
                                fontSize = 13.sp,
                                color = SlateMedium
                            )

                            Spacer(Modifier.height(10.dp))

                            statusAbsensi.chunked(2).forEach { baris ->
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    baris.forEach { (kode, label) ->
                                        val isActive = catatan?.status == kode
                                        Button(
                                            modifier = Modifier.weight(1f),
                                            onClick = {
                                                scope.launch {
                                                    try {
                                                        db.withTransaction {
                                                            val lama = db.absensiDao().getByMuridTanggal(murid.localId, tanggal)
                                                            val baru = AbsensiEntity(
                                                                localId = lama?.localId ?: java.util.UUID.randomUUID().toString(),
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
                                                        pesan = "Gagal menyimpan absensi: ${e.message}"
                                                    }
                                                }
                                            },
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = if (isActive) EmeraldPrimary else MintSurface,
                                                contentColor = if (isActive) Color.White else SlateDark
                                            ),
                                            shape = RoundedCornerShape(10.dp)
                                        ) { Text(label, fontSize = 12.sp) }
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
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    val valid = try {
                        val format = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT)
                        format.isLenient = false
                        val hasil = format.parse(tanggal)
                        hasil != null && format.format(hasil) == tanggal
                    } catch (_: Exception) { false }
                    if (valid) tampilDialogTanggal = false
                    else pesan = "Format tanggal tidak valid. Gunakan YYYY-MM-DD."
                }) { Text("Terapkan", color = EmeraldPrimary) }
            },
            dismissButton = { TextButton(onClick = { tampilDialogTanggal = false }) { Text("Batal") } }
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
fun EmptyState(pesan: String) {
    ModernCard(modifier = Modifier.fillMaxWidth()) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Text(pesan, fontSize = 13.sp, color = SlateMedium)
        }
    }
}
