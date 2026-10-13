# 📋 TAHAP 2: PEMETAAN FITUR PWA → ANDROID NATIVE

## 2.1 Arsitektur Android Native

```
┌─────────────────────────────────────────────────────────┐
│                    PRESENTATION LAYER                    │
│  (Jetpack Compose UI + ViewModel + StateFlow)           │
├─────────────────────────────────────────────────────────┤
│                    DOMAIN LAYER                          │
│  (Use Cases / Interactors)                              │
├─────────────────────────────────────────────────────────┤
│                     DATA LAYER                           │
│  ┌──────────────┐  ┌──────────────┐  ┌──────────────┐ │
│  │ Room Database│  │Supabase API  │  │   Sync       │ │
│  │  (Local)     │  │  (Remote)    │  │  Manager     │ │
│  └──────────────┘  └──────────────┘  └──────────────┘ │
├─────────────────────────────────────────────────────────┤
│                   INFRASTRUCTURE                         │
│  (WorkManager, FCM, CameraX, Location, Storage)        │
└─────────────────────────────────────────────────────────┘
```

## 2.2 Pemetaan Fitur Detail

### A. AUTENTIKASI
| PWA Feature | Android Native Implementation | File/Class |
|-------------|------------------------------|------------|
| Supabase Auth (Email/Password) | Supabase Kotlin SDK | `AuthRepository.kt` |
| Session Management | DataStore (secure token storage) | `SessionManager.kt` |
| Role-based Access | Sealed class UserRole + checks | `AuthViewModel.kt` |
| ID Login → Email lookup | Supabase Edge Function call | `AuthRepository.kt` |

### B. NAVIGASI
| PWA Feature | Android Native Implementation | File/Class |
|-------------|------------------------------|------------|
| Bottom Navigation (5 tab) | Compose Navigation + BottomNavBar | `MainScreen.kt` |
| Sidebar/Drawer | ModalDrawer (Compose) | `MainScreen.kt` |
| Header with date | TopAppBar + formatted date | `MainScreen.kt` |
| Page transitions | Compose animatedContent | `NavGraph.kt` |
| Back button handling | OnBackPressedCallback | `MainActivity.kt` |
| Toast notifications | Snackbar (Compose) | `MainScreen.kt` |
| Modal/Dialog | AlertDialog (Compose) | Reusable component |

### C. HALAMAN → COMPOSE SCREENS
| PWA Page | Android Screen | ViewModel | Repository |
|----------|---------------|-----------|------------|
| DashboardPage | `DashboardScreen.kt` | `DashboardViewModel.kt` | `DashboardRepository.kt` |
| JadwalPage | `JadwalScreen.kt` | `JadwalViewModel.kt` | `JadwalRepository.kt` |
| KalenderPage | `KalenderScreen.kt` | `KalenderViewModel.kt` | `AgendaRepository.kt` |
| MuridPage | `MuridScreen.kt` | `MuridViewModel.kt` | `MuridRepository.kt` |
| AbsensiPage | `AbsensiScreen.kt` | `AbsensiViewModel.kt` | `AbsensiRepository.kt` |
| JurnalPage | `JurnalScreen.kt` | `JurnalViewModel.kt` | `JurnalRepository.kt` |
| NilaiPage | `NilaiScreen.kt` | `NilaiViewModel.kt` | `NilaiRepository.kt` |
| SikapPage | `SikapScreen.kt` | `SikapViewModel.kt` | `SikapRepository.kt` |
| CatatanPage | `CatatanScreen.kt` | `CatatanViewModel.kt` | `CatatanRepository.kt` |
| SoalPage | `SoalScreen.kt` | `SoalViewModel.kt` | `SoalRepository.kt` |
| IzinPage | `IzinScreen.kt` | `IzinViewModel.kt` | `IzinRepository.kt` |
| RaporPage | `RaporScreen.kt` | `RaporViewModel.kt` | `RaporRepository.kt` |
| PengumumanPage | `PengumumanScreen.kt` | `PengumumanViewModel.kt` | `PengumumanRepository.kt` |
| AdminPage | `AdminScreen.kt` | `AdminViewModel.kt` | `KelasRepository.kt` |
| ProfilPage | `ProfilScreen.kt` | `ProfilViewModel.kt` | `ProfilRepository.kt` |
| PengaturanPage | `PengaturanScreen.kt` | `PengaturanViewModel.kt` | `SettingsRepository.kt` |

