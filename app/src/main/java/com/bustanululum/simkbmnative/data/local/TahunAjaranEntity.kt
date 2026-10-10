package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "tahun_ajaran")
data class TahunAjaranEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val namaTahunAjaran: String,
    val semester: String,
    val tanggalMulai: String,
    val tanggalSelesai: String? = null,
    val aktif: Boolean = false,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
