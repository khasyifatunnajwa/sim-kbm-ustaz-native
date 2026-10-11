package com.bustanululum.simkbmnative.ui

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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Event
import androidx.compose.material.icons.filled.Grades
import androidx.compose.material.icons.filled.LibraryBooks
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.School
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.filled.Task
import androidx.compose.material.icons.filled.TimeToLeave
import androidx.compose.material.icons.filled.Tune
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

val EmeraldPrimary = Color(0xFF059669)
val EmeraldDark = Color(0xFF047857)
val EmeraldLight = Color(0xFF10B981)
val TealAccent = Color(0xFF0D9488)
val MintSurface = Color(0xFFECFDF5)
val SlateDark = Color(0xFF1E293B)
val SlateMedium = Color(0xFF475569)
val SlateLight = Color(0xFF94A3B8)
val SurfaceGray = Color(0xFFF8FAFC)
val CardWhite = Color(0xFFFFFFFF)
val ErrorRed = Color(0xFFDC2626)
val WarningAmber = Color(0xFFF59E0B)
val InfoBlue = Color(0xFF0284C7)

val GradientHeader = Brush.linearGradient(
    colors = listOf(EmeraldDark, TealAccent)
)

val GradientCard = Brush.linearGradient(
    colors = listOf(EmeraldPrimary, TealAccent)
)

val GradientSurface = Brush.verticalGradient(
    colors = listOf(MintSurface, SurfaceGray)
)

object MenuIcons {
    val kelas: ImageVector = Icons.Filled.School
    val murid: ImageVector = Icons.Filled.People
    val absensi: ImageVector = Icons.Filled.Checklist
    val jurnal: ImageVector = Icons.Filled.MenuBook
    val jadwal: ImageVector = Icons.Filled.Schedule
    val nilai: ImageVector = Icons.Filled.Grades
    val rapor: ImageVector = Icons.Filled.Task
    val agenda: ImageVector = Icons.Filled.Event
    val pengumuman: ImageVector = Icons.Filled.Notifications
    val sikap: ImageVector = Icons.Filled.Tune
    val tahunAjaran: ImageVector = Icons.Filled.CalendarMonth
    val bankSoal: ImageVector = Icons.Filled.LibraryBooks
    val izinMengajar: ImageVector = Icons.Filled.TimeToLeave
    val bukuSaku: ImageVector = Icons.Filled.MenuBook
    val sinkronisasi: ImageVector = Icons.Filled.Sync
    val pengaturan: ImageVector = Icons.Filled.Settings
    val profil: ImageVector = Icons.Filled.Person
    val cloudSync: ImageVector = Icons.Filled.CloudSync
}

@Composable
fun GradientHeaderBox(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp))
            .background(GradientHeader)
            .padding(24.dp)
    ) {
        Column {
            Text(
                title,
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(6.dp))
            Text(
                subtitle,
                fontSize = 14.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
        }
    }
}

@Composable
fun StatCard(
    label: String,
    value: String,
    icon: ImageVector,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(MintSurface),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        icon,
                        contentDescription = null,
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Text(
                    value,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = EmeraldDark
                )
            }
            Spacer(Modifier.height(8.dp))
            Text(
                label,
                fontSize = 13.sp,
                color = SlateMedium
            )
        }
    }
}

@Composable
fun MenuTile(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
                    .background(GradientCard),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(26.dp)
                )
            }
            Spacer(Modifier.height(10.dp))
            Text(
                title,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = SlateDark
            )
            Spacer(Modifier.height(2.dp))
            Text(
                subtitle,
                fontSize = 11.sp,
                color = SlateLight
            )
        }
    }
}

@Composable
fun SectionHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    Text(
        text,
        fontSize = 16.sp,
        fontWeight = FontWeight.Bold,
        color = SlateDark,
        modifier = modifier
    )
}

@Composable
fun ModernCard(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = CardWhite),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
    ) {
        content()
    }
}
