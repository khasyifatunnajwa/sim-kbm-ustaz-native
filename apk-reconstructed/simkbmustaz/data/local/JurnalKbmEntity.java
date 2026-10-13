package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\"\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Bi\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005\u0012\b\b\u0002\u0010\r\u001a\u00020\u0005¢\u0006\u0004\b\u000e\u0010\u000fJ\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0005HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0003HÆ\u0003J\t\u0010\"\u001a\u00020\u0003HÆ\u0003J\t\u0010#\u001a\u00020\u0003HÆ\u0003J\t\u0010$\u001a\u00020\u0005HÆ\u0003J\t\u0010%\u001a\u00020\u0005HÆ\u0003Jo\u0010&\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00032\b\b\u0002\u0010\f\u001a\u00020\u00052\b\b\u0002\u0010\r\u001a\u00020\u0005HÆ\u0001J\u0014\u0010'\u001a\u00020(2\b\u0010)\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010*\u001a\u00020+HÖ\u0081\u0004J\n\u0010,\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u0011R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0011R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0011R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0011R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0011R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0011R\u0011\u0010\u000b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0011R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0013R\u0011\u0010\r\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0013¨\u0006-"}, d2 = {"Lcom/rork/simkbmustaz/data/local/JurnalKbmEntity;", "", "id", "", "tanggal", "", "kelasId", "mapel", "materi", "uraianKegiatan", "capaian", "kendala", "createdAt", "updatedAt", "<init>", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getTanggal", "()J", "getKelasId", "getMapel", "getMateri", "getUraianKegiatan", "getCapaian", "getKendala", "getCreatedAt", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "component10", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class JurnalKbmEntity {
    public static final int $stable = 0;
    private final String capaian;
    private final long createdAt;
    private final String id;
    private final String kelasId;
    private final String kendala;
    private final String mapel;
    private final String materi;
    private final long tanggal;
    private final long updatedAt;
    private final String uraianKegiatan;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component10, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getTanggal() {
        return this.tanggal;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getKelasId() {
        return this.kelasId;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getMapel() {
        return this.mapel;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getMateri() {
        return this.materi;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getUraianKegiatan() {
        return this.uraianKegiatan;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getCapaian() {
        return this.capaian;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final String getKendala() {
        return this.kendala;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final JurnalKbmEntity copy(String id, long tanggal, String kelasId, String mapel, String materi, String uraianKegiatan, String capaian, String kendala, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(mapel, "mapel");
        Intrinsics.checkNotNullParameter(materi, "materi");
        Intrinsics.checkNotNullParameter(uraianKegiatan, "uraianKegiatan");
        Intrinsics.checkNotNullParameter(capaian, "capaian");
        Intrinsics.checkNotNullParameter(kendala, "kendala");
        return new JurnalKbmEntity(id, tanggal, kelasId, mapel, materi, uraianKegiatan, capaian, kendala, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JurnalKbmEntity)) {
            return false;
        }
        JurnalKbmEntity jurnalKbmEntity = (JurnalKbmEntity) other;
        return Intrinsics.areEqual(this.id, jurnalKbmEntity.id) && this.tanggal == jurnalKbmEntity.tanggal && Intrinsics.areEqual(this.kelasId, jurnalKbmEntity.kelasId) && Intrinsics.areEqual(this.mapel, jurnalKbmEntity.mapel) && Intrinsics.areEqual(this.materi, jurnalKbmEntity.materi) && Intrinsics.areEqual(this.uraianKegiatan, jurnalKbmEntity.uraianKegiatan) && Intrinsics.areEqual(this.capaian, jurnalKbmEntity.capaian) && Intrinsics.areEqual(this.kendala, jurnalKbmEntity.kendala) && this.createdAt == jurnalKbmEntity.createdAt && this.updatedAt == jurnalKbmEntity.updatedAt;
    }

    public int hashCode() {
        int iHashCode = ((this.id.hashCode() * 31) + Long.hashCode(this.tanggal)) * 31;
        String str = this.kelasId;
        return ((((((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.mapel.hashCode()) * 31) + this.materi.hashCode()) * 31) + this.uraianKegiatan.hashCode()) * 31) + this.capaian.hashCode()) * 31) + this.kendala.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "JurnalKbmEntity(id=" + this.id + ", tanggal=" + this.tanggal + ", kelasId=" + this.kelasId + ", mapel=" + this.mapel + ", materi=" + this.materi + ", uraianKegiatan=" + this.uraianKegiatan + ", capaian=" + this.capaian + ", kendala=" + this.kendala + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public JurnalKbmEntity(String id, long j, String str, String mapel, String materi, String uraianKegiatan, String capaian, String kendala, long j2, long j3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(mapel, "mapel");
        Intrinsics.checkNotNullParameter(materi, "materi");
        Intrinsics.checkNotNullParameter(uraianKegiatan, "uraianKegiatan");
        Intrinsics.checkNotNullParameter(capaian, "capaian");
        Intrinsics.checkNotNullParameter(kendala, "kendala");
        this.id = id;
        this.tanggal = j;
        this.kelasId = str;
        this.mapel = mapel;
        this.materi = materi;
        this.uraianKegiatan = uraianKegiatan;
        this.capaian = capaian;
        this.kendala = kendala;
        this.createdAt = j2;
        this.updatedAt = j3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ JurnalKbmEntity(String str, long j, String str2, String str3, String str4, String str5, String str6, String str7, long j2, long j3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str8;
        if ((i & 1) != 0) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            str8 = string;
        } else {
            str8 = str;
        }
        this(str8, j, (i & 4) != 0 ? null : str2, str3, (i & 16) != 0 ? "" : str4, (i & 32) != 0 ? "" : str5, (i & 64) != 0 ? "" : str6, (i & 128) != 0 ? "" : str7, (i & 256) != 0 ? System.currentTimeMillis() : j2, (i & 512) != 0 ? System.currentTimeMillis() : j3);
    }

    public final String getId() {
        return this.id;
    }

    public final long getTanggal() {
        return this.tanggal;
    }

    public final String getKelasId() {
        return this.kelasId;
    }

    public final String getMapel() {
        return this.mapel;
    }

    public final String getMateri() {
        return this.materi;
    }

    public final String getUraianKegiatan() {
        return this.uraianKegiatan;
    }

    public final String getCapaian() {
        return this.capaian;
    }

    public final String getKendala() {
        return this.kendala;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
