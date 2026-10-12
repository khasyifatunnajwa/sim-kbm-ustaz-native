package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

class AbsensiRepository(private val db: AppDatabase) {
    private val dao = db.absensiDao()

    fun getByTanggalAndKelas(tanggal: String, kelasId: String): Flow<List<AbsensiEntity>> =
        dao.getByTanggalAndKelas(tanggal, kelasId)

    fun getByTanggal(tanggal: String): Flow<List<AbsensiEntity>> =
        dao.getByTanggal(tanggal)

    suspend fun insert(absensi: AbsensiEntity): String {
        val id = absensi.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = absensi.copy(
            id = id,
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.insert(entity)
        addToSyncQueue("absensi", id, "INSERT", entity)
        return id
    }

    suspend fun update(absensi: AbsensiEntity) {
        val entity = absensi.copy(
            sync_status = "pending",
            updated_at = System.currentTimeMillis()
        )
        dao.update(entity)
        addToSyncQueue("absensi", absensi.id, "UPDATE", entity)
    }

    suspend fun getPendingSync(): List<AbsensiEntity> = dao.getPendingSync()

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
