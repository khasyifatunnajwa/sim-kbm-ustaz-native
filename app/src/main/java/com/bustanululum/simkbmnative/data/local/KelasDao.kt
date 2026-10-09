package com.bustanululum.simkbmnative.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface KelasDao {

    @Query("SELECT * FROM kelas ORDER BY nama ASC")
    fun observeAll(): Flow<List<KelasEntity>>

    @Query("SELECT * FROM kelas WHERE localId = :localId LIMIT 1")
    suspend fun getByLocalId(localId: String): KelasEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(kelas: KelasEntity)

    @Delete
    suspend fun delete(kelas: KelasEntity)
}
