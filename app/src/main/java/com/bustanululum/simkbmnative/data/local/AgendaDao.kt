package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AgendaDao {

    @Query("SELECT * FROM agenda ORDER BY tanggal ASC")
    fun observeAll(): Flow<List<AgendaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(agenda: AgendaEntity)

    @Delete
    suspend fun delete(agenda: AgendaEntity)
}
