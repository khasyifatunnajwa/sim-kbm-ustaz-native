package com.bustanululum.simkbmnative.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

@Database(
    entities = [
        KelasEntity::class,
        MuridEntity::class,
        SyncQueueEntity::class,
        AbsensiEntity::class,
        JurnalKBMEntity::class,
        JadwalEntity::class,
        NilaiEntity::class,
        AgendaEntity::class,
        PengumumanEntity::class,
        SikapEntity::class,
        TahunAjaranEntity::class,
        BankSoalEntity::class,
        IzinMengajarEntity::class,
        BukuSakuEntity::class
    ],
    version = 3,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kelasDao(): KelasDao
    abstract fun muridDao(): MuridDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun absensiDao(): AbsensiDao
    abstract fun jurnalKBMDao(): JurnalKBMDao
    abstract fun jadwalDao(): JadwalDao
    abstract fun nilaiDao(): NilaiDao
    abstract fun agendaDao(): AgendaDao
    abstract fun pengumumanDao(): PengumumanDao
    abstract fun sikapDao(): SikapDao
    abstract fun tahunAjaranDao(): TahunAjaranDao
    abstract fun bankSoalDao(): BankSoalDao
    abstract fun izinMengajarDao(): IzinMengajarDao
    abstract fun bukuSakuDao(): BukuSakuDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        private val MIGRATION_1_2 = object : Migration(1, 2) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS absensi (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        muridLocalId TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        status TEXT NOT NULL,
                        catatan TEXT,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(muridLocalId)
                            REFERENCES murid(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS " +
                        "index_absensi_muridLocalId " +
                        "ON absensi(muridLocalId)"
                )

                db.execSQL(
                    "CREATE UNIQUE INDEX IF NOT EXISTS " +
                        "index_absensi_muridLocalId_tanggal " +
                        "ON absensi(muridLocalId, tanggal)"
                )
            }
        }

        private val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS jurnal_kbm (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        kelasLocalId TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        materi TEXT NOT NULL,
                        kegiatan TEXT NOT NULL,
                        kendala TEXT,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(kelasLocalId)
                            REFERENCES kelas(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_jurnal_kbm_kelasLocalId " +
                        "ON jurnal_kbm(kelasLocalId)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS jadwal (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        kelasLocalId TEXT NOT NULL,
                        hari TEXT NOT NULL,
                        jamMulai TEXT NOT NULL,
                        jamSelesai TEXT NOT NULL,
                        mataPelajaran TEXT NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(kelasLocalId)
                            REFERENCES kelas(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_jadwal_kelasLocalId " +
                        "ON jadwal(kelasLocalId)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS nilai (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        muridLocalId TEXT NOT NULL,
                        mataPelajaran TEXT NOT NULL,
                        jenisNilai TEXT NOT NULL,
                        nilai REAL NOT NULL,
                        tanggal TEXT NOT NULL,
                        keterangan TEXT,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(muridLocalId)
                            REFERENCES murid(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_nilai_muridLocalId " +
                        "ON nilai(muridLocalId)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS agenda (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        judul TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        waktu TEXT,
                        lokasi TEXT,
                        catatan TEXT,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId)
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS pengumuman (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        judul TEXT NOT NULL,
                        isi TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId)
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS sikap (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        muridLocalId TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        kategori TEXT NOT NULL,
                        deskripsi TEXT NOT NULL,
                        penilaian TEXT NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(muridLocalId)
                            REFERENCES murid(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_sikap_muridLocalId " +
                        "ON sikap(muridLocalId)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS tahun_ajaran (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        namaTahunAjaran TEXT NOT NULL,
                        semester TEXT NOT NULL,
                        tanggalMulai TEXT NOT NULL,
                        tanggalSelesai TEXT,
                        aktif INTEGER NOT NULL DEFAULT 0,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId)
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS bank_soal (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        mataPelajaran TEXT NOT NULL,
                        pertanyaan TEXT NOT NULL,
                        pilihanA TEXT,
                        pilihanB TEXT,
                        pilihanC TEXT,
                        pilihanD TEXT,
                        jawabanBenar TEXT,
                        tingkat TEXT,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId)
                    )
                    """.trimIndent()
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS izin_mengajar (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        kelasLocalId TEXT NOT NULL,
                        tanggal TEXT NOT NULL,
                        alasan TEXT NOT NULL,
                        pengganti TEXT,
                        status TEXT NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId),
                        FOREIGN KEY(kelasLocalId)
                            REFERENCES kelas(localId)
                            ON UPDATE NO ACTION
                            ON DELETE CASCADE
                    )
                    """.trimIndent()
                )
                db.execSQL(
                    "CREATE INDEX IF NOT EXISTS index_izin_mengajar_kelasLocalId " +
                        "ON izin_mengajar(kelasLocalId)"
                )

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS buku_saku (
                        localId TEXT NOT NULL,
                        serverId TEXT,
                        judul TEXT NOT NULL,
                        kategori TEXT NOT NULL,
                        isi TEXT NOT NULL,
                        updatedAt INTEGER NOT NULL,
                        syncStatus TEXT NOT NULL,
                        PRIMARY KEY(localId)
                    )
                    """.trimIndent()
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sim_kbm_ustaz.db"
                )
                    .addMigrations(MIGRATION_1_2, MIGRATION_2_3)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
