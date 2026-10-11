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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.BukuSakuEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateMedium
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun BukuSakuScreen(db: AppDatabase) {
    var pesan by remember { mutableStateOf<String?>(null) }
    var tampilDialog by remember { mutableStateOf(false) }
    val bukuList by db.bukuSakuDao().observeAll()
        .collectAsState(initial = emptyList())
    val scope = rememberCoroutineScope()

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
                Text("Buku Saku", fontSize = 24.sp, fontWeight = FontWeight.Bold, color = Color.White)
                Spacer(Modifier.height(4.dp))
                Text("Referensi ringkas untuk ustaz", fontSize = 13.sp, color = Color.White.copy(alpha = 0.85f))
            }
        }

        Column(modifier = Modifier.padding(16.dp)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text("${bukuList.size} catatan", fontSize = 14.sp, color = SlateMedium)
                Button(
                    onClick = { tampilDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(4.dp))
                    Text("Tambah")
                }
            }

            Spacer(Modifier.height(16.dp))

            if (bukuList.isEmpty()) {
                EmptyState("Belum ada catatan buku saku.")
            }

            bukuList.forEach { buku ->
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
                                Icon(Icons.Filled.MenuBook, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                            }
                            Spacer(Modifier.width(10.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(buku.judul, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                                Text("Kategori: ${buku.kategori}", fontSize = 12.sp, color = SlateMedium)
                            }
                        }
                        Spacer(Modifier.height(8.dp))
                        Text(buku.isi, fontSize = 13.sp, color = SlateDark)
                        TextButton(onClick = {
                            scope.launch {
                                try {
                                    db.withTransaction {
                                        db.syncQueueDao().enqueue(
                                            SyncQueueEntity(
                                                entityType = "buku_saku",
                                                localId = buku.localId,
                                                operation = "DELETE",
                                                payloadJson = JSONObject()
                                                    .put("localId", buku.localId)
                                                    .put("serverId", buku.serverId)
                                                    .toString()
                                            )
                                        )
                                        db.bukuSakuDao().delete(buku)
                                    }
                                } catch (e: Exception) {
                                    pesan = "Gagal menghapus: ${e.message}"
                                }
                            }
                        }) {
                            Icon(Icons.Filled.Delete, contentDescription = null, tint = Color(0xFFDC2626), modifier = Modifier.size(16.dp))
                            Spacer(Modifier.width(4.dp))
                            Text("Hapus", color = Color(0xFFDC2626))
                        }
                    }
                }
            }
        }
    }

    if (tampilDialog) {
        DialogTambahBukuSaku(
            onTutup = { tampilDialog = false },
            onSimpan = { judul, kategori, isi ->
                scope.launch {
                    try {
                        val buku = BukuSakuEntity(judul = judul, kategori = kategori, isi = isi)
                        db.withTransaction {
                            db.bukuSakuDao().upsert(buku)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "buku_saku",
                                    localId = buku.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", buku.localId)
                                        .put("judul", buku.judul)
                                        .put("kategori", buku.kategori)
                                        .put("isi", buku.isi)
                                        .put("updatedAt", buku.updatedAt)
                                        .toString()
                                )
                            )
                        }
                        tampilDialog = false
                    } catch (e: Exception) {
                        pesan = "Gagal menyimpan: ${e.message}"
                    }
                }
            }
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
private fun DialogTambahBukuSaku(
    onTutup: () -> Unit,
    onSimpan: (String, String, String) -> Unit
) {
    var judul by remember { mutableStateOf("") }
    var kategori by remember { mutableStateOf("") }
    var isi by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Buku Saku", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(value = judul, onValueChange = { judul = it }, label = { Text("Judul") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = kategori, onValueChange = { kategori = it }, label = { Text("Kategori") }, singleLine = true, shape = RoundedCornerShape(12.dp))
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(value = isi, onValueChange = { isi = it }, label = { Text("Isi catatan") }, minLines = 3, shape = RoundedCornerShape(12.dp))
            }
        },
        confirmButton = {
            Button(
                enabled = judul.isNotBlank() && kategori.isNotBlank() && isi.isNotBlank(),
                onClick = { onSimpan(judul.trim(), kategori.trim(), isi.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = { TextButton(onClick = onTutup) { Text("Batal") } }
    )
}
