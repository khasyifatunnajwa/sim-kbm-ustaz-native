package com.bustanululum.simkbmnative

import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bustanululum.simkbmnative.data.local.AppDatabase

@Composable
fun PengaturanScreen(db: AppDatabase) {
    val antrean by db.syncQueueDao().observePendingCount()
        .collectAsState(initial = 0)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Text("Pengaturan", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Informasi Aplikasi",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Text("Nama: SIM KBM Ustaz Native")
                Text("Versi: 1.0.0")
                Text("Mode: Penyimpanan lokal (offline)")
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Sinkronisasi",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Text("Antrean menunggu sinkronisasi: $antrean")
                Text(
                    "Sinkronisasi Supabase belum diaktifkan.",
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        Card(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    "Catatan",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    "Data disimpan secara lokal di perangkat. " +
                        "Pastikan untuk tidak menghapus data aplikasi " +
                        "sebelum melakukan pencadangan."
                )
            }
        }
    }
}
