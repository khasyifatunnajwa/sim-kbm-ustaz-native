package com.simkbm.ustaz.data.sync

import android.content.Context
import androidx.work.*
import com.simkbm.ustaz.SimKbmApp
import com.simkbm.ustaz.data.local.AppDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.util.concurrent.TimeUnit

class SyncWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val database = (applicationContext as SimKbmApp).database
    private val supabaseClient = (applicationContext as SimKbmApp).supabaseClient

    override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
        try {
            // Get pending sync items
            val syncQueue = database.syncQueueDao()
            val pendingItems = syncQueue.getPending(50)

            if (pendingItems.isEmpty()) {
                return@withContext Result.success()
            }

            // Process each pending item
            for (item in pendingItems) {
                try {
                    when (item.operation) {
                        "INSERT" -> {
                            // Insert to Supabase
                            // supabaseClient.insertData(item.table_name, item.data_json, accessToken)
                        }
                        "UPDATE" -> {
                            // Update in Supabase
                            // supabaseClient.updateData(item.table_name, item.record_id, item.data_json, accessToken)
                        }
                        "DELETE" -> {
                            // Delete from Supabase
                            // supabaseClient.deleteData(item.table_name, item.record_id, accessToken)
                        }
                    }
                    // Remove from queue after successful sync
                    syncQueue.delete(item.queue_id)
                } catch (e: Exception) {
                    // Increment retry count
                    syncQueue.incrementRetry(item.queue_id, e.message ?: "Unknown error")
                }
            }

            Result.success()
        } catch (e: Exception) {
            Result.retry()
        }
    }

    companion object {
        const val WORK_NAME = "sim_kbm_sync_worker"

        fun schedule(context: Context) {
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

            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                WORK_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                syncRequest
            )
        }
    }
}
