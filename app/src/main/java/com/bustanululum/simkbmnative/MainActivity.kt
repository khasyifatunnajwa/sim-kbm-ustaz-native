package com.bustanululum.simkbmnative

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarMonth
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Checklist
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.CloudSync
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.GridView
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
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
import com.bustanululum.simkbmnative.data.local.KelasEntity
import com.bustanululum.simkbmnative.data.local.MuridEntity
import com.bustanululum.simkbmnative.data.local.SyncQueueEntity
import com.bustanululum.simkbmnative.ui.EmeraldDark
import com.bustanululum.simkbmnative.ui.EmeraldPrimary
import com.bustanululum.simkbmnative.ui.GradientHeader
import com.bustanululum.simkbmnative.ui.MenuIcons
import com.bustanululum.simkbmnative.ui.MenuTile
import com.bustanululum.simkbmnative.ui.ModernCard
import com.bustanululum.simkbmnative.ui.MintSurface
import com.bustanululum.simkbmnative.ui.SectionHeader
import com.bustanululum.simkbmnative.ui.SlateDark
import com.bustanululum.simkbmnative.ui.SlateLight
import com.bustanululum.simkbmnative.ui.SlateMedium
import com.bustanululum.simkbmnative.ui.StatCard
import kotlinx.coroutines.launch
import org.json.JSONObject

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database = AppDatabase.getInstance(applicationContext)

        setContent {
            MaterialTheme(
                colorScheme = androidx.compose.material3.lightColorScheme(
                    primary = EmeraldPrimary,
                    onPrimary = Color.White,
                    primaryContainer = EmeraldDark,
                    onPrimaryContainer = Color.White,
                    secondary = EmeraldDark,
                    background = Color(0xFFF8FAFC),
                    surface = Color.White,
                    onSurface = SlateDark
                )
            ) {
                SurfaceBox {
                    NativeHomeScreen(database)
                }
            }
        }
    }
}

