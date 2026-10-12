# 📘 PANDUAN LENGKAP: SIM KBM Ustaz Native

## ⚠️ PENTING: Keterbatasan AI Assistant

AI assistant yang Anda gunakan **TIDAK BISA**:
- ❌ Commit dan push ke GitHub
- ❌ Menjalankan GitHub Actions
- ❌ Generate file APK
- ❌ Akses terminal/Git

AI hanya bisa membuat file di environment web ini. **Anda harus melakukan langkah-langkah berikut secara manual.**

---

## 📦 Apa yang Sudah Dibuat

Aplikasi web lengkap dengan semua fitur SIM KBM Ustaz PWA:
- ✅ Dashboard
- ✅ Jadwal Mengajar
- ✅ Kalender
- ✅ Data Santri
- ✅ Absensi Harian
- ✅ Jurnal KBM
- ✅ Nilai
- ✅ Penilaian Sikap
- ✅ Catatan
- ✅ Bank Soal
- ✅ Izin Mengajar
- ✅ Rapor
- ✅ Pengumuman
- ✅ Panel Admin
- ✅ Profil
- ✅ Pengaturan

**Data tersimpan lokal di perangkat (localStorage)**

---

## 🚀 LANGKAH 1: Download & Push ke GitHub

### Cara 1: Download Manual (Recommended)

1. **Download project ini:**
   - Klik tombol "Download" di interface ini
   - Atau copy semua file secara manual

2. **Extract ke folder lokal**

3. **Buka terminal/command prompt** di folder project

4. **Initialize Git:**
   ```bash
   git init
   git add .
   git commit -m "Initial commit: SIM KBM Ustaz Native"
   ```

5. **Tambahkan remote repository:**
   ```bash
   git remote add origin https://github.com/khasyifatunnajwa/sim-kbm-ustaz-native.git
   ```

6. **Push ke GitHub:**
   ```bash
   git branch -M main
   git push -u origin main
   ```

### Cara 2: Upload via GitHub Web Interface

1. Buka https://github.com/khasyifatunnajwa/sim-kbm-ustaz-native
2. Klik "Add file" → "Upload files"
3. Upload semua file dari project ini
4. Commit changes

---

## 🔄 LANGKAH 2: GitHub Actions (Auto Build)

File workflow sudah tersedia di `.github/workflows/build.yml`

**Setelah push ke GitHub:**

1. Buka tab **Actions** di repository GitHub
2. Workflow akan otomatis berjalan
3. Tunggu hingga selesai (biasanya 2-5 menit)
4. Download artifact di tab Actions → "sim-kbm-ustaz-web"

**Untuk deploy ke GitHub Pages:**

1. Buka repository → Settings → Pages
2. Source: "GitHub Actions"
3. Pilih workflow yang sudah ada
4. Tunggu deploy selesai
5. Akses di: `https://khasyifatunnajwa.github.io/sim-kbm-ustaz-native/`

---

## 📱 LANGKAH 3: Membuat APK (PILIH SALAH SATU)

### Opsi A: Menggunakan Capacitor (Recommended untuk APK Native)

1. **Install Capacitor:**
   ```bash
   npm install @capacitor/core @capacitor/cli
   npm install @capacitor/android
   npx cap init "SIM KBM Ustaz" "com.simkbm.ustaz" --web-dir=dist
   ```

2. **Build web app:**
   ```bash
   npm run build
   ```

3. **Add Android platform:**
   ```bash
   npx cap add android
   ```

4. **Copy web files ke Android:**
   ```bash
   npx cap copy android
   ```

5. **Buka di Android Studio:**
   ```bash
   npx cap open android
   ```

6. **Build APK di Android Studio:**
   - Build → Build Bundle(s) / APK(s) → Build APK(s)
   - APK akan ada di: `android/app/build/outputs/apk/debug/app-debug.apk`

### Opsi B: Menggunakan PWA Builder (Lebih Mudah)

1. **Deploy web app ke URL publik:**
   - Gunakan GitHub Pages, Vercel, atau Netlify
   - Contoh: `https://sim-kbm-ustaz.vercel.app`

2. **Buka PWABuilder:**
   - https://www.pwabuilder.com/

3. **Enter URL:**
   - Masukkan URL web app Anda
   - Klik "Start"

