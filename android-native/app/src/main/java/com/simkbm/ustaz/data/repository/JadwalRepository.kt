package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

class JadwalRepository(private val db: AppDatabase) {
    private val dao = db.jadwalDao()

    fun getAll(): Flow<List<JadwalEntity>> = dao.getAll()

    fun getByHari(hari: String): Flow<List<JadwalEntity>> = dao.getByHari(hari)

    suspend fun insert(jadwal: JadwalEntity): String {
        val id = jadwal.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = jadwal.copy(
            id = id,
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.insert(entity)
        addToSyncQueue("jadwal", id, "INSERT", entity)
        return id
    }

    suspend fun update(jadwal: JadwalEntity) {
        val entity = jadwal.copy(
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.update(entity)
        addToSyncQueue("jadwal", jadwal.id, "UPDATE", entity)
    }

    suspend fun delete(id: String) {
        dao.softDelete(id)
        addToSyncQueue("jadwal", id, "DELETE", null)
    }

    suspend fun getPendingSync(): List<JadwalEntity> = dao.getPendingSync()

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
