package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "kelas")
data class KelasEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val nama: String,
    val lembaga: String,
    val jenisKelamin: String? = null,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
