package com.simkbm.ustaz.data.local

import android.content.Context
import androidx.room.*
import androidx.room.Database
import kotlinx.coroutines.flow.Flow

// ==================== ENTITIES ====================

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val id: String,
    val nama_lengkap: String? = null,
    val nama_panggilan: String? = null,
    val email: String? = null,
    val nomor_whatsapp: String? = null,
    val role: String = "ustaz",
    val foto: String? = null,
    val is_active: Boolean = true,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "kelas")
data class KelasEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val nama_kelas: String,
    val tingkat: String? = null,
    val gender: String? = null,
    val warna: String? = null,
    val is_active: Boolean = true,
    val sync_status: String = "synced", // synced, pending, conflict
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "murid")
data class MuridEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val kelas_id: String? = null,
    val nis: String? = null,
    val nama: String,
    val jenis_kelamin: String? = null,
    val tempat_lahir: String? = null,
    val tanggal_lahir: String? = null,
    val alamat: String? = null,
    val nama_wali: String? = null,
    val no_hp_wali: String? = null,
    val status: String = "Aktif",
    val is_active: Boolean = true,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "jadwal")
data class JadwalEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val hari: String,
    val jam_mulai: String,
    val jam_selesai: String,
    val kelas: String,
    val pelajaran: String,
    val ruangan: String? = null,
    val is_active: Boolean = true,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "absensi")
data class AbsensiEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val murid_id: String,
    val kelas_id: String,
    val tanggal: String,
    val status: String,
    val keterangan: String? = null,
    val jam_datang: String? = null,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "jurnal")
data class JurnalEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val tanggal: String,
    val kelas: String,
    val pelajaran: String,
    val materi: String? = null,
    val target: String? = null,
    val realisasi: String? = null,
    val metode: String? = null,
    val catatan: String? = null,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "nilai")
data class NilaiEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val murid_id: String,
    val pelajaran: String,
    val jenis: String,
    val skor: Int,
    val tanggal: String,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "sikap")
data class SikapEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val murid_id: String,
    val tanggal: String,
    val disiplin: Int = 3,
    val adab: Int = 3,
    val kerajinan: Int = 3,
    val kejujuran: Int = 3,
    val tanggung_jawab: Int = 3,
    val catatan: String? = null,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "catatan")
data class CatatanEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val kategori: String = "Umum",
    val judul: String,
    val isi: String? = null,
    val tanggal_waktu: String? = null,
    val status: String = "Belum Selesai",
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "soal")
data class SoalEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val pelajaran: String,
    val kelas: String,
    val pertanyaan: String,
    val jawaban: String? = null,
    val tingkat: String? = "Mudah",
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "izin")
data class IzinEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val nama_ustaz: String,
    val jenis_izin: String,
    val tanggal_mulai: String,
    val tanggal_selesai: String? = null,
    val mata_pelajaran: String? = null,
    val kelas: String? = null,
    val guru_pengganti: String? = null,
    val catatan: String? = null,
    val status: String = "Diajukan",
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "pengumuman")
data class PengumumanEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val judul: String,
    val isi: String? = null,
    val prioritas: String = "Normal",
    val tanggal: String,
    val aktif: Boolean = true,
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "agenda")
data class AgendaEntity(
    @PrimaryKey val id: String,
    val user_id: String = "",
    val judul: String,
    val isi: String? = null,
    val tanggal: String,
    val jam: String? = null,
    val warna: String? = "#059669",
    val sync_status: String = "synced",
    val server_id: String? = null,
    val updated_at: Long = System.currentTimeMillis()
)

@Entity(tableName = "sync_queue")
data class SyncQueueEntity(
    @PrimaryKey(autoGenerate = true) val queue_id: Long = 0,
    val table_name: String,
    val record_id: String,
    val operation: String, // INSERT, UPDATE, DELETE
    val data_json: String,
    val created_at: Long = System.currentTimeMillis(),
    val retry_count: Int = 0,
    val last_error: String? = null
)

@Entity(tableName = "settings")
data class SettingsEntity(
    @PrimaryKey val key: String,
    val value: String,
    val updated_at: Long = System.currentTimeMillis()
)

// ==================== DAOs ====================

@Dao
interface ProfileDao {
    @Query("SELECT * FROM profiles WHERE id = :id")
    fun getById(id: String): Flow<ProfileEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(profile: ProfileEntity)

    @Update
    suspend fun update(profile: ProfileEntity)
}

