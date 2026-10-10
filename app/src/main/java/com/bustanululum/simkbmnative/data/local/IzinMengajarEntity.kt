package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "izin_mengajar",
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
data class IzinMengajarEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val kelasLocalId: String,
    val tanggal: String,
    val alasan: String,
    val pengganti: String? = null,
    val status: String = "DIAJUKAN",
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
