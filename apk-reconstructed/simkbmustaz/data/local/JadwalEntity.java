package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\b\n\u0002\u0010\t\n\u0002\b\u001b\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Be\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f\u0012\b\b\u0002\u0010\r\u001a\u00020\f¢\u0006\u0004\b\u000e\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\u000b\u0010 \u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\fHÆ\u0003J\t\u0010%\u001a\u00020\fHÆ\u0003Jo\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\f2\b\b\u0002\u0010\r\u001a\u00020\fHÆ\u0001J\u0014\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010*\u001a\u00020+HÖ\u0081\u0004J\n\u0010,\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0011R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0011R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0013\u0010\u0007\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u001aR\u0011\u0010\r\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u001a¨\u0006-"}, d2 = {"Lcom/rork/simkbmustaz/data/local/JadwalEntity;", "", "id", "", "hari", "jamMulai", "jamSelesai", "kelasId", "mapel", "pengajar", "catatan", "createdAt", "", "updatedAt", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getHari", "getJamMulai", "getJamSelesai", "getKelasId", "getMapel", "getPengajar", "getCatatan", "getCreatedAt", "()J", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class JadwalEntity {
    public static final int $stable = 0;
    private final String catatan;
    private final long createdAt;
    private final String hari;
    private final String id;
    private final String jamMulai;
    private final String jamSelesai;
    private final String kelasId;
    private final String mapel;
    private final String pengajar;
    private final long updatedAt;

    public static /* synthetic */ JadwalEntity copy$default(JadwalEntity jadwalEntity, String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = jadwalEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = jadwalEntity.hari;
        }
        if ((i & 4) != 0) {
            str3 = jadwalEntity.jamMulai;
        }
        if ((i & 8) != 0) {
            str4 = jadwalEntity.jamSelesai;
        }
        if ((i & 16) != 0) {
            str5 = jadwalEntity.kelasId;
        }
        if ((i & 32) != 0) {
            str6 = jadwalEntity.mapel;
        }
        if ((i & 64) != 0) {
            str7 = jadwalEntity.pengajar;
        }
        if ((i & 128) != 0) {
            str8 = jadwalEntity.catatan;
        }
        if ((i & 256) != 0) {
            j = jadwalEntity.createdAt;
        }
        if ((i & 512) != 0) {
            j2 = jadwalEntity.updatedAt;
        }
        long j3 = j2;
        long j4 = j;
        String str9 = str7;
        String str10 = str8;
        String str11 = str5;
        String str12 = str6;
        return jadwalEntity.copy(str, str2, str3, str4, str11, str12, str9, str10, j4, j3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getHari() {
        return this.hari;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getJamMulai() {
        return this.jamMulai;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getJamSelesai() {
        return this.jamSelesai;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getKelasId() {
        return this.kelasId;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getMapel() {
        return this.mapel;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getPengajar() {
        return this.pengajar;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getCatatan() {
        return this.catatan;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final JadwalEntity copy(String id, String hari, String jamMulai, String jamSelesai, String kelasId, String mapel, String pengajar, String catatan, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(hari, "hari");
        Intrinsics.checkNotNullParameter(jamMulai, "jamMulai");
        Intrinsics.checkNotNullParameter(jamSelesai, "jamSelesai");
        Intrinsics.checkNotNullParameter(mapel, "mapel");
        Intrinsics.checkNotNullParameter(pengajar, "pengajar");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        return new JadwalEntity(id, hari, jamMulai, jamSelesai, kelasId, mapel, pengajar, catatan, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JadwalEntity)) {
            return false;
        }
        JadwalEntity jadwalEntity = (JadwalEntity) other;
        return Intrinsics.areEqual(this.id, jadwalEntity.id) && Intrinsics.areEqual(this.hari, jadwalEntity.hari) && Intrinsics.areEqual(this.jamMulai, jadwalEntity.jamMulai) && Intrinsics.areEqual(this.jamSelesai, jadwalEntity.jamSelesai) && Intrinsics.areEqual(this.kelasId, jadwalEntity.kelasId) && Intrinsics.areEqual(this.mapel, jadwalEntity.mapel) && Intrinsics.areEqual(this.pengajar, jadwalEntity.pengajar) && Intrinsics.areEqual(this.catatan, jadwalEntity.catatan) && this.createdAt == jadwalEntity.createdAt && this.updatedAt == jadwalEntity.updatedAt;
    }

    public int hashCode() {
        int iHashCode = ((((((this.id.hashCode() * 31) + this.hari.hashCode()) * 31) + this.jamMulai.hashCode()) * 31) + this.jamSelesai.hashCode()) * 31;
        String str = this.kelasId;
        return ((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.mapel.hashCode()) * 31) + this.pengajar.hashCode()) * 31) + this.catatan.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "JadwalEntity(id=" + this.id + ", hari=" + this.hari + ", jamMulai=" + this.jamMulai + ", jamSelesai=" + this.jamSelesai + ", kelasId=" + this.kelasId + ", mapel=" + this.mapel + ", pengajar=" + this.pengajar + ", catatan=" + this.catatan + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public JadwalEntity(String id, String hari, String jamMulai, String jamSelesai, String str, String mapel, String pengajar, String catatan, long j, long j2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(hari, "hari");
        Intrinsics.checkNotNullParameter(jamMulai, "jamMulai");
        Intrinsics.checkNotNullParameter(jamSelesai, "jamSelesai");
        Intrinsics.checkNotNullParameter(mapel, "mapel");
        Intrinsics.checkNotNullParameter(pengajar, "pengajar");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        this.id = id;
        this.hari = hari;
        this.jamMulai = jamMulai;
        this.jamSelesai = jamSelesai;
        this.kelasId = str;
        this.mapel = mapel;
        this.pengajar = pengajar;
        this.catatan = catatan;
        this.createdAt = j;
        this.updatedAt = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ JadwalEntity(String str, String str2, String str3, String str4, String str5, String str6, String str7, String str8, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str9;
        if ((i & 1) != 0) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            str9 = string;
        } else {
            str9 = str;
        }
        this(str9, str2, str3, str4, (i & 16) != 0 ? null : str5, str6, (i & 64) != 0 ? "" : str7, (i & 128) != 0 ? "" : str8, (i & 256) != 0 ? System.currentTimeMillis() : j, (i & 512) != 0 ? System.currentTimeMillis() : j2);
    }

    public final String getId() {
        return this.id;
    }

    public final String getHari() {
        return this.hari;
    }

    public final String getJamMulai() {
        return this.jamMulai;
    }

    public final String getJamSelesai() {
        return this.jamSelesai;
    }

    public final String getKelasId() {
        return this.kelasId;
    }

    public final String getMapel() {
        return this.mapel;
    }

    public final String getPengajar() {
        return this.pengajar;
    }

    public final String getCatatan() {
        return this.catatan;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
