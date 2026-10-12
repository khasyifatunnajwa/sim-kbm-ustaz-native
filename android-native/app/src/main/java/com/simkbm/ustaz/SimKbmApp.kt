package com.simkbm.ustaz

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import androidx.work.*
import com.simkbm.ustaz.data.local.AppDatabase
import com.simkbm.ustaz.data.remote.SupabaseClient
import com.simkbm.ustaz.data.sync.SyncWorker
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.dsl.module
import java.util.concurrent.TimeUnit

class SimKbmApp : Application() {

    lateinit var database: AppDatabase
    lateinit var supabaseClient: SupabaseClient

    private val appModule = module {
        single { database }
        single { supabaseClient }
        // Repositories will be added here
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
