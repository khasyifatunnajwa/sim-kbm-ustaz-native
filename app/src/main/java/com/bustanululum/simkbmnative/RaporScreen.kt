package com.bustanululum.simkbmnative

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bustanululum.simkbmnative.data.local.AppDatabase

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
            .padding(16.dp)
    ) {
        Text("Rapor", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))

        Text("Pilih kelas untuk melihat rekap nilai:")
        kelasList.forEach { kelas ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Row(modifier = Modifier.padding(12.dp)) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(kelas.nama, style = MaterialTheme.typography.titleSmall)
                        Text(kelas.lembaga, style = MaterialTheme.typography.bodySmall)
                    }
                    Text(
                        if (kelasTerpilih == kelas.localId) "✓" else "Pilih",
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }

        if (kelasTerpilih != null && kelasAktif != null) {
            Spacer(Modifier.height(12.dp))
            Text(
                "Rekap Nilai ${kelasAktif.nama}",
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(Modifier.height(8.dp))

            muridKelas.forEach { murid ->
                val nilaiMurid = nilaiList.filter { it.muridLocalId == murid.localId }
                val rataRata = if (nilaiMurid.isNotEmpty()) {
                    nilaiMurid.map { it.nilai }.average()
                } else 0.0

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 5.dp)
                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Text(
                            murid.nama,
                            style = MaterialTheme.typography.titleSmall
                        )
                        if (nilaiMurid.isEmpty()) {
                            Text("Belum ada nilai.")
                        } else {
                            nilaiMurid.forEach { nilai ->
                                Text(
                                    "${nilai.mataPelajaran} - ${nilai.jenisNilai}: ${nilai.nilai}"
                                )
                            }
                            Spacer(Modifier.height(4.dp))
                            Text(
                                "Rata-rata: ${"%.1f".format(rataRata)}",
                                style = MaterialTheme.typography.titleSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}
