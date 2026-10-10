package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "jadwal",
    foreignKeys = [
        ForeignKey(
            entity = KelasEntity::class,
            parentColumns = ["localId"],
            childColumns = ["kelasLocalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("kelasLocalId")]
)
data class JadwalEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val kelasLocalId: String,
    val hari: String,
    val jamMulai: String,
    val jamSelesai: String,
    val mataPelajaran: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