### D. DATA LAYER (Room Database)
| PWA Type | Room Entity | DAO |
|----------|-------------|-----|
| UserProfile | `ProfileEntity.kt` | `ProfileDao.kt` |
| Kelas | `KelasEntity.kt` | `KelasDao.kt` |
| Murid | `MuridEntity.kt` | `MuridDao.kt` |
| JadwalMengajar | `JadwalEntity.kt` | `JadwalDao.kt` |
| AbsensiRecord | `AbsensiEntity.kt` | `AbsensiDao.kt` |
| JurnalRecord | `JurnalEntity.kt` | `JurnalDao.kt` |
| NilaiRecord | `NilaiEntity.kt` | `NilaiDao.kt` |
| SikapRecord | `SikapEntity.kt` | `SikapDao.kt` |
| CatatanRecord | `CatatanEntity.kt` | `CatatanDao.kt` |
| SoalRecord | `SoalEntity.kt` | `SoalDao.kt` |
| IzinRecord | `IzinEntity.kt` | `IzinDao.kt` |
| PengumumanRecord | `PengumumanEntity.kt` | `PengumumanDao.kt` |
| AgendaRecord | `AgendaEntity.kt` | `AgendaDao.kt` |

### E. SYNC & OFFLINE
| Fitur | Implementation | File |
|-------|---------------|------|
| Change Queue | Room table `sync_queue` | `SyncQueueEntity.kt` |
| Conflict Resolution | Last-write-wins + merge | `SyncConflictResolver.kt` |
| Background Sync | WorkManager periodic | `SyncWorker.kt` |
| Network Detection | ConnectivityManager | `NetworkMonitor.kt` |
| Duplicate Prevention | UUID + server_id mapping | `SyncManager.kt` |

### F. FITUR NATIVE KHUSUS
| Fitur Browser | Android Native API | File |
|---------------|-------------------|------|
| Camera (presensi) | CameraX | `CameraScreen.kt` |
| GPS Location | FusedLocationProviderClient | `LocationHelper.kt` |
| PDF Export | PdfDocument API | `PdfExporter.kt` |
| WhatsApp Share | Intent.ACTION_SEND | `ShareHelper.kt` |
| File Storage | Storage Access Framework | `FileHelper.kt` |
| Push Notification | Firebase Cloud Messaging | `FCMService.kt` |
| Biometric Auth | BiometricPrompt | `BiometricHelper.kt` |

## 2.3 Struktur File Android yang Akan Dibuat

```
app/
├── build.gradle.kts
├── src/
│   ├── main/
│   │   ├── AndroidManifest.xml
│   │   ├── java/com/simkbm/ustaz/
│   │   │   ├── SimKbmApp.kt (Application class)
│   │   │   ├── MainActivity.kt
│   │   │   ├── di/ (Dependency Injection)
│   │   │   │   └── AppModule.kt
│   │   │   ├── data/
│   │   │   │   ├── local/
│   │   │   │   │   ├── AppDatabase.kt
│   │   │   │   │   ├── entity/ (13 entities)
│   │   │   │   │   ├── dao/ (13 DAOs)
│   │   │   │   │   └── Converters.kt
│   │   │   │   ├── remote/
│   │   │   │   │   ├── SupabaseClient.kt
│   │   │   │   │   ├── SupabaseApi.kt
│   │   │   │   │   └── dto/ (Data Transfer Objects)
│   │   │   │   ├── repository/ (15 repositories)
│   │   │   │   └── sync/
│   │   │   │       ├── SyncManager.kt
│   │   │   │       ├── SyncWorker.kt
│   │   │   │       └── SyncQueueEntity.kt
│   │   │   ├── domain/
│   │   │   │   ├── model/ (Domain models)
│   │   │   │   └── usecase/ (Use cases)
│   │   │   ├── ui/
│   │   │   │   ├── theme/
│   │   │   │   │   ├── Color.kt
│   │   │   │   │   ├── Theme.kt
│   │   │   │   │   └── Type.kt
│   │   │   │   ├── navigation/
│   │   │   │   │   ├── NavGraph.kt
│   │   │   │   │   └── Screen.kt
│   │   │   │   ├── components/ (Reusable UI)
│   │   │   │   │   ├── BottomNavBar.kt
│   │   │   │   │   ├── SideDrawer.kt
│   │   │   │   │   ├── TopBar.kt
│   │   │   │   │   ├── ToastSnackbar.kt
│   │   │   │   │   └── ModalDialog.kt
│   │   │   │   └── screens/ (17 screens)
│   │   │   │       ├── auth/
│   │   │   │       │   ├── LoginScreen.kt
│   │   │   │       │   └── AuthViewModel.kt
│   │   │   │       ├── dashboard/
│   │   │   │       ├── jadwal/
│   │   │   │       ├── murid/
│   │   │   │       ├── absensi/
│   │   │   │       ├── jurnal/
│   │   │   │       ├── nilai/
│   │   │   │       ├── sikap/
│   │   │   │       ├── catatan/
│   │   │   │       ├── soal/
│   │   │   │       ├── izin/
│   │   │   │       ├── rapor/
│   │   │   │       ├── kalender/
│   │   │   │       ├── pengumuman/
│   │   │   │       ├── admin/
│   │   │   │       ├── profil/
│   │   │   │       └── pengaturan/
│   │   │   └── util/
│   │   │       ├── DateUtils.kt
│   │   │       ├── NetworkMonitor.kt
│   │   │       └── Extensions.kt
│   │   └── res/
│   │       ├── values/
│   │       │   ├── strings.xml
│   │       │   ├── colors.xml
│   │       │   └── themes.xml
│   │       ├── drawable/
│   │       └── mipmap/
│   └── test/
├── build.gradle.kts (root)
├── settings.gradle.kts
├── gradle.properties
└── gradle/wrapper/
    └── gradle-wrapper.properties
```

