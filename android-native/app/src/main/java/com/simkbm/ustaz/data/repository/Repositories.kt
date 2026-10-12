package com.simkbm.ustaz.data.repository

import com.simkbm.ustaz.data.local.*
import kotlinx.coroutines.flow.Flow

// Nilai Repository
class NilaiRepository(private val db: AppDatabase) {
    private val dao = db.nilaiDao()

    fun getAll(): Flow<List<NilaiEntity>> = dao.getAll()
    fun getByMurid(muridId: String): Flow<List<NilaiEntity>> = dao.getByMurid(muridId)
    fun getByPelajaran(pelajaran: String): Flow<List<NilaiEntity>> = dao.getByPelajaran(pelajaran)

    suspend fun insert(nilai: NilaiEntity): String {
        val id = nilai.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        val entity = nilai.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis())
        dao.insert(entity)
        return id
    }

    suspend fun update(nilai: NilaiEntity) {
        dao.update(nilai.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Sikap Repository
class SikapRepository(private val db: AppDatabase) {
    private val dao = db.sikapDao()

    fun getAll(): Flow<List<SikapEntity>> = dao.getAll()
    fun getByMurid(muridId: String): Flow<List<SikapEntity>> = dao.getByMurid(muridId)

    suspend fun insert(sikap: SikapEntity): String {
        val id = sikap.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(sikap.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(sikap: SikapEntity) {
        dao.update(sikap.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Catatan Repository
class CatatanRepository(private val db: AppDatabase) {
    private val dao = db.catatanDao()

    fun getAll(): Flow<List<CatatanEntity>> = dao.getAll()

    suspend fun insert(catatan: CatatanEntity): String {
        val id = catatan.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(catatan.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(catatan: CatatanEntity) {
        dao.update(catatan.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Soal Repository
class SoalRepository(private val db: AppDatabase) {
    private val dao = db.soalDao()

    fun getAll(): Flow<List<SoalEntity>> = dao.getAll()

    suspend fun insert(soal: SoalEntity): String {
        val id = soal.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(soal.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(soal: SoalEntity) {
        dao.update(soal.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Izin Repository
class IzinRepository(private val db: AppDatabase) {
    private val dao = db.izinDao()

    fun getAll(): Flow<List<IzinEntity>> = dao.getAll()

    suspend fun insert(izin: IzinEntity): String {
        val id = izin.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(izin.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(izin: IzinEntity) {
        dao.update(izin.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Pengumuman Repository
class PengumumanRepository(private val db: AppDatabase) {
    private val dao = db.pengumumanDao()

    fun getAll(): Flow<List<PengumumanEntity>> = dao.getAll()
    fun getActive(): Flow<List<PengumumanEntity>> = dao.getActive()

    suspend fun insert(pengumuman: PengumumanEntity): String {
        val id = pengumuman.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(pengumuman.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(pengumuman: PengumumanEntity) {
        dao.update(pengumuman.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Agenda Repository
class AgendaRepository(private val db: AppDatabase) {
    private val dao = db.agendaDao()

    fun getAll(): Flow<List<AgendaEntity>> = dao.getAll()
    fun getUpcoming(today: String): Flow<List<AgendaEntity>> = dao.getUpcoming(today)

    suspend fun insert(agenda: AgendaEntity): String {
        val id = agenda.id.ifEmpty { java.util.UUID.randomUUID().toString() }
        dao.insert(agenda.copy(id = id, sync_status = "pending", updated_at = System.currentTimeMillis()))
        return id
    }

    suspend fun update(agenda: AgendaEntity) {
        dao.update(agenda.copy(sync_status = "pending", updated_at = System.currentTimeMillis()))
    }

    suspend fun delete(id: String) = dao.delete(id)
}

// Profile Repository
class ProfileRepository(private val db: AppDatabase) {
    private val dao = db.profileDao()

    fun getById(id: String): Flow<ProfileEntity?> = dao.getById(id)

    suspend fun insert(profile: ProfileEntity) = dao.insert(profile)
    suspend fun update(profile: ProfileEntity) = dao.update(profile)
}

// Settings Repository
class SettingsRepository(private val db: AppDatabase) {
    private val dao = db.settingsDao()

    suspend fun get(key: String): String? = dao.get(key)?.value

    suspend fun set(key: String, value: String) {
        dao.set(SettingsEntity(key = key, value = value, updated_at = System.currentTimeMillis()))
    }
}
