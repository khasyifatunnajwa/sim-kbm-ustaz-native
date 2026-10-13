package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

class MuridRepository(private val db: AppDatabase) {
    private val dao = db.muridDao()

    fun getAll(): Flow<List<MuridEntity>> = dao.getAll()

    fun getByKelas(kelasId: String): Flow<List<MuridEntity>> = dao.getByKelas(kelasId)

    fun search(query: String): Flow<List<MuridEntity>> = dao.search(query)

    suspend fun getById(id: String): MuridEntity? = dao.getById(id)

    suspend fun insert(murid: MuridEntity): String {
        val id = murid.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = murid.copy(
            id = id,
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.insert(entity)
        addToSyncQueue("murid", id, "INSERT", entity)
        return id
    }

    suspend fun update(murid: MuridEntity) {
        val entity = murid.copy(
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.update(entity)
        addToSyncQueue("murid", murid.id, "UPDATE", entity)
    }

    suspend fun delete(id: String) {
        dao.softDelete(id)
        addToSyncQueue("murid", id, "DELETE", null)
    }

    suspend fun getPendingSync(): List<MuridEntity> = dao.getPendingSync()

    private suspend fun addToSyncQueue(
        tableName: String,
        recordId: String,
        operation: String,
        data: Any?
    ) {
        val queue = SyncQueueEntity(
            table_name = tableName,
            record_id = recordId,
            operation = operation,
            data_json = data?.toString() ?: "{}"
        )
        db.syncQueueDao().insert(queue)
    }
}
