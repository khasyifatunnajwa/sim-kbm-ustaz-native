package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

class KelasRepository(private val db: AppDatabase) {
    private val dao = db.kelasDao()

    fun getAll(): Flow<List<KelasEntity>> = dao.getAll()

    suspend fun getById(id: String): KelasEntity? = dao.getById(id)

    suspend fun insert(kelas: KelasEntity): String {
        val id = kelas.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = kelas.copy(
            id = id,
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.insert(entity)
        // Add to sync queue
        addToSyncQueue("kelas", id, "INSERT", entity)
        return id
    }

    suspend fun update(kelas: KelasEntity) {
        val entity = kelas.copy(
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.update(entity)
        addToSyncQueue("kelas", kelas.id, "UPDATE", entity)
    }

    suspend fun delete(id: String) {
        dao.softDelete(id)
        addToSyncQueue("kelas", id, "DELETE", null)
    }

    suspend fun getPendingSync(): List<KelasEntity> = dao.getPendingSync()

    suspend fun markAsSynced(id: String) {
        val entity = dao.getById(id)
        entity?.let {
            dao.update(it.copy(sync_status = "synced"))
        }
    }

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
