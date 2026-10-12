// Stub screens for remaining features
// Each screen follows the same pattern as DashboardScreen

package com.simkbm.ustaz.ui.screens.jurnal

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JurnalScreen() {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {}, containerColor = Emerald600, contentColor = androidx.compose.ui.graphics.Color.White) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.fillMaxSize().padding(padding).padding(16.dp)) {
            Text("Jurnal KBM", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Slate800)
            Spacer(modifier = Modifier.height(16.dp))
            Surface(modifier = Modifier.fillMaxWidth(), shape = RoundedCornerShape(16.dp), color = MaterialTheme.colorScheme.surface, shadowElevation = 1.dp) {
                Column(modifier = Modifier.padding(32.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(Icons.Default.Description, contentDescription = null, modifier = Modifier.size(48.dp), tint = Slate300)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Belum ada jurnal", color = Slate500, fontSize = 14.sp)
                }
            }
        }
    }
}
