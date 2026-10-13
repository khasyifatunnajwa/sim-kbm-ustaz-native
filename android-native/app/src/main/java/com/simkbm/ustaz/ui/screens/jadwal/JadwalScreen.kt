package com.simkbm.ustaz.ui.screens.jadwal

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
fun JadwalScreen() {
    var showForm by remember { mutableStateOf(false) }
    var selectedHari by remember { mutableStateOf("") }
    val hariList = listOf("Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu")

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showForm = true },
                containerColor = Emerald600,
                contentColor = androidx.compose.ui.graphics.Color.White
            ) {
                Icon(Icons.Default.Add, contentDescription = "Tambah")
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text("Jadwal Mengajar", fontWeight = FontWeight.Bold, fontSize = 18.sp, color = Slate800)
            Spacer(modifier = Modifier.height(12.dp))

            // Filter chips
            Row(
                modifier = Modifier.horizontalScroll(androidx.compose.foundation.rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = selectedHari.isEmpty(),
                    onClick = { selectedHari = "" },
                    label = { Text("Semua") }
                )
                hariList.forEach { hari ->
                    FilterChip(
                        selected = selectedHari == hari,
                        onClick = { selectedHari = hari },
                        label = { Text(hari) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Empty state
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 1.dp
            ) {
                Column(
                    modifier = Modifier.padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(Icons.Default.EventBusy, contentDescription = null, modifier = Modifier.size(48.dp), tint = Slate300)
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("Belum ada jadwal", color = Slate500, fontSize = 14.sp)
                    Text("Tap tombol + untuk menambah jadwal", color = Slate400, fontSize = 12.sp)
                }
            }
        }
    }

    if (showForm) {
        JadwalFormDialog(
            onDismiss = { showForm = false },
            onSave = { showForm = false }
        )
    }
}

@Composable
fun JadwalFormDialog(onDismiss: () -> Unit, onSave: () -> Unit) {
    var hari by remember { mutableStateOf("Senin") }
    var jamMulai by remember { mutableStateOf("07:00") }
    var jamSelesai by remember { mutableStateOf("08:00") }
    var kelas by remember { mutableStateOf("") }
    var pelajaran by remember { mutableStateOf("") }
    var ruangan by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Tambah Jadwal", fontWeight = FontWeight.Bold) },
        text = {
            Column(
                modifier = Modifier.verticalScroll(androidx.compose.foundation.rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedTextField(
                    value = kelas,
                    onValueChange = { kelas = it },
                    label = { Text("Kelas") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = pelajaran,
                    onValueChange = { pelajaran = it },
                    label = { Text("Mata Pelajaran") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
                OutlinedTextField(
                    value = ruangan,
                    onValueChange = { ruangan = it },
                    label = { Text("Ruangan (opsional)") },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onSave,
                colors = ButtonDefaults.buttonColors(containerColor = Emerald600)
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Batal") }
        }
    )
}
