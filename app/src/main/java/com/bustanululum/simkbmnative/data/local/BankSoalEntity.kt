package com.bustanululum.simkbmnative.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "bank_soal")
data class BankSoalEntity(
    @PrimaryKey
    val localId: String = UUID.randomUUID().toString(),

    val serverId: String? = null,
    val mataPelajaran: String,
    val pertanyaan: String,
    val pilihanA: String? = null,
    val pilihanB: String? = null,
    val pilihanC: String? = null,
    val pilihanD: String? = null,
    val jawabanBenar: String? = null,
    val tingkat: String? = null,
    val updatedAt: Long = System.currentTimeMillis(),
    val syncStatus: String = "PENDING"
)
