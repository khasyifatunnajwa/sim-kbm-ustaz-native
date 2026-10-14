# Peta Kode dan Menu SIM KBM Ustaz Native

## 1. Daftar file Kotlin

- `app/src/main/java/com/bustanululum/simkbmnative/AbsensiScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/AgendaScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/BankSoalScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/BukuSakuScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/IzinMengajarScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/JadwalScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/JurnalKBMScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/MainActivity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/NilaiScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/PengaturanScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/PengumumanScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/ProfilScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/RaporScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/SikapScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/TahunAjaranScreen.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/AbsensiDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/AbsensiEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/AgendaDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/AgendaEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/AppDatabase.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/BankSoalDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/BankSoalEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/BukuSakuDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/BukuSakuEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/IzinMengajarDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/IzinMengajarEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/JadwalDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/JadwalEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/JurnalKBMDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/JurnalKBMEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/KelasDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/KelasEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/MuridDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/MuridEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/NilaiDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/NilaiEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/PengumumanDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/PengumumanEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/SikapDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/SikapEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/SyncQueueDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/SyncQueueEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/TahunAjaranDao.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/data/local/TahunAjaranEntity.kt`
- `app/src/main/java/com/bustanululum/simkbmnative/ui/Theme.kt`

## 2. Layar dan fungsi Compose

### `app/src/main/java/com/bustanululum/simkbmnative/AbsensiScreen.kt`
- `AbsensiScreen`
- `EmptyState`

### `app/src/main/java/com/bustanululum/simkbmnative/AgendaScreen.kt`
- `AgendaScreen`
- `DialogTambahAgenda`

### `app/src/main/java/com/bustanululum/simkbmnative/BankSoalScreen.kt`
- `BankSoalScreen`
- `DialogTambahSoal`

### `app/src/main/java/com/bustanululum/simkbmnative/BukuSakuScreen.kt`
- `BukuSakuScreen`
- `DialogTambahBukuSaku`

### `app/src/main/java/com/bustanululum/simkbmnative/IzinMengajarScreen.kt`
- `IzinMengajarScreen`
- `DialogTambahIzin`

### `app/src/main/java/com/bustanululum/simkbmnative/JadwalScreen.kt`
- `JadwalScreen`
- `DialogTambahJadwal`

### `app/src/main/java/com/bustanululum/simkbmnative/JurnalKBMScreen.kt`
- `JurnalKBMScreen`
- `DialogTambahJurnal`

### `app/src/main/java/com/bustanululum/simkbmnative/MainActivity.kt`
- `SurfaceBox`
- `NativeHomeScreen`
- `BerandaPage`
- `KelasPage`
- `MuridPage`
- `SinkronisasiPage`
- `LainnyaPage`
- `EmptyStateMain`
- `HalamanFiturBelumTersedia`
- `DialogTambahKelas`
- `DialogTambahMurid`

### `app/src/main/java/com/bustanululum/simkbmnative/NilaiScreen.kt`
- `NilaiScreen`
- `DialogTambahNilai`

### `app/src/main/java/com/bustanululum/simkbmnative/PengaturanScreen.kt`
- `PengaturanScreen`

### `app/src/main/java/com/bustanululum/simkbmnative/PengumumanScreen.kt`
- `PengumumanScreen`
- `DialogTambahPengumuman`

### `app/src/main/java/com/bustanululum/simkbmnative/ProfilScreen.kt`
- `ProfilScreen`

### `app/src/main/java/com/bustanululum/simkbmnative/RaporScreen.kt`
- `RaporScreen`

### `app/src/main/java/com/bustanululum/simkbmnative/SikapScreen.kt`
- `SikapScreen`
- `DialogTambahSikap`

### `app/src/main/java/com/bustanululum/simkbmnative/TahunAjaranScreen.kt`
- `TahunAjaranScreen`
- `DialogTambahTahunAjaran`

