package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SikapDao {

    @Query("SELECT * FROM sikap ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<SikapEntity>>

    @Query("SELECT * FROM sikap WHERE muridLocalId = :muridLocalId ORDER BY tanggal DESC")
    fun observeByMurid(muridLocalId: String): Flow<List<SikapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(sikap: SikapEntity)

    @Delete
    suspend fun delete(sikap: SikapEntity)
}
