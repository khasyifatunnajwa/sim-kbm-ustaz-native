package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MuridDao {

    @Query("SELECT * FROM murid ORDER BY nama ASC")
    fun observeAll(): Flow<List<MuridEntity>>

    @Query(
        "SELECT * FROM murid " +
        "WHERE kelasLocalId = :kelasLocalId ORDER BY nama ASC"
    )
    fun observeByKelas(kelasLocalId: String): Flow<List<MuridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(murid: MuridEntity)

    @Delete
    suspend fun delete(murid: MuridEntity)
}
