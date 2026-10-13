package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u001f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B]\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0005\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0005\u0012\b\b\u0002\u0010\f\u001a\u00020\u0005¢\u0006\u0004\b\r\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001e\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u0005HÆ\u0003J\t\u0010\"\u001a\u00020\u0005HÆ\u0003Je\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00052\b\b\u0002\u0010\u0007\u001a\u00020\u00032\n\b\u0002\u0010\b\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\u00052\b\b\u0002\u0010\f\u001a\u00020\u0005HÆ\u0001J\u0014\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010'\u001a\u00020(HÖ\u0081\u0004J\n\u0010)\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0006\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0013\u0010\b\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0012R\u0011\u0010\f\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0012¨\u0006*"}, d2 = {"Lcom/rork/simkbmustaz/data/local/IzinMengajarEntity;", "", "id", "", "tanggalMulai", "", "tanggalSelesai", "namaUstaz", "kelasId", "alasan", "pengganti", "createdAt", "updatedAt", "<init>", "(Ljava/lang/String;JJLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getTanggalMulai", "()J", "getTanggalSelesai", "getNamaUstaz", "getKelasId", "getAlasan", "getPengganti", "getCreatedAt", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class IzinMengajarEntity {
    public static final int $stable = 0;
    private final String alasan;
    private final long createdAt;
    private final String id;
    private final String kelasId;
    private final String namaUstaz;
    private final String pengganti;
    private final long tanggalMulai;
    private final long tanggalSelesai;
    private final long updatedAt;

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final long getTanggalMulai() {
        return this.tanggalMulai;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final long getTanggalSelesai() {
        return this.tanggalSelesai;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getNamaUstaz() {
        return this.namaUstaz;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getKelasId() {
        return this.kelasId;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getAlasan() {
        return this.alasan;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getPengganti() {
        return this.pengganti;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final IzinMengajarEntity copy(String id, long tanggalMulai, long tanggalSelesai, String namaUstaz, String kelasId, String alasan, String pengganti, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namaUstaz, "namaUstaz");
        Intrinsics.checkNotNullParameter(alasan, "alasan");
        Intrinsics.checkNotNullParameter(pengganti, "pengganti");
        return new IzinMengajarEntity(id, tanggalMulai, tanggalSelesai, namaUstaz, kelasId, alasan, pengganti, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IzinMengajarEntity)) {
            return false;
        }
        IzinMengajarEntity izinMengajarEntity = (IzinMengajarEntity) other;
        return Intrinsics.areEqual(this.id, izinMengajarEntity.id) && this.tanggalMulai == izinMengajarEntity.tanggalMulai && this.tanggalSelesai == izinMengajarEntity.tanggalSelesai && Intrinsics.areEqual(this.namaUstaz, izinMengajarEntity.namaUstaz) && Intrinsics.areEqual(this.kelasId, izinMengajarEntity.kelasId) && Intrinsics.areEqual(this.alasan, izinMengajarEntity.alasan) && Intrinsics.areEqual(this.pengganti, izinMengajarEntity.pengganti) && this.createdAt == izinMengajarEntity.createdAt && this.updatedAt == izinMengajarEntity.updatedAt;
    }

    public int hashCode() {
        int iHashCode = ((((((this.id.hashCode() * 31) + Long.hashCode(this.tanggalMulai)) * 31) + Long.hashCode(this.tanggalSelesai)) * 31) + this.namaUstaz.hashCode()) * 31;
        String str = this.kelasId;
        return ((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.alasan.hashCode()) * 31) + this.pengganti.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "IzinMengajarEntity(id=" + this.id + ", tanggalMulai=" + this.tanggalMulai + ", tanggalSelesai=" + this.tanggalSelesai + ", namaUstaz=" + this.namaUstaz + ", kelasId=" + this.kelasId + ", alasan=" + this.alasan + ", pengganti=" + this.pengganti + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public IzinMengajarEntity(String id, long j, long j2, String namaUstaz, String str, String alasan, String pengganti, long j3, long j4) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namaUstaz, "namaUstaz");
        Intrinsics.checkNotNullParameter(alasan, "alasan");
        Intrinsics.checkNotNullParameter(pengganti, "pengganti");
        this.id = id;
        this.tanggalMulai = j;
        this.tanggalSelesai = j2;
        this.namaUstaz = namaUstaz;
        this.kelasId = str;
        this.alasan = alasan;
        this.pengganti = pengganti;
        this.createdAt = j3;
        this.updatedAt = j4;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ IzinMengajarEntity(String str, long j, long j2, String str2, String str3, String str4, String str5, long j3, long j4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str6;
        if ((i & 1) != 0) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            str6 = string;
        } else {
            str6 = str;
        }
        this(str6, j, j2, str2, (i & 16) != 0 ? null : str3, (i & 32) != 0 ? "" : str4, (i & 64) != 0 ? "" : str5, (i & 128) != 0 ? System.currentTimeMillis() : j3, (i & 256) != 0 ? System.currentTimeMillis() : j4);
    }

    public final String getId() {
        return this.id;
    }

    public final long getTanggalMulai() {
        return this.tanggalMulai;
    }

    public final long getTanggalSelesai() {
        return this.tanggalSelesai;
    }

    public final String getNamaUstaz() {
        return this.namaUstaz;
    }

    public final String getKelasId() {
        return this.kelasId;
    }

    public final String getAlasan() {
        return this.alasan;
    }

    public final String getPengganti() {
        return this.pengganti;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
