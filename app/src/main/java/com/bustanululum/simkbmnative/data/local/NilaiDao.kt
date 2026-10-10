package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NilaiDao {

    @Query("SELECT * FROM nilai ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<NilaiEntity>>

    @Query("SELECT * FROM nilai WHERE muridLocalId = :muridLocalId ORDER BY tanggal DESC")
    fun observeByMurid(muridLocalId: String): Flow<List<NilaiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(nilai: NilaiEntity)

    @Delete
    suspend fun delete(nilai: NilaiEntity)
}
