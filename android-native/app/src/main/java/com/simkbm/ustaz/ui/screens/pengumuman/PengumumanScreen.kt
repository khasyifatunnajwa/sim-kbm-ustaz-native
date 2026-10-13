package com.simkbm.ustaz.ui.screens.pengumuman

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simkbm.ustaz.ui.theme.*

@Composable
fun PengumumanScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text("Pengumuman", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Slate800)
        Spacer(modifier = Modifier.height(16.dp))
        Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface, shadowElevation = 1.dp) {
            Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(Icons.Default.Campaign, contentDescription = null, modifier = Modifier.size(48.dp), tint = Slate300)
                Spacer(modifier = Modifier.height(12.dp))
                Text("Belum ada pengumuman", color = Slate500, fontSize = 14.sp)
            }
        }
    }
}
