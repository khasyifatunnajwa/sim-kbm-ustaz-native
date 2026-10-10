package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(
    tableName = "nilai",
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
data class NilaiEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val muridLocalId: String,
    val mataPelajaran: String,
    val jenisNilai: String,
    val nilai: Double,
    val tanggal: String,
    val keterangan: String? = null,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
