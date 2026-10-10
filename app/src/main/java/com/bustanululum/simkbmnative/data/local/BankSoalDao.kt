package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BankSoalDao {

    @Query("SELECT * FROM bank_soal ORDER BY mataPelajaran ASC")
    fun observeAll(): Flow<List<BankSoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(soal: BankSoalEntity)

    @Delete
    suspend fun delete(soal: BankSoalEntity)
}
