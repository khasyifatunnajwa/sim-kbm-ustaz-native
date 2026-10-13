# 📋 TAHAP 1: AUDIT LENGKAP SIM KBM USTAZ PWA

## 1.1 Inventarisasi Fitur

### A. AUTENTIKASI & OTORISASI
| # | Fitur | Status PWA | Prioritas Native |
|---|-------|-----------|-----------------|
| 1 | Login (ID Login / Email + Password) | ✅ Aktif | HIGH |
| 2 | Register akun baru | ✅ Aktif | HIGH |
| 3 | Role-based access (admin/operator/ustaz) | ✅ Aktif | HIGH |
| 4 | Session management via Supabase | ✅ Aktif | HIGH |
| 5 | Lookup email by ID Login (Edge Function) | ✅ Aktif | HIGH |
| 6 | Logout & clear session | ✅ Aktif | HIGH |

### B. NAVIGASI & LAYOUT
| # | Fitur | Status PWA | Prioritas Native |
|---|-------|-----------|-----------------|
| 7 | Bottom Navigation (5 tab: Dashboard, Jadwal, Absensi, Murid, Jurnal) | ✅ Aktif | HIGH |
| 8 | Sidebar Desktop (full navigation) | ✅ Aktif | MEDIUM |
| 9 | Sidebar Mobile (drawer) | ✅ Aktif | HIGH |
| 10 | Header dengan tanggal & nama halaman | ✅ Aktif | HIGH |
| 11 | Tombol Pengaturan & Profil di header | ✅ Aktif | HIGH |
| 12 | Dialog konfirmasi keluar | ✅ Aktif | MEDIUM |
| 13 | Animasi transisi halaman (Framer Motion) | ✅ Aktif | LOW |
| 14 | Toast notifications | ✅ Aktif | HIGH |
| 15 | Modal/Dialog system | ✅ Aktif | HIGH |

### C. HALAMAN & FITUR UTAMA
| # | Halaman | Fitur | Prioritas |
|---|---------|-------|-----------|
| 16 | Dashboard | Welcome card, statistik, quick menu, jadwal hari ini, pengumuman, agenda | HIGH |
| 17 | Jadwal Mengajar | CRUD jadwal, filter hari, tampilan per hari | HIGH |
| 18 | Kalender | Tampilan kalender bulanan, agenda, CRUD agenda | HIGH |
| 19 | Data Santri | CRUD santri, search, filter kelas, status | HIGH |
| 20 | Absensi Harian | Pilih kelas & tanggal, status per santri (Hadir/Izin/Sakit/Alfa/Telat) | HIGH |
| 21 | Jurnal KBM | CRUD jurnal, materi, target, realisasi, metode | HIGH |
| 22 | Nilai | CRUD nilai, filter mapel, jenis penilaian, skor | HIGH |
| 23 | Sikap | Penilaian 5 aspek (disiplin, adab, kerajinan, kejujuran, tanggung jawab) | HIGH |
| 24 | Catatan | CRUD catatan, kategori (Umum/Acara/Undangan/Agenda), status | MEDIUM |
| 25 | Bank Soal | CRUD soal, tingkat kesulitan, jawaban tersembunyi | MEDIUM |
| 26 | Izin Mengajar | CRUD izin, status (Diajukan/Disetujui/Ditolak), guru pengganti | MEDIUM |
| 27 | Rapor | Rekap nilai per santri, rata-rata per mapel | HIGH |
| 28 | Pengumuman | CRUD pengumuman, prioritas, aktif/nonaktif | HIGH |
| 29 | Panel Admin | Kelola kelas, statistik | HIGH |
| 30 | Profil | Edit profil (nama, email, WhatsApp) | HIGH |
| 31 | Pengaturan | Nama sekolah, tema, info aplikasi | MEDIUM |
| 32 | Buku Saku | Placeholder (belum implementasi penuh) | LOW |

### D. PENYIMPANAN DATA
| # | Fitur | Status PWA | Prioritas Native |
|---|-------|-----------|-----------------|
| 33 | Local Storage (Zustand persist) | ✅ Aktif | HIGH → Room DB |
| 34 | Supabase Backend (auth + data) | ✅ Aktif | HIGH |
| 35 | Sync offline-online | ⚠️ Belum | HIGH |

### E. FITUR KHUSUS BROWSER YANG PERLU PENGGANTI NATIVE
| # | Fitur Browser | Pengganti Native |
|---|---------------|-----------------|
| 36 | LocalStorage | Room Database |
| 37 | PWA Install Banner | Tidak diperlukan (native app) |
| 38 | Push Notification (Firebase) | Firebase Cloud Messaging (FCM) |
| 39 | Camera (untuk presensi foto) | CameraX API |
| 40 | GPS/Location | Fused Location Provider |
| 41 | File Download/Export | Storage Access Framework |
| 42 | WhatsApp Share | Intent.ACTION_SEND |
| 43 | PDF Export | Android Print Framework / PdfDocument |
| 44 | Back Button Navigation | OnBackPressedDispatcher |
| 45 | Pull to Refresh | SwipeRefreshLayout |

---

## 1.2 Analisis Skema Database (dari types/index.ts)

### Tabel Utama yang Digunakan:
1. **profiles** - Data pengguna (ustaz/admin/operator)
2. **kelas** - Data kelas
3. **murid** - Data santri
4. **jadwal_mengajar** - Jadwal mengajar
5. **absensi** - Absensi harian
6. **jurnal_kbm** - Jurnal KBM
7. **penilaian** + **detail_nilai** - Nilai santri
8. **sikap** - Penilaian sikap
9. **catatan_guru** - Catatan guru
10. **soal** / **bank_soal** - Bank soal
11. **izin_mengajar** - Izin mengajar
12. **pengumuman** - Pengumuman
13. **agenda** - Agenda penting
14. **pengaturan** - Pengaturan aplikasi
15. **tahun_ajaran** & **semester** - Periode akademik
16. **kalender_pendidikan** - Kalender pendidikan
17. **mata_pelajaran** - Data mapel

---

## 1.3 Ringkasan Statistik

- **Total Fitur:** 45 fitur
- **Halaman:** 17 halaman
- **Tabel Database:** 17+ tabel
- **Role Pengguna:** 3 (admin, operator, ustaz)
- **Prioritas HIGH:** 22 fitur
- **Prioritas MEDIUM:** 10 fitur
- **Prioritas LOW:** 3 fitur
