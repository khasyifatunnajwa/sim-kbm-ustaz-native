package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AbsensiDao {

    @Query(
        "SELECT * FROM absensi " +
        "WHERE tanggal = :tanggal " +
        "ORDER BY updatedAt DESC"
    )
    fun observeByTanggal(
        tanggal: String
    ): Flow<List<AbsensiEntity>>

    @Query(
        "SELECT * FROM absensi " +
        "WHERE muridLocalId = :muridLocalId " +
        "AND tanggal = :tanggal LIMIT 1"
    )
    suspend fun getByMuridTanggal(
        muridLocalId: String,
        tanggal: String
    ): AbsensiEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(absensi: AbsensiEntity)

    @Query(
        "SELECT COUNT(*) FROM absensi " +
        "WHERE tanggal = :tanggal AND status = :status"
    )
    fun observeJumlahStatus(
        tanggal: String,
        status: String
    ): Flow<Int>
}
