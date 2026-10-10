package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface IzinMengajarDao {

    @Query("SELECT * FROM izin_mengajar ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<IzinMengajarEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(izin: IzinMengajarEntity)

    @Delete
    suspend fun delete(izin: IzinMengajarEntity)
}