4. **Download APK:**
   - Klik "Package for stores" → "Android"
   - Download APK

### Opsi C: Menggunakan Bubblewrap (TWA)

1. **Install Bubblewrap:**
   ```bash
   npm install -g @bubblewrap/cli
   ```

2. **Initialize project:**
   ```bash
   bubblewrap init --manifest=https://your-domain.com/manifest.json
   ```

3. **Build APK:**
   ```bash
   bubblewrap build
   ```

---

## 🔧 Konfigurasi Tambahan

### Untuk Capacitor (capacitor.config.json):

```json
{
  "appId": "com.simkbm.ustaz",
  "appName": "SIM KBM Ustaz",
  "webDir": "dist",
  "bundledWebRuntime": false,
  "android": {
    "allowMixedContent": true,
    "captureInput": true,
    "webContentsDebuggingEnabled": true
  }
}
```

### Untuk PWA (manifest.json):

```json
{
  "name": "SIM KBM Ustaz",
  "short_name": "SIM KBM",
  "description": "Sistem Informasi Manajemen KBM Ustaz",
  "start_url": "/",
  "display": "standalone",
  "background_color": "#ffffff",
  "theme_color": "#059669",
  "icons": [
    {
      "src": "/icon-192.png",
      "sizes": "192x192",
      "type": "image/png"
    },
    {
      "src": "/icon-512.png",
      "sizes": "512x512",
      "type": "image/png"
    }
  ]
}
```

---

## 📊 Struktur File Penting

```
sim-kbm-ustaz-native/
├── .github/
│   └── workflows/
│       └── build.yml          # GitHub Actions workflow
├── src/
│   ├── App.tsx                # Main app component
│   ├── main.tsx               # Entry point
│   ├── index.css              # Tailwind CSS
│   └── store/
│       └── useStore.ts        # Zustand store
├── public/                    # Static assets
├── index.html                 # HTML template
├── package.json               # Dependencies
├── vite.config.js             # Vite config
└── README.md                  # This file
```

---

## 🔐 Supabase Integration (Opsional)

Jika Anda ingin mengaktifkan sinkronisasi dengan Supabase:

1. **Buat project di Supabase:**
   - https://supabase.com/

2. **Dapatkan credentials:**
   - Project URL
   - Anon/Public Key

3. **Install Supabase client:**
   ```bash
   npm install @supabase/supabase-js
   ```

4. **Update file `src/lib/supabase.ts`:**
   ```typescript
   import { createClient } from '@supabase/supabase-js'
   
   const supabaseUrl = 'YOUR_SUPABASE_URL'
   const supabaseKey = 'YOUR_SUPABASE_ANON_KEY'
   
   export const supabase = createClient(supabaseUrl, supabaseKey)
   ```

5. **Update `src/App.tsx`** untuk menggunakan Supabase auth dan database

---

## 🐛 Troubleshooting

### Build Error di GitHub Actions:
- Pastikan `package.json` sudah benar
- Cek Node.js version (gunakan 18 atau 20)
- Hapus `node_modules` dan `package-lock.json`, lalu `npm install` ulang

### APK tidak bisa diinstall:
- Enable "Unknown sources" di Android settings
- Cek minimum SDK version (min 21 / Android 5.0)
- Pastikan APK ditandatangani dengan benar

### Data tidak tersimpan:
- Cek browser localStorage (F12 → Application → Local Storage)
- Pastikan tidak ada error di console
- Clear cache dan reload

---

## 📞 Bantuan Lebih Lanjut

Jika ada masalah:
1. Cek GitHub Actions logs di tab Actions
2. Cek browser console (F12)
3. Cek repository issues di GitHub
4. Hubungi support

---

## ✅ Checklist

- [ ] Download project
- [ ] Push ke GitHub
- [ ] GitHub Actions build berhasil
- [ ] Deploy ke GitHub Pages (opsional)
- [ ] Install Capacitor/PWABuilder
- [ ] Build APK
- [ ] Test APK di device
- [ ] (Opsional) Setup Supabase untuk sync

---

**Catatan:** Aplikasi ini menggunakan localStorage untuk penyimpanan data. Untuk multi-device sync, Anda perlu mengintegrasikan backend seperti Supabase, Firebase, atau custom API.

**Versi:** 2.0 Native  
**Last Updated:** 2026-10-12
