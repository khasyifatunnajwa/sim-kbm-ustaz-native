package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface BukuSakuDao {

    @Query("SELECT * FROM buku_saku ORDER BY kategori ASC, judul ASC")
    fun observeAll(): Flow<List<BukuSakuEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(buku: BukuSakuEntity)

    @Delete
    suspend fun delete(buku: BukuSakuEntity)
}
