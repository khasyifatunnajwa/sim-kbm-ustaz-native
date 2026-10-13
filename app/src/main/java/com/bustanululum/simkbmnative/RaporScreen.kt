package com.bustanululum.simkbmnative

import com.bustanululum.simkbmnative.ui.MenuIcons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Task
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium

@Composable
fun RaporScreen(db: AppDatabase) {
    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())
    val semuaMurid by db.muridDao().observeAll()
        .collectAsState(initial = emptyList())
    val nilaiList by db.nilaiDao().observeAll()
        .collectAsState(initial = emptyList())
    var kelasTerpilih by remember { mutableStateOf<String?>(null) }

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
                Text("Rapor", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Rekap nilai per murid", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Text("Pilih kelas untuk melihat rekap nilai:", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
            Spacer(Modifier.height(8.dp))

            kelasList.forEach { kelas ->
                val selected = kelasTerpilih == kelas.localId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clickable { kelasTerpilih = kelas.localId },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = if (selected) MintSurface else Color.White),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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

            if (kelasTerpilih != null && kelasAktif != null) {
                Spacer(Modifier.height(16.dp))
                Text("Rekap Nilai ${kelasAktif.nama}", fontSize = 18.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                Spacer(Modifier.height(12.dp))

                muridKelas.forEach { murid ->
                    val nilaiMurid = nilaiList.filter { it.muridLocalId == murid.localId }
                    val rataRata = if (nilaiMurid.isNotEmpty()) {
                        nilaiMurid.map { it.nilai }.average()
                    } else 0.0

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
                                    Icon(Icons.Filled.Task, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                                }
                                Spacer(Modifier.width(10.dp))
                                Text(murid.nama, fontSize = 15.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                            }
                            if (nilaiMurid.isEmpty()) {
                                Spacer(Modifier.height(6.dp))
                                Text("Belum ada nilai.", fontSize = 13.sp, color = SlateMedium)
                            } else {
                                Spacer(Modifier.height(8.dp))
                                nilaiMurid.forEach { nilai ->
                                    Text("${nilai.mataPelajaran} - ${nilai.jenisNilai}: ${nilai.nilai}", fontSize = 13.sp, color = SlateDark)
                                }
                                Spacer(Modifier.height(6.dp))
                                Text("Rata-rata: ${"%.1f".format(rataRata)}", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = EmeraldPrimary)
                            }
                        }
                    }
                }
            }
        }
    }
}
