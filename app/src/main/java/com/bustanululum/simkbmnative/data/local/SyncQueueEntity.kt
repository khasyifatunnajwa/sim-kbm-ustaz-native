package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey
    val queueId: String = UUID.randomUUID().toString(),

    val entityType: String,
    val localId: String,
    val operation: String,
    val payloadJson: String,
    val createdAt: Long = System.currentTimeMillis(),
    val attempts: Int = 0,
    val lastError: String? = null
)
