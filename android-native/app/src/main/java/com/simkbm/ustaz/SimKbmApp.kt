package com.simkbm.ustaz

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.simkbm.ustaz.data.local.AppDatabase
import com.simkbm.ustaz.data.remote.SupabaseClient
import com.simkbm.ustaz.data.repository.*
import com.simkbm.ustaz.data.session.SessionManager
import com.simkbm.ustaz.data.sync.SyncWorker
import com.simkbm.ustaz.util.NetworkMonitor
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

class SimKbmApp : Application() {

    lateinit var database: AppDatabase
    lateinit var supabaseClient: SupabaseClient
    lateinit var sessionManager: SessionManager
    lateinit var networkMonitor: NetworkMonitor

    private val appModule = module {
        // Core
        single { database }
        single { supabaseClient }
        single { sessionManager }
        single { networkMonitor }

        // Repositories
        single { KelasRepository(get()) }
        single { MuridRepository(get()) }
        single { JadwalRepository(get()) }
        single { AbsensiRepository(get()) }
        single { JurnalRepository(get()) }
        single { NilaiRepository(get()) }
        single { SikapRepository(get()) }
        single { CatatanRepository(get()) }
        single { SoalRepository(get()) }
        single { IzinRepository(get()) }
        single { PengumumanRepository(get()) }
        single { AgendaRepository(get()) }
        single { ProfileRepository(get()) }
        single { SettingsRepository(get()) }

        // ViewModels
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.DashboardViewModel(get(), get(), get(), get(), get(), get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.JadwalViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.MuridViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.AbsensiViewModel(get(), get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.JurnalViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.NilaiViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.SikapViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.CatatanViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.SoalViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.IzinViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.PengumumanViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.viewmodels.AgendaViewModel(get()) }
        viewModel { com.simkbm.ustaz.ui.screens.auth.AuthViewModel(get(), get()) }
    }

    override fun onCreate() {
        super.onCreate()

        // Initialize Database
        database = AppDatabase.getInstance(this)

        // Initialize Supabase Client
        supabaseClient = SupabaseClient(
            url = BuildConfig.SUPABASE_URL,
            anonKey = BuildConfig.SUPABASE_ANON_KEY
        )

        // Initialize Session Manager
        sessionManager = SessionManager(this)

        // Initialize Network Monitor
        networkMonitor = NetworkMonitor(this)

        // Initialize Koin
        startKoin {
            androidLogger()
            androidContext(this@SimKbmApp)
            modules(appModule)
        }

        // Create notification channels
        createNotificationChannels()

        // Schedule sync work
        scheduleSyncWork()
    }

    private fun createNotificationChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationManager = getSystemService(NotificationManager::class.java)

            // Sync channel
            val syncChannel = NotificationChannel(
                CHANNEL_SYNC,
                "Sinkronisasi Data",
                NotificationManager.IMPORTANCE_LOW
            ).apply {
                description = "Notifikasi sinkronisasi data"
            }
            notificationManager.createNotificationChannel(syncChannel)

            // Pengumuman channel
            val pengumumanChannel = NotificationChannel(
                CHANNEL_PENGUMUMAN,
                "Pengumuman",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notifikasi pengumuman penting"
            }
            notificationManager.createNotificationChannel(pengumumanChannel)
        }
    }

    private fun scheduleSyncWork() {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val syncRequest = PeriodicWorkRequestBuilder<SyncWorker>(
            15, TimeUnit.MINUTES
        )
            .setConstraints(constraints)
            .setBackoffCriteria(
                BackoffPolicy.EXPONENTIAL,
                WorkRequest.MIN_BACKOFF_MILLIS,
                TimeUnit.MILLISECONDS
            )
            .build()

        WorkManager.getInstance(this).enqueueUniquePeriodicWork(
            "sim_kbm_sync",
            ExistingPeriodicWorkPolicy.KEEP,
            syncRequest
        )
    }

    companion object {
        const val CHANNEL_SYNC = "sync_channel"
        const val CHANNEL_PENGUMUMAN = "pengumuman_channel"
    }
}
