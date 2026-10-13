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
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.WarningAmber
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.bustanululum.simkbmnative.ui.ModernCard
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium
import com.bustanululum.simkbmnative.ui.WarningAmber as WarningAmberColor

@Composable
fun PengaturanScreen(db: AppDatabase) {
    val antrean by db.syncQueueDao().observePendingCount()
        .collectAsState(initial = 0)

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
                Text("Pengaturan", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Konfigurasi dan informasi aplikasi", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            SettingSectionCard(
                icon = Icons.Filled.Info,
                title = "Informasi Aplikasi",
                items = listOf(
                    "Nama" to "SIM KBM Ustaz Native",
                    "Versi" to "1.0.0",
                    "Mode" to "Penyimpanan lokal (offline)"
                )
            )

            Spacer(Modifier.height(12.dp))

            SettingSectionCard(
                icon = Icons.Filled.Sync,
                title = "Sinkronisasi",
                items = listOf(
                    "Antrean menunggu" to "$antrean perubahan",
                    "Status" to "Supabase belum diaktifkan"
                )
            )

            Spacer(Modifier.height(12.dp))

            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.Storage, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("Penyimpanan Lokal", fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                        Spacer(Modifier.height(4.dp))
                        Text(
                            "Data tersimpan di perangkat. Pastikan tidak menghapus data aplikasi sebelum melakukan pencadangan.",
                            fontSize = 12.sp,
                            color = SlateMedium
                        )
                    }
                }
            }

            Spacer(Modifier.height(12.dp))

            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.WarningAmber, contentDescription = null, tint = WarningAmberColor, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Cadangkan data secara berkala untuk menghindari kehilangan informasi.",
                        fontSize = 12.sp,
                        color = SlateMedium
                    )
                }
            }
        }
    }
}

@Composable
private fun SettingSectionCard(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    title: String,
    items: List<Pair<String, String>>
) {
    ModernCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MintSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(icon, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                }
                Spacer(Modifier.width(10.dp))
                Text(title, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
            }
            Spacer(Modifier.height(12.dp))
            items.forEach { (label, value) ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(label, fontSize = 13.sp, color = SlateMedium)
                    Text(value, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
                }
            }
        }
    }
}
