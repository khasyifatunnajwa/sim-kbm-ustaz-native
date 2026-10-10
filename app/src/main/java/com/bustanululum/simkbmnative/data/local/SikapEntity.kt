package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "sikap",
    foreignKeys = [
        ForeignKey(
            entity = MuridEntity::class,
            parentColumns = ["localId"],
            childColumns = ["muridLocalId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index("muridLocalId")]
)
data class SikapEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val muridLocalId: String,
    val tanggal: String,
    val kategori: String,
    val deskripsi: String,
    val penilaian: String,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
