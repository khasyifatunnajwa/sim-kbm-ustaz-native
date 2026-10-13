package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

class JurnalRepository(private val db: AppDatabase) {
    private val dao = db.jurnalDao()

    fun getAll(): Flow<List<JurnalEntity>> = dao.getAll()

    suspend fun insert(jurnal: JurnalEntity): String {
        val id = jurnal.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = jurnal.copy(
            id = id,
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.insert(entity)
        addToSyncQueue("jurnal", id, "INSERT", entity)
        return id
    }

    suspend fun update(jurnal: JurnalEntity) {
        val entity = jurnal.copy(
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.update(entity)
        addToSyncQueue("jurnal", jurnal.id, "UPDATE", entity)
    }

    suspend fun delete(id: String) {
        dao.delete(id)
        addToSyncQueue("jurnal", id, "DELETE", null)
    }

    suspend fun getPendingSync(): List<JurnalEntity> = dao.getPendingSync()

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
