package com.bustanululum.simkbmnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.room.withTransaction
import com.bustanululum.simkbmnative.data.local.AppDatabase
import com.bustanululum.simkbmnative.data.local.KelasEntity
import com.bustanululum.simkbmnative.data.local.MuridEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)

        setContent {
            MaterialTheme(colorScheme = lightColorScheme()) {
                NativeHomeScreen(database)
            }
        }
    }
}

@Composable
private fun NativeHomeScreen(db: AppDatabase) {
    var halaman by remember { mutableStateOf("Beranda") }
    var kelasTerpilih by remember { mutableStateOf<String?>(null) }
    var tampilDialogKelas by remember { mutableStateOf(false) }
    var tampilDialogMurid by remember { mutableStateOf(false) }
    var pesanError by remember { mutableStateOf<String?>(null) }

    val kelasList by db.kelasDao().observeAll()
        .collectAsState(initial = emptyList())

    val semuaMurid by db.muridDao().observeAll()
        .collectAsState(initial = emptyList())

    val antrean by db.syncQueueDao().observePendingCount()
        .collectAsState(initial = 0)

    val scope = rememberCoroutineScope()
    val kelasAktif = kelasList.firstOrNull { it.localId == kelasTerpilih }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf("Beranda", "Kelas", "Murid", "Sinkronisasi").forEach { menu ->
                    NavigationBarItem(
                        selected = halaman == menu,
                        onClick = {
                            halaman = menu
                            pesanError = null
                        },
                        icon = {
                            Text(
                                when (menu) {
                                    "Beranda" -> "⌂"
                                    "Kelas" -> "▤"
                                    "Murid" -> "♙"
                                    else -> "↻"
                                }
                            )
                        },
                        label = { Text(menu) }
                    )
                }
            }
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {
            Text(
                text = "SIM KBM USTAZ",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = "Sistem Informasi Kegiatan Belajar Mengajar",
                fontSize = 13.sp
            )

            Spacer(modifier = Modifier.height(20.dp))

            when (halaman) {
                "Beranda" -> {
                    Text(
                        "Assalamu'alaikum, Ustaz!",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(6.dp))
                    Text("Ringkasan data aplikasi")

                    Spacer(modifier = Modifier.height(14.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        RingkasanCard(
                            judul = "Jumlah Kelas",
                            jumlah = kelasList.size.toString(),
                            modifier = Modifier.weight(1f)
                        )
                        RingkasanCard(
                            judul = "Jumlah Murid",
                            jumlah = semuaMurid.size.toString(),
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    RingkasanCard(
                        judul = "Menunggu Sinkronisasi",
                        jumlah = antrean.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    Text(
                        "Menu Utama",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    MenuCard("🏫  Data Kelas", "Tambah dan kelola kelas") {
                        halaman = "Kelas"
                    }

                    MenuCard("👨‍🎓  Data Murid", "Lihat murid berdasarkan kelas") {
                        halaman = "Murid"
                    }

                    MenuCard("🔄  Status Sinkronisasi", "Periksa antrean perubahan data") {
                        halaman = "Sinkronisasi"
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        "Penyimpanan lokal aktif. Data kelas dan murid disimpan di perangkat.",
                        fontSize = 13.sp
                    )
                }

                "Kelas" -> {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            "Data Kelas",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Button(onClick = { tampilDialogKelas = true }) {
                            Text("+ Tambah")
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text("Jumlah kelas: ${kelasList.size}")

                    Spacer(modifier = Modifier.height(12.dp))

                    if (kelasList.isEmpty()) {
                        PesanKosong(
                            "Belum ada kelas. Tekan Tambah untuk membuat kelas pertama."
                        )
                    }

                    kelasList.forEach { kelas ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 5.dp)
                                .clickable {
                                    kelasTerpilih = kelas.localId
                                    halaman = "Murid"
                                }
                        ) {
                            Column(modifier = Modifier.padding(15.dp)) {
                                Text(
                                    kelas.nama,
                                    fontSize = 17.sp,
                                    fontWeight = FontWeight.Bold
                                )
                                Text("Lembaga: ${kelas.lembaga}")
                                Text(
                                    "Jenis kelamin: ${kelas.jenisKelamin ?: "Belum ditentukan"}"
                                )
                                Text(
                                    "Status data: ${kelas.syncStatus}",
                                    fontSize = 12.sp
                                )
                                Spacer(modifier = Modifier.height(5.dp))
                                Text(
                                    "Tekan untuk melihat murid",
                                    color = MaterialTheme.colorScheme.primary,
                                    fontSize = 13.sp
                                )
                            }
                        }
                    }
                }

                "Murid" -> {
                    Text(
                        "Data Murid",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    if (kelasList.isEmpty()) {
                        PesanKosong(
                            "Buat kelas terlebih dahulu melalui menu Kelas."
                        )
                        Button(onClick = { halaman = "Kelas" }) {
                            Text("Buka Data Kelas")
                        }
                    } else {
                        Text("Pilih kelas:")

                        kelasList.forEach { kelas ->
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp)
                                    .clickable {
                                        kelasTerpilih = kelas.localId
                                    }
                            ) {
                                Row(
                                    modifier = Modifier.padding(12.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(
                                            kelas.nama,
                                            fontWeight = FontWeight.SemiBold
                                        )
                                        Text(kelas.lembaga, fontSize = 12.sp)
                                    }
                                    Text(
                                        if (kelasTerpilih == kelas.localId) {
                                            "✓ Dipilih"
                                        } else {
                                            "Pilih"
                                        },
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(12.dp))

                        if (kelasAktif != null) {
                            Text(
                                "Murid di ${kelasAktif.nama}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(modifier = Modifier.height(8.dp))

                            Button(
                                onClick = { tampilDialogMurid = true },
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text("+ Tambah Murid")
                            }

                            val muridKelas = semuaMurid.filter {
                                it.kelasLocalId == kelasAktif.localId
                            }

                            if (muridKelas.isEmpty()) {
                                PesanKosong("Belum ada murid di kelas ini.")
                            }

                            muridKelas.forEach { murid ->
                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp)
                                ) {
                                    Column(modifier = Modifier.padding(12.dp)) {
                                        Text(
                                            murid.nama,
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp
                                        )
                                        Text(
                                            "Jenis kelamin: ${
                                                murid.jenisKelamin ?: "Belum ditentukan"
                                            }"
                                        )
                                        Text(
                                            "Status data: ${murid.syncStatus}",
                                            fontSize = 12.sp
                                        )

                                        TextButton(
                                            onClick = {
                                                scope.launch {
                                                    try {
                                                        db.withTransaction {
                                                            db.syncQueueDao().enqueue(
                                                                SyncQueueEntity(
                                                                    entityType = "murid",
                                                                    localId = murid.localId,
                                                                    operation = "DELETE",
                                                                    payloadJson = JSONObject()
                                                                        .put("localId", murid.localId)
                                                                        .put("serverId", murid.serverId)
                                                                        .toString()
                                                                )
                                                            )
                                                            db.muridDao().delete(murid)
                                                        }
                                                    } catch (e: Exception) {
                                                        pesanError =
                                                            "Gagal menghapus murid: ${e.message}"
                                                    }
                                                }
                                            }
                                        ) {
                                            Text(
                                                "Hapus murid",
                                                color = MaterialTheme.colorScheme.error
                                            )
                                        }
                                    }
                                }
                            }
                        } else {
                            PesanKosong("Pilih kelas untuk melihat daftar murid.")
                        }
                    }
                }

                "Sinkronisasi" -> {
                    Text(
                        "Sinkronisasi Data",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    RingkasanCard(
                        judul = "Perubahan Menunggu Sinkronisasi",
                        jumlah = antrean.toString(),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Data baru disimpan di perangkat terlebih dahulu. " +
                            "Antrean mencatat perubahan yang perlu dikirim ke server."
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Text(
                        "Status: Sinkronisasi Supabase belum diaktifkan.",
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Jangan menghapus data aplikasi sebelum data penting " +
                            "dicadangkan. Antrean yang tercatat belum berarti " +
                            "data sudah terkirim ke Supabase."
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                "SIM KBM Ustaz Native · Versi 1.0.0",
                fontSize = 11.sp
            )
        }
    }

    if (tampilDialogKelas) {
        DialogTambahKelas(
            onTutup = { tampilDialogKelas = false },
            onSimpan = { nama, lembaga, gender ->
                scope.launch {
                    try {
                        val kelas = KelasEntity(
                            nama = nama,
                            lembaga = lembaga,
                            jenisKelamin = gender.ifBlank { null }
                        )

                        db.withTransaction {
                            db.kelasDao().upsert(kelas)
                            db.syncQueueDao().enqueue(
                                SyncQueueEntity(
                                    entityType = "kelas",
                                    localId = kelas.localId,
                                    operation = "UPSERT",
                                    payloadJson = JSONObject()
                                        .put("localId", kelas.localId)
                                        .put("nama", kelas.nama)
                                        .put("lembaga", kelas.lembaga)
                                        .put("jenisKelamin", kelas.jenisKelamin)
                                        .put("updatedAt", kelas.updatedAt)
                                        .toString()
                                )
                            )
                        }
                        tampilDialogKelas = false
                    } catch (e: Exception) {
                        pesanError = "Gagal menyimpan kelas: ${e.message}"
                    }
                }
            }
        )
    }

    if (tampilDialogMurid) {
        val kelas = kelasAktif

        if (kelas != null) {
            DialogTambahMurid(
                namaKelas = kelas.nama,
                onTutup = { tampilDialogMurid = false },
                onSimpan = { nama, gender ->
                    scope.launch {
                        try {
                            val murid = MuridEntity(
                                kelasLocalId = kelas.localId,
                                nama = nama,
                                jenisKelamin = gender.ifBlank { null }
                            )

                            db.withTransaction {
                                db.muridDao().upsert(murid)
                                db.syncQueueDao().enqueue(
                                    SyncQueueEntity(
                                        entityType = "murid",
                                        localId = murid.localId,
                                        operation = "UPSERT",
                                        payloadJson = JSONObject()
                                            .put("localId", murid.localId)
                                            .put("kelasLocalId", murid.kelasLocalId)
                                            .put("nama", murid.nama)
                                            .put("jenisKelamin", murid.jenisKelamin)
                                            .put("updatedAt", murid.updatedAt)
                                            .toString()
                                    )
                                )
                            }
                            tampilDialogMurid = false
                        } catch (e: Exception) {
                            pesanError = "Gagal menyimpan murid: ${e.message}"
                        }
                    }
                }
            )
        }
    }

    pesanError?.let { pesan ->
        AlertDialog(
            onDismissRequest = { pesanError = null },
            title = { Text("Terjadi Kesalahan") },
            text = { Text(pesan) },
            confirmButton = {
                TextButton(onClick = { pesanError = null }) {
                    Text("Tutup")
                }
            }
        )
    }
}

@Composable
private fun RingkasanCard(
    judul: String,
    jumlah: String,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(judul, fontSize = 13.sp)
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                jumlah,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun MenuCard(
    judul: String,
    keterangan: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 5.dp)
            .clickable(onClick = onClick)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(judul, fontWeight = FontWeight.Bold, fontSize = 16.sp)
            Spacer(modifier = Modifier.height(4.dp))
            Text(keterangan, fontSize = 13.sp)
        }
    }
}

@Composable
private fun PesanKosong(pesan: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            pesan,
            modifier = Modifier.padding(16.dp)
        )
    }
}

@Composable
private fun DialogTambahKelas(
    onTutup: () -> Unit,
    onSimpan: (String, String, String) -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var lembaga by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Kelas") },
        text = {
            Column {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama kelas") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = lembaga,
                    onValueChange = { lembaga = it },
                    label = { Text("Nama lembaga") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Jenis kelamin (Banin/Banat)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank() && lembaga.isNotBlank(),
                onClick = {
                    onSimpan(nama.trim(), lembaga.trim(), gender.trim())
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onTutup) {
                Text("Batal")
            }
        }
    )
}

@Composable
private fun DialogTambahMurid(
    namaKelas: String,
    onTutup: () -> Unit,
    onSimpan: (String, String) -> Unit
) {
    var nama by remember { mutableStateOf("") }
    var gender by remember { mutableStateOf("") }

    AlertDialog(
        onDismissRequest = onTutup,
        title = { Text("Tambah Murid") },
        text = {
            Column {
                Text("Kelas: $namaKelas")
                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama lengkap murid") },
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Jenis kelamin (Banin/Banat)") },
                    singleLine = true
                )
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank(),
                onClick = {
                    onSimpan(nama.trim(), gender.trim())
                }
            ) {
                Text("Simpan")
            }
        },
        dismissButton = {
            TextButton(onClick = onTutup) {
                Text("Batal")
            }
        }
    )
}
