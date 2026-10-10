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
        AbsensiEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun kelasDao(): KelasDao
    abstract fun muridDao(): MuridDao
    abstract fun syncQueueDao(): SyncQueueDao
    abstract fun absensiDao(): AbsensiDao

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

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "sim_kbm_ustaz.db"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also { INSTANCE = it }
            }
        }
    }
}
