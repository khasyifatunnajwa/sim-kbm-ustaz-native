package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface JurnalKBMDao {

    @Query("SELECT * FROM jurnal_kbm ORDER BY tanggal DESC")
    fun observeAll(): Flow<List<JurnalKBMEntity>>

    @Query("SELECT * FROM jurnal_kbm WHERE kelasLocalId = :kelasLocalId ORDER BY tanggal DESC")
    fun observeByKelas(kelasLocalId: String): Flow<List<JurnalKBMEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(jurnal: JurnalKBMEntity)

    @Delete
    suspend fun delete(jurnal: JurnalKBMEntity)
}