@Dao
interface KelasDao {
    @Query("SELECT * FROM kelas WHERE is_active = 1 ORDER BY nama_kelas")
    fun getAll(): Flow<List<KelasEntity>>

    @Query("SELECT * FROM kelas WHERE id = :id")
    suspend fun getById(id: String): KelasEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(kelas: KelasEntity)

    @Update
    suspend fun update(kelas: KelasEntity)

    @Query("UPDATE kelas SET is_active = 0, updated_at = :now WHERE id = :id")
    suspend fun softDelete(id: String, now: Long = System.currentTimeMillis())

    @Query("SELECT * FROM kelas WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<KelasEntity>
}

@Dao
interface MuridDao {
    @Query("SELECT * FROM murid WHERE is_active = 1 ORDER BY nama")
    fun getAll(): Flow<List<MuridEntity>>

    @Query("SELECT * FROM murid WHERE kelas_id = :kelasId AND is_active = 1 ORDER BY nama")
    fun getByKelas(kelasId: String): Flow<List<MuridEntity>>

    @Query("SELECT * FROM murid WHERE id = :id")
    suspend fun getById(id: String): MuridEntity?

    @Query("SELECT * FROM murid WHERE nama LIKE '%' || :query || '%' OR nis LIKE '%' || :query || '%'")
    fun search(query: String): Flow<List<MuridEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(murid: MuridEntity)

    @Update
    suspend fun update(murid: MuridEntity)

    @Query("UPDATE murid SET is_active = 0, updated_at = :now WHERE id = :id")
    suspend fun softDelete(id: String, now: Long = System.currentTimeMillis())

    @Query("SELECT * FROM murid WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<MuridEntity>
}

@Dao
interface JadwalDao {
    @Query("SELECT * FROM jadwal WHERE is_active = 1 ORDER BY hari, jam_mulai")
    fun getAll(): Flow<List<JadwalEntity>>

    @Query("SELECT * FROM jadwal WHERE hari = :hari AND is_active = 1 ORDER BY jam_mulai")
    fun getByHari(hari: String): Flow<List<JadwalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jadwal: JadwalEntity)

    @Update
    suspend fun update(jadwal: JadwalEntity)

    @Query("UPDATE jadwal SET is_active = 0, updated_at = :now WHERE id = :id")
    suspend fun softDelete(id: String, now: Long = System.currentTimeMillis())

    @Query("SELECT * FROM jadwal WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<JadwalEntity>
}

@Dao
interface AbsensiDao {
    @Query("SELECT * FROM absensi WHERE tanggal = :tanggal AND kelas_id = :kelasId")
    fun getByTanggalAndKelas(tanggal: String, kelasId: String): Flow<List<AbsensiEntity>>

    @Query("SELECT * FROM absensi WHERE tanggal = :tanggal")
    fun getByTanggal(tanggal: String): Flow<List<AbsensiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(absensi: AbsensiEntity)

    @Update
    suspend fun update(absensi: AbsensiEntity)

    @Query("SELECT * FROM absensi WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<AbsensiEntity>
}

@Dao
interface JurnalDao {
    @Query("SELECT * FROM jurnal ORDER BY tanggal DESC")
    fun getAll(): Flow<List<JurnalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(jurnal: JurnalEntity)

    @Update
    suspend fun update(jurnal: JurnalEntity)

    @Query("DELETE FROM jurnal WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM jurnal WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<JurnalEntity>
}

@Dao
interface NilaiDao {
    @Query("SELECT * FROM nilai ORDER BY tanggal DESC")
    fun getAll(): Flow<List<NilaiEntity>>

    @Query("SELECT * FROM nilai WHERE murid_id = :muridId ORDER BY tanggal DESC")
    fun getByMurid(muridId: String): Flow<List<NilaiEntity>>

    @Query("SELECT * FROM nilai WHERE pelajaran = :pelajaran ORDER BY tanggal DESC")
    fun getByPelajaran(pelajaran: String): Flow<List<NilaiEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(nilai: NilaiEntity)

    @Update
    suspend fun update(nilai: NilaiEntity)

    @Query("DELETE FROM nilai WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM nilai WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<NilaiEntity>
}

@Dao
interface SikapDao {
    @Query("SELECT * FROM sikap ORDER BY tanggal DESC")
    fun getAll(): Flow<List<SikapEntity>>

    @Query("SELECT * FROM sikap WHERE murid_id = :muridId ORDER BY tanggal DESC")
    fun getByMurid(muridId: String): Flow<List<SikapEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(sikap: SikapEntity)

