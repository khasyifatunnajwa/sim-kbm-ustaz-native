package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: AppDatabase.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\t\n\u0000\n\u0002\b\u0003*\u0001\u0001\"\u0010\u0010\u0000\u001a\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0002¨\u0006\u0003"}, d2 = {"MIGRATION_1_2", "com/rork/simkbmustaz/data/local/AppDatabaseKt$MIGRATION_1_2$1", "Lcom/rork/simkbmustaz/data/local/AppDatabaseKt$MIGRATION_1_2$1;", "app_release"}, k = 2, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final class AppDatabaseKt {
    private static final AppDatabaseKt$MIGRATION_1_2$1 MIGRATION_1_2 = new Migration() { // from class: com.rork.simkbmustaz.data.local.AppDatabaseKt$MIGRATION_1_2$1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase db) {
            Intrinsics.checkNotNullParameter(db, "db");
            db.execSQL("CREATE TABLE IF NOT EXISTS jadwal (id TEXT NOT NULL PRIMARY KEY, hari TEXT NOT NULL, jamMulai TEXT NOT NULL, jamSelesai TEXT NOT NULL, kelasId TEXT, mapel TEXT NOT NULL, pengajar TEXT NOT NULL, catatan TEXT NOT NULL, createdAt INTEGER NOT NULL, updatedAt INTEGER NOT NULL)");
            db.execSQL("CREATE TABLE IF NOT EXISTS absensi (id TEXT NOT NULL PRIMARY KEY, tanggal INTEGER NOT NULL, kelasId TEXT NOT NULL, santriId TEXT NOT NULL, status TEXT NOT NULL, catatan TEXT NOT NULL, updatedAt INTEGER NOT NULL)");
            db.execSQL("CREATE UNIQUE INDEX IF NOT EXISTS index_absensi_tanggal_santriId ON absensi (tanggal, santriId)");
            db.execSQL("CREATE TABLE IF NOT EXISTS jurnal_kbm (id TEXT NOT NULL PRIMARY KEY, tanggal INTEGER NOT NULL, kelasId TEXT, mapel TEXT NOT NULL, materi TEXT NOT NULL, uraianKegiatan TEXT NOT NULL, capaian TEXT NOT NULL, kendala TEXT NOT NULL, createdAt INTEGER NOT NULL, updatedAt INTEGER NOT NULL)");
            db.execSQL("CREATE TABLE IF NOT EXISTS izin_mengajar (id TEXT NOT NULL PRIMARY KEY, tanggalMulai INTEGER NOT NULL, tanggalSelesai INTEGER NOT NULL, namaUstaz TEXT NOT NULL, kelasId TEXT, alasan TEXT NOT NULL, pengganti TEXT NOT NULL, createdAt INTEGER NOT NULL, updatedAt INTEGER NOT NULL)");
            db.execSQL("CREATE TABLE IF NOT EXISTS agenda (id TEXT NOT NULL PRIMARY KEY, tanggal INTEGER NOT NULL, judul TEXT NOT NULL, waktu TEXT NOT NULL, keterangan TEXT NOT NULL, createdAt INTEGER NOT NULL, updatedAt INTEGER NOT NULL)");
        }
    };
}
