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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Badge
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.bustanululum.simkbmnative.ui.EmeraldDark
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.ModernCard
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium

@Composable
fun ProfilScreen() {
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
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Box(
                    modifier = Modifier
                        .size(72.dp)
                        .clip(CircleShape)
                        .background(Color.White.copy(alpha = 0.2f)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Filled.Person, contentDescription = null, tint = Color.White, modifier = Modifier.size(40.dp))
                }
                Spacer(Modifier.height(12.dp))
                Text("Ustaz", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Pengajar", fontSize = 14.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Informasi Pengguna", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                    Spacer(Modifier.height(12.dp))

                    ProfileRow(
                        icon = Icons.Filled.Person,
                        label = "Nama",
                        value = "Ustaz"
                    )
                    Spacer(Modifier.height(10.dp))
                    ProfileRow(
                        icon = Icons.Filled.Badge,
                        label = "Peran",
                        value = "Pengajar"
                    )
                    Spacer(Modifier.height(10.dp))
                    ProfileRow(
                        icon = Icons.Filled.CheckCircle,
                        label = "Status",
                        value = "Aktif"
                    )
                    Spacer(Modifier.height(10.dp))
                    ProfileRow(
                        icon = Icons.Filled.School,
                        label = "Login",
                        value = "Belum tersedia"
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Filled.Info, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        Spacer(Modifier.width(8.dp))
                        Text("Tentang Aplikasi", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "SIM KBM Ustaz adalah aplikasi Sistem Informasi " +
                            "Kegiatan Belajar Mengajar untuk membantu ustaz " +
                            "mengelola kelas, murid, absensi, jurnal, jadwal, " +
                            "nilai, dan berbagai catatan kegiatan mengajar.",
                        fontSize = 13.sp,
                        color = SlateMedium,
                        lineHeight = 20.sp
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            ModernCard(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(Icons.Filled.School, contentDescription = null, tint = EmeraldDark, modifier = Modifier.size(28.dp))
                    Spacer(Modifier.width(12.dp))
                    Column {
                        Text("SIM KBM Ustaz Native", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                        Text("Versi 1.0.0", fontSize = 12.sp, color = SlateLight)
                    }
                }
            }
        }
    }
}

@Composable
private fun ProfileRow(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String,
    value: String
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MintSurface),
            contentAlignment = Alignment.Center
        ) {
            Icon(icon, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(18.dp))
        }
        Spacer(Modifier.width(10.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(label, fontSize = 11.sp, color = SlateLight)
            Text(value, fontSize = 14.sp, fontWeight = FontWeight.SemiBold, color = SlateDark)
        }
    }
}