    @Update
    suspend fun update(sikap: SikapEntity)

    @Query("DELETE FROM sikap WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM sikap WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<SikapEntity>
}

@Dao
interface CatatanDao {
    @Query("SELECT * FROM catatan ORDER BY tanggal_waktu DESC")
    fun getAll(): Flow<List<CatatanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(catatan: CatatanEntity)

    @Update
    suspend fun update(catatan: CatatanEntity)

    @Query("DELETE FROM catatan WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM catatan WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<CatatanEntity>
}

@Dao
interface SoalDao {
    @Query("SELECT * FROM soal ORDER BY updated_at DESC")
    fun getAll(): Flow<List<SoalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(soal: SoalEntity)

    @Update
    suspend fun update(soal: SoalEntity)

    @Query("DELETE FROM soal WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM soal WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<SoalEntity>
}

@Dao
interface IzinDao {
    @Query("SELECT * FROM izin ORDER BY tanggal_mulai DESC")
    fun getAll(): Flow<List<IzinEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(izin: IzinEntity)

    @Update
    suspend fun update(izin: IzinEntity)

    @Query("DELETE FROM izin WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM izin WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<IzinEntity>
}

@Dao
interface PengumumanDao {
    @Query("SELECT * FROM pengumuman WHERE aktif = 1 ORDER BY tanggal DESC")
    fun getActive(): Flow<List<PengumumanEntity>>

    @Query("SELECT * FROM pengumuman ORDER BY tanggal DESC")
    fun getAll(): Flow<List<PengumumanEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(pengumuman: PengumumanEntity)

    @Update
    suspend fun update(pengumuman: PengumumanEntity)

    @Query("DELETE FROM pengumuman WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM pengumuman WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<PengumumanEntity>
}

@Dao
interface AgendaDao {
    @Query("SELECT * FROM agenda ORDER BY tanggal ASC")
    fun getAll(): Flow<List<AgendaEntity>>

    @Query("SELECT * FROM agenda WHERE tanggal >= :today ORDER BY tanggal ASC")
    fun getUpcoming(today: String): Flow<List<AgendaEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(agenda: AgendaEntity)

    @Update
    suspend fun update(agenda: AgendaEntity)

    @Query("DELETE FROM agenda WHERE id = :id")
    suspend fun delete(id: String)

    @Query("SELECT * FROM agenda WHERE sync_status = 'pending'")
    suspend fun getPendingSync(): List<AgendaEntity>
}

@Dao
interface SyncQueueDao {
    @Insert
    suspend fun insert(queue: SyncQueueEntity)

    @Query("SELECT * FROM sync_queue ORDER BY created_at ASC LIMIT :limit")
    suspend fun getPending(limit: Int = 50): List<SyncQueueEntity>

    @Query("DELETE FROM sync_queue WHERE queue_id = :id")
    suspend fun delete(id: Long)

    @Query("SELECT COUNT(*) FROM sync_queue")
    suspend fun getCount(): Int

    @Query("UPDATE sync_queue SET retry_count = retry_count + 1, last_error = :error WHERE queue_id = :id")
    suspend fun incrementRetry(id: Long, error: String)
}

@Dao
interface SettingsDao {
    @Query("SELECT * FROM settings WHERE `key` = :key")
    suspend fun get(key: String): SettingsEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun set(settings: SettingsEntity)
}

// ==================== DATABASE ====================

@Database(
    entities = [
        ProfileEntity::class, KelasEntity::class, MuridEntity::class,
        JadwalEntity::class, AbsensiEntity::class, JurnalEntity::class,
        NilaiEntity::class, SikapEntity::class, CatatanEntity::class,
        SoalEntity::class, IzinEntity::class, PengumumanEntity::class,
        AgendaEntity::class, SyncQueueEntity::class, SettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun profileDao(): ProfileDao
    abstract fun kelasDao(): KelasDao
    abstract fun muridDao(): MuridDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun absensiDao(): AbsensiDao
    abstract fun jurnalDao(): JurnalDao
    abstract fun nilaiDao(): NilaiDao
    abstract fun sikapDao(): SikapDao
    abstract fun catatanDao(): CatatanDao
    abstract fun soalDao(): SoalDao
    abstract fun izinDao(): IzinDao
    abstract fun pengumumanDao(): PengumumanDao
    abstract fun agendaDao(): AgendaDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun settingsDao(): SettingsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sim_kbm_ustaz.db"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
