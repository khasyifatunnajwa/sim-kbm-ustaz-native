package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PengumumanDao {

    @Query("SELECT * FROM pengumuman ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<PengumumanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(pengumuman: PengumumanEntity)

    @Delete
    suspend fun delete(pengumuman: PengumumanEntity)
}