@Composable
private fun SurfaceBox(content: @Composable () -> Unit) {
    androidx.compose.material3.Surface(
        color = Color(0xFFF8FAFC),
        content = content
    )
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

    val menuUtama = listOf(
        MenuEntry("Data Kelas", "Kelola kelas", "Kelas", MenuIcons.kelas),
        MenuEntry("Data Murid", "Daftar murid", "Murid", MenuIcons.murid),
        MenuEntry("Absensi", "Kehadiran harian", "Absensi", MenuIcons.absensi),
        MenuEntry("Jurnal KBM", "Catatan mengajar", "Jurnal KBM", MenuIcons.jurnal),
        MenuEntry("Jadwal", "Jadwal mengajar", "Jadwal Mengajar", MenuIcons.jadwal),
        MenuEntry("Nilai", "Input nilai", "Nilai", MenuIcons.nilai),
        MenuEntry("Rapor", "Rekap nilai", "Rapor", MenuIcons.rapor),
        MenuEntry("Agenda", "Agenda kegiatan", "Agenda", MenuIcons.agenda),
        MenuEntry("Pengumuman", "Info & pengumuman", "Pengumuman", MenuIcons.pengumuman),
        MenuEntry("Sikap", "Penilaian sikap", "Sikap", MenuIcons.sikap),
        MenuEntry("Tahun Ajaran", "Periode belajar", "Tahun Ajaran & Semester", MenuIcons.tahunAjaran),
        MenuEntry("Bank Soal", "Kumpulan soal", "Bank Soal", MenuIcons.bankSoal),
        MenuEntry("Izin Mengajar", "Catatan izin", "Izin Mengajar", MenuIcons.izinMengajar),
        MenuEntry("Buku Saku", "Referensi ringkas", "Buku Saku", MenuIcons.bukuSaku),
        MenuEntry("Sinkronisasi", "Antrean data", "Sinkronisasi", MenuIcons.sinkronisasi),
        MenuEntry("Pengaturan", "Konfigurasi", "Pengaturan", MenuIcons.pengaturan),
        MenuEntry("Profil", "Profil pengguna", "Profil", MenuIcons.profil)
    )

    val bottomNavItems = listOf(
        BottomNavItem("Beranda", Icons.Filled.Home),
        BottomNavItem("Absensi", Icons.Filled.Checklist),
        BottomNavItem("Jurnal KBM", Icons.Filled.MenuBook),
        BottomNavItem("Jadwal Mengajar", Icons.Filled.Schedule),
        BottomNavItem("Lainnya", Icons.Filled.GridView)
    )

    Scaffold(
        bottomBar = {
            NavigationBar(
                containerColor = Color.White,
                tonalElevation = 8.dp
            ) {
                bottomNavItems.forEach { item ->
                    val selected = halaman == item.label
                    NavigationBarItem(
                        selected = selected,
                        onClick = {
                            halaman = item.label
                            pesanError = null
                        },
                        icon = {
                            Icon(
                                item.icon,
                                contentDescription = item.label,
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                item.label,
                                fontSize = 11.sp,
                                fontWeight = if (selected) FontWeight.SemiBold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = EmeraldPrimary,
                            selectedTextColor = EmeraldPrimary,
                            unselectedIconColor = SlateLight,
                            unselectedTextColor = SlateLight,
                            indicatorColor = MintSurface
                        )
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
        ) {
            when (halaman) {
                "Beranda" -> BerandaPage(
                    kelasList = kelasList,
                    semuaMurid = semuaMurid,
                    antrean = antrean,
                    menuUtama = menuUtama,
                    onMenuClick = { menu -> halaman = menu }
                )

                "Kelas" -> KelasPage(
                    kelasList = kelasList,
                    onTambah = { tampilDialogKelas = true },
                    onKelasClick = { id ->
                        kelasTerpilih = id
                        halaman = "Murid"
                    }
                )

                "Murid" -> MuridPage(
                    kelasList = kelasList,
                    kelasTerpilih = kelasTerpilih,
                    onPilihKelas = { kelasTerpilih = it },
                    kelasAktif = kelasAktif,
                    semuaMurid = semuaMurid,
                    onTambah = { tampilDialogMurid = true },
                    onHapus = { murid ->
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
                                pesanError = "Gagal menghapus murid: ${e.message}"
                            }
                        }
                    },
                    onBukaKelas = { halaman = "Kelas" }
                )

                "Absensi" -> AbsensiScreen(db)
                "Jurnal KBM" -> JurnalKBMScreen(db)
                "Jadwal Mengajar" -> JadwalScreen(db)
                "Nilai" -> NilaiScreen(db)
                "Rapor" -> RaporScreen(db)
                "Agenda" -> AgendaScreen(db)
                "Pengumuman" -> PengumumanScreen(db)
                "Sikap" -> SikapScreen(db)
                "Tahun Ajaran & Semester" -> TahunAjaranScreen(db)
                "Bank Soal" -> BankSoalScreen(db)
                "Izin Mengajar" -> IzinMengajarScreen(db)
                "Buku Saku" -> BukuSakuScreen(db)
                "Pengaturan" -> PengaturanScreen(db)
                "Profil" -> ProfilScreen()

                "Sinkronisasi" -> SinkronisasiPage(antrean = antrean)

                "Lainnya" -> LainnyaPage(
                    menuUtama = menuUtama,
                    onMenuClick = { menu -> halaman = menu }
                )

                else -> HalamanFiturBelumTersedia(
                    namaMenu = halaman,
                    onKembali = { halaman = "Lainnya" }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                "SIM KBM Ustaz Native  v1.0.0",
                fontSize = 11.sp,
                color = SlateLight,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 4.dp)
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
                TextButton(onClick = { pesanError = null }) { Text("Tutup") }
            }
        )
    }
}

private data class MenuEntry(
    val title: String,
    val subtitle: String,
    val target: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

private data class BottomNavItem(
    val label: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@Composable
private fun BerandaPage(
    kelasList: List<com.bustanululum.simkbmnative.data.local.KelasEntity>,
    semuaMurid: List<com.bustanululum.simkbmnative.data.local.MuridEntity>,
    antrean: Int,
    menuUtama: List<MenuEntry>,
    onMenuClick: (String) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(bottomStart = 28.dp, bottomEnd = 28.dp))
            .background(GradientHeader)
            .padding(24.dp)
    ) {
        Column {
            Text(
                "SIM KBM USTAZ",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Spacer(Modifier.height(4.dp))
            Text(
                "Sistem Informasi Kegiatan Belajar Mengajar",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.85f)
            )
            Spacer(Modifier.height(16.dp))
            Text(
                "Assalamu'alaikum, Ustaz!",
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.White
            )
            Spacer(Modifier.height(2.dp))
            Text(
                "Ringkasan data aplikasi",
                fontSize = 13.sp,
                color = Color.White.copy(alpha = 0.8f)
            )
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            StatCard(
                label = "Jumlah Kelas",
                value = kelasList.size.toString(),
                icon = MenuIcons.kelas,
                modifier = Modifier.weight(1f)
            )
            StatCard(
                label = "Jumlah Murid",
                value = semuaMurid.size.toString(),
                icon = MenuIcons.murid,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(Modifier.height(12.dp))

        StatCard(
            label = "Menunggu Sinkronisasi",
            value = antrean.toString(),
            icon = MenuIcons.cloudSync,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(20.dp))

        SectionHeader("Menu Utama")
        Spacer(Modifier.height(12.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height((((menuUtama.size + 2) / 3) * 130).dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(menuUtama) { menu ->
                MenuTile(
                    title = menu.title,
                    subtitle = menu.subtitle,
                    icon = menu.icon,
                    onClick = { onMenuClick(menu.target) }
                )
            }
        }

        Spacer(Modifier.height(16.dp))

        ModernCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Filled.Info,
                    contentDescription = null,
                    tint = SlateMedium,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(Modifier.width(10.dp))
                Text(
                    "Penyimpanan lokal aktif. Data tersimpan di perangkat.",
                    fontSize = 12.sp,
                    color = SlateMedium
                )
            }
        }
    }
}

@Composable
private fun KelasPage(
    kelasList: List<com.bustanululum.simkbmnative.data.local.KelasEntity>,
    onTambah: () -> Unit,
    onKelasClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Data Kelas", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                Text("${kelasList.size} kelas terdaftar", fontSize = 13.sp, color = SlateMedium)
            }
            Button(
                onClick = onTambah,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                Spacer(Modifier.width(4.dp))
                Text("Tambah")
            }
        }

        Spacer(Modifier.height(16.dp))

        if (kelasList.isEmpty()) {
            EmptyState("Belum ada kelas. Tekan Tambah untuk membuat kelas pertama.")
        }

        kelasList.forEach { kelas ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onKelasClick(kelas.localId) },
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
            ) {
                Row(
                    modifier = Modifier.padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(MintSurface),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            MenuIcons.kelas,
                            contentDescription = null,
                            tint = EmeraldPrimary,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(kelas.nama, fontSize = 16.sp, fontWeight = FontWeight.Bold, color = SlateDark)
                        Text("Lembaga: ${kelas.lembaga}", fontSize = 12.sp, color = SlateMedium)
                        Text(
                            "Jenis kelamin: ${kelas.jenisKelamin ?: "Belum ditentukan"}",
                            fontSize = 12.sp,
                            color = SlateMedium
                        )
                    }
                    Icon(
                        Icons.Filled.ArrowBack,
                        contentDescription = "Lihat murid",
                        tint = EmeraldPrimary,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun MuridPage(
    kelasList: List<com.bustanululum.simkbmnative.data.local.KelasEntity>,
    kelasTerpilih: String?,
    onPilihKelas: (String) -> Unit,
    kelasAktif: com.bustanululum.simkbmnative.data.local.KelasEntity?,
    semuaMurid: List<com.bustanululum.simkbmnative.data.local.MuridEntity>,
    onTambah: () -> Unit,
    onHapus: (com.bustanululum.simkbmnative.data.local.MuridEntity) -> Unit,
    onBukaKelas: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Data Murid", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SlateDark)

        Spacer(Modifier.height(16.dp))

        if (kelasList.isEmpty()) {
            EmptyState("Buat kelas terlebih dahulu melalui menu Kelas.")
            Spacer(Modifier.height(12.dp))
            Button(
                onClick = onBukaKelas,
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Buka Data Kelas") }
        } else {
            Text("Pilih kelas:", fontSize = 14.sp, color = SlateMedium)
            Spacer(Modifier.height(8.dp))

            kelasList.forEach { kelas ->
                val selected = kelasTerpilih == kelas.localId
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 3.dp)
                        .clickable { onPilihKelas(kelas.localId) },
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = if (selected) MintSurface else Color.White
                    ),
                    elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(kelas.nama, fontWeight = FontWeight.SemiBold, color = SlateDark, fontSize = 15.sp)
                            Text(kelas.lembaga, fontSize = 12.sp, color = SlateMedium)
                        }
                        if (selected) {
                            Icon(Icons.Filled.CheckCircle, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(20.dp))
                        }
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            if (kelasAktif != null) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Murid di ${kelasAktif.nama}",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = SlateDark
                    )
                    Button(
                        onClick = onTambah,
                        colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Icon(Icons.Filled.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(Modifier.width(4.dp))
                        Text("Tambah")
                    }
                }

                Spacer(Modifier.height(12.dp))

                val muridKelas = semuaMurid.filter { it.kelasLocalId == kelasAktif.localId }

                if (muridKelas.isEmpty()) {
                    EmptyState("Belum ada murid di kelas ini.")
                }

                muridKelas.forEach { murid ->
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 3.dp),
                        shape = RoundedCornerShape(14.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White),
                        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
                    ) {
                        Row(
                            modifier = Modifier.padding(14.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .background(MintSurface),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(MenuIcons.profil, contentDescription = null, tint = EmeraldPrimary, modifier = Modifier.size(22.dp))
                            }
                            Spacer(Modifier.width(12.dp))
                            Column(modifier = Modifier.weight(1f)) {
                                Text(murid.nama, fontWeight = FontWeight.Bold, fontSize = 15.sp, color = SlateDark)
                                Text("Jenis kelamin: ${murid.jenisKelamin ?: "Belum ditentukan"}", fontSize = 12.sp, color = SlateMedium)
                                Text("Status: ${murid.syncStatus}", fontSize = 11.sp, color = SlateLight)
                            }
                            IconButton(onClick = { onHapus(murid) }) {
                                Icon(Icons.Filled.Delete, contentDescription = "Hapus", tint = Color(0xFFDC2626), modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            } else {
                EmptyState("Pilih kelas untuk melihat daftar murid.")
            }
        }
    }
}

@Composable
private fun SinkronisasiPage(antrean: Int) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Sinkronisasi Data", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SlateDark)
        Spacer(Modifier.height(16.dp))

        StatCard(
            label = "Perubahan Menunggu Sinkronisasi",
            value = antrean.toString(),
            icon = MenuIcons.sinkronisasi,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        ModernCard(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Filled.CloudOff, contentDescription = null, tint = SlateMedium, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Sinkronisasi Supabase belum diaktifkan", fontWeight = FontWeight.SemiBold, color = SlateDark, fontSize = 14.sp)
                }
                Spacer(Modifier.height(8.dp))
                Text(
                    "Data baru disimpan di perangkat terlebih dahulu. Antrean mencatat perubahan yang perlu dikirim ke server.",
                    fontSize = 13.sp,
                    color = SlateMedium
                )
            }
        }

        Spacer(Modifier.height(12.dp))

        ModernCard(modifier = Modifier.fillMaxWidth()) {
            Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Info, contentDescription = null, tint = Color(0xFFF59E0B), modifier = Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text(
                    "Jangan menghapus data aplikasi sebelum data dicadangkan.",
                    fontSize = 12.sp,
                    color = SlateMedium
                )
            }
        }
    }
}

