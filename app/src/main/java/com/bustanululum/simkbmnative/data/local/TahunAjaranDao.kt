package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface TahunAjaranDao {

    @Query("SELECT * FROM tahun_ajaran ORDER BY tanggalMulai DESC")
    fun observeAll(): Flow<List<TahunAjaranEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(tahun: TahunAjaranEntity)

    @Delete
    suspend fun delete(tahun: TahunAjaranEntity)
}
