package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JadwalDao {

    @Query("SELECT * FROM jadwal ORDER BY hari ASC, jamMulai ASC")
    fun observeAll(): Flow<List<JadwalEntity>>

    @Query("SELECT * FROM jadwal WHERE kelasLocalId = :kelasLocalId ORDER BY hari ASC, jamMulai ASC")
    fun observeByKelas(kelasLocalId: String): Flow<List<JadwalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(jadwal: JadwalEntity)

    @Delete
    suspend fun delete(jadwal: JadwalEntity)
}