## 2.4 Dependencies yang Diperlukan

```kotlin
// Core Android
androidx.core:core-ktx
androidx.lifecycle:lifecycle-runtime-ktx
androidx.activity:activity-compose

// Compose UI
androidx.compose.ui:ui
androidx.compose.material3:material3
androidx.compose.ui:ui-tooling-preview
androidx.navigation:navigation-compose

// Room Database
androidx.room:room-runtime
androidx.room:room-ktx
androidx.room:room-compiler (kapt)

// DataStore (secure storage)
androidx.datastore:datastore-preferences

// WorkManager
androidx.work:work-runtime-ktx

// Network
io.ktor:ktor-client-core
io.ktor:ktor-client-cio
io.ktor:ktor-client-content-negotiation
io.ktor:ktor-serialization-kotlinx-json

// Supabase (via PostgREST/Kong)
// Menggunakan Ktor client untuk REST API calls

// Dependency Injection
io.insert-koin:koin-android
io.insert-koin:koin-androidx-compose

// Camera
androidx.camera:camera-core
androidx.camera:camera-camera2
androidx.camera:camera-lifecycle
androidx.camera:camera-view

// Location
com.google.android.gms:play-services-location

// Firebase (Push Notifications)
com.google.firebase:firebase-messaging
com.google.firebase:firebase-bom

// Serialization
org.jetbrains.kotlinx:kotlinx-serialization-json

// Coroutines
org.jetbrains.kotlinx:kotlinx-coroutines-android
```

## 2.5 Rencana Implementasi Bertahap

### Phase 1: Foundation (Build System + Core)
1. Gradle configuration (build.gradle.kts, settings.gradle.kts)
2. AndroidManifest.xml
3. Application class + MainActivity
4. Theme (Color, Type, Theme)
5. Room Database + Entities + DAOs
6. Navigation setup

### Phase 2: Authentication
7. Supabase API client
8. Auth Repository + ViewModel
9. Login Screen
10. Session management

### Phase 3: Main Navigation + Layout
11. MainScreen with BottomNav + Drawer
12. TopAppBar
13. Reusable components (Modal, Toast)

### Phase 4: Core Features (Data CRUD)
14. Dashboard Screen
15. Murid (Santri) Screen
16. Jadwal Screen
17. Absensi Screen
18. Jurnal Screen

### Phase 5: Additional Features
19. Nilai Screen
20. Sikap Screen
21. Catatan Screen
22. Soal Screen
23. Izin Screen
24. Rapor Screen
25. Kalender Screen
26. Pengumuman Screen
27. Admin Screen
28. Profil Screen
29. Pengaturan Screen

### Phase 6: Sync & Offline
30. Sync Queue implementation
31. SyncWorker (WorkManager)
32. Conflict resolution
33. Network monitoring

### Phase 7: Native Features
34. Camera integration
35. Location/GPS
36. PDF Export
37. WhatsApp Share
38. Push Notifications (FCM)

### Phase 8: Testing & Build
39. GitHub Actions workflow
40. APK build verification
41. Bug fixes