@Composable
private fun LainnyaPage(
    menuUtama: List<MenuEntry>,
    onMenuClick: (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Semua Menu", fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SlateDark)
        Text("Pilih menu yang ingin dibuka", fontSize = 13.sp, color = SlateMedium)
        Spacer(Modifier.height(16.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(3),
            modifier = Modifier
                .fillMaxWidth()
                .height((((menuUtama.size + 2) / 3) * 130).dp),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items(menuUtama) { menu ->
                MenuTile(
                    title = menu.title,
                    subtitle = menu.subtitle,
                    icon = menu.icon,
                    onClick = { onMenuClick(menu.target) }
                )
            }
        }
    }
}

@Composable
private fun EmptyState(pesan: String) {
    ModernCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(Icons.Filled.Info, contentDescription = null, tint = SlateLight, modifier = Modifier.size(20.dp))
            Spacer(Modifier.width(10.dp))
            Text(pesan, fontSize = 13.sp, color = SlateMedium)
        }
    }
}

@Composable
private fun HalamanFiturBelumTersedia(
    namaMenu: String,
    onKembali: () -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(namaMenu, fontSize = 22.sp, fontWeight = FontWeight.Bold, color = SlateDark)
        Spacer(Modifier.height(12.dp))
        EmptyState("Fitur $namaMenu belum diimplementasikan.")
        Spacer(Modifier.height(12.dp))
        Button(
            onClick = onKembali,
            colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
            shape = RoundedCornerShape(12.dp)
        ) { Text("Kembali ke Semua Menu") }
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
        title = { Text("Tambah Kelas", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama kelas") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = lembaga,
                    onValueChange = { lembaga = it },
                    label = { Text("Nama lembaga") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Jenis kelamin (Banin/Banat)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank() && lembaga.isNotBlank(),
                onClick = { onSimpan(nama.trim(), lembaga.trim(), gender.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
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
        title = { Text("Tambah Murid", fontWeight = FontWeight.Bold) },
        text = {
            Column {
                Text("Kelas: $namaKelas", fontSize = 13.sp, color = SlateMedium)
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = nama,
                    onValueChange = { nama = it },
                    label = { Text("Nama lengkap murid") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                Spacer(Modifier.height(8.dp))
                OutlinedTextField(
                    value = gender,
                    onValueChange = { gender = it },
                    label = { Text("Jenis kelamin (Banin/Banat)") },
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }
        },
        confirmButton = {
            Button(
                enabled = nama.isNotBlank(),
                onClick = { onSimpan(nama.trim(), gender.trim()) },
                colors = ButtonDefaults.buttonColors(containerColor = EmeraldPrimary),
                shape = RoundedCornerShape(12.dp)
            ) { Text("Simpan") }
        },
        dismissButton = {
            TextButton(onClick = onTutup) { Text("Batal") }
        }
    )
}