### `app/src/main/java/com/bustanululum/simkbmnative/ui/Theme.kt`
- `GradientHeaderBox`
- `StatCard`
- `MenuTile`
- `SectionHeader`
- `ModernCard`

## 3. Kandidat rute, menu, dan navigasi

### `app/src/main/java/com/bustanululum/simkbmnative/AbsensiScreen.kt`
- Baris 71: `fun AbsensiScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/AgendaScreen.kt`
- Baris 62: `fun AgendaScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/BankSoalScreen.kt`
- Baris 57: `fun BankSoalScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/BukuSakuScreen.kt`
- Baris 57: `fun BukuSakuScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/IzinMengajarScreen.kt`
- Baris 61: `fun IzinMengajarScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/JadwalScreen.kt`
- Baris 60: `fun JadwalScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/JurnalKBMScreen.kt`
- Baris 62: `fun JurnalKBMScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/MainActivity.kt`
- Baris 52: `import androidx.compose.material3.NavigationBarItem`
- Baris 53: `import androidx.compose.material3.NavigationBarItemDefaults`
- Baris 112: `NativeHomeScreen(database)`
- Baris 128: `private fun NativeHomeScreen(db: AppDatabase) {`
- Baris 183: `NavigationBarItem(`
- Baris 203: `colors = NavigationBarItemDefaults.colors(`
- Baris 271: `"Absensi" -> AbsensiScreen(db)`
- Baris 272: `"Jurnal KBM" -> JurnalKBMScreen(db)`
- Baris 273: `"Jadwal Mengajar" -> JadwalScreen(db)`
- Baris 274: `"Nilai" -> NilaiScreen(db)`
- Baris 275: `"Rapor" -> RaporScreen(db)`
- Baris 276: `"Agenda" -> AgendaScreen(db)`
- Baris 277: `"Pengumuman" -> PengumumanScreen(db)`
- Baris 278: `"Sikap" -> SikapScreen(db)`
- Baris 279: `"Tahun Ajaran & Semester" -> TahunAjaranScreen(db)`
- Baris 280: `"Bank Soal" -> BankSoalScreen(db)`
- Baris 281: `"Izin Mengajar" -> IzinMengajarScreen(db)`
- Baris 282: `"Buku Saku" -> BukuSakuScreen(db)`
- Baris 283: `"Pengaturan" -> PengaturanScreen(db)`
- Baris 284: `"Profil" -> ProfilScreen()`

### `app/src/main/java/com/bustanululum/simkbmnative/NilaiScreen.kt`
- Baris 64: `fun NilaiScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/PengaturanScreen.kt`
- Baris 21: `fun PengaturanScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/PengumumanScreen.kt`
- Baris 39: `fun PengumumanScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/ProfilScreen.kt`
- Baris 20: `fun ProfilScreen() {`

### `app/src/main/java/com/bustanululum/simkbmnative/RaporScreen.kt`
- Baris 47: `fun RaporScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/SikapScreen.kt`
- Baris 66: `fun SikapScreen(db: AppDatabase) {`

### `app/src/main/java/com/bustanululum/simkbmnative/TahunAjaranScreen.kt`
- Baris 38: `fun TahunAjaranScreen(db: AppDatabase) {`

## 4. Manifest dan resource XML

- `app/src/main/AndroidManifest.xml`

### Isi AndroidManifest.xml
```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:allowBackup="false"
        android:label="SIM KBM Ustaz Native"
        android:theme="@android:style/Theme.Material.Light.NoActionBar"
        android:usesCleartextTraffic="false">

        <activity
            android:name=".MainActivity"
            android:exported="true">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
    </application>
</manifest>

```

## Catatan
- Peta ini dibuat dari kode yang tersedia di repository.
- Nama fungsi belum tentu berarti fitur sudah berfungsi penuh.
- Periksa implementasi, penyimpanan lokal, sinkronisasi, dan navigasi sebelum menyatakan fitur selesai.
