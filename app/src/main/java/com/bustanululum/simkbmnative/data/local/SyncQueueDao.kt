package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SyncQueueDao {

    @Query(
        "SELECT * FROM sync_queue " +
        "ORDER BY createdAt ASC"
    )
    fun observeAll(): Flow<List<SyncQueueEntity>>

    @Query(
        "SELECT * FROM sync_queue " +
        "ORDER BY createdAt ASC LIMIT :limit"
    )
    suspend fun getPending(limit: Int = 50): List<SyncQueueEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun enqueue(item: SyncQueueEntity)

    @Query("DELETE FROM sync_queue WHERE queueId = :queueId")
    suspend fun remove(queueId: String)

    @Query("SELECT COUNT(*) FROM sync_queue")
    fun observePendingCount(): Flow<Int>
}
