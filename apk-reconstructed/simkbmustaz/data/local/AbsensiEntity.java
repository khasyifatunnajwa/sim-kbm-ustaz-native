package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.core.app.NotificationCompat;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BE\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\u0006\u0010\u0007\u001a\u00020\u0003\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003JO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u0005HÆ\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010!\u001a\u00020\"HÖ\u0081\u0004J\n\u0010#\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000eR\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010¨\u0006$"}, d2 = {"Lcom/rork/simkbmustaz/data/local/AbsensiEntity;", "", "id", "", "tanggal", "", "kelasId", "santriId", NotificationCompat.CATEGORY_STATUS, "catatan", "updatedAt", "<init>", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getId", "()Ljava/lang/String;", "getTanggal", "()J", "getKelasId", "getSantriId", "getStatus", "getCatatan", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class AbsensiEntity {
    public static final int $stable = 0;
    private final String catatan;
    private final String id;
    private final String kelasId;
    private final String santriId;
    private final String status;
    private final long tanggal;
    private final long updatedAt;

    public static /* synthetic */ AbsensiEntity copy$default(AbsensiEntity absensiEntity, String str, long j, String str2, String str3, String str4, String str5, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = absensiEntity.id;
        }
        if ((i & 2) != 0) {
            j = absensiEntity.tanggal;
        }
        if ((i & 4) != 0) {
            str2 = absensiEntity.kelasId;
        }
        if ((i & 8) != 0) {
            str3 = absensiEntity.santriId;
        }
        if ((i & 16) != 0) {
            str4 = absensiEntity.status;
        }
        if ((i & 32) != 0) {
            str5 = absensiEntity.catatan;
        }
        if ((i & 64) != 0) {
            j2 = absensiEntity.updatedAt;
        }
        long j3 = j2;
        return absensiEntity.copy(str, j, str2, str3, str4, str5, j3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
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
    public final String getSantriId() {
        return this.santriId;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getCatatan() {
        return this.catatan;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final AbsensiEntity copy(String id, long tanggal, String kelasId, String santriId, String status, String catatan, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(kelasId, "kelasId");
        Intrinsics.checkNotNullParameter(santriId, "santriId");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        return new AbsensiEntity(id, tanggal, kelasId, santriId, status, catatan, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AbsensiEntity)) {
            return false;
        }
        AbsensiEntity absensiEntity = (AbsensiEntity) other;
        return Intrinsics.areEqual(this.id, absensiEntity.id) && this.tanggal == absensiEntity.tanggal && Intrinsics.areEqual(this.kelasId, absensiEntity.kelasId) && Intrinsics.areEqual(this.santriId, absensiEntity.santriId) && Intrinsics.areEqual(this.status, absensiEntity.status) && Intrinsics.areEqual(this.catatan, absensiEntity.catatan) && this.updatedAt == absensiEntity.updatedAt;
    }

    public int hashCode() {
        return (((((((((((this.id.hashCode() * 31) + Long.hashCode(this.tanggal)) * 31) + this.kelasId.hashCode()) * 31) + this.santriId.hashCode()) * 31) + this.status.hashCode()) * 31) + this.catatan.hashCode()) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "AbsensiEntity(id=" + this.id + ", tanggal=" + this.tanggal + ", kelasId=" + this.kelasId + ", santriId=" + this.santriId + ", status=" + this.status + ", catatan=" + this.catatan + ", updatedAt=" + this.updatedAt + ")";
    }

    public AbsensiEntity(String id, long j, String kelasId, String santriId, String status, String catatan, long j2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(kelasId, "kelasId");
        Intrinsics.checkNotNullParameter(santriId, "santriId");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        this.id = id;
        this.tanggal = j;
        this.kelasId = kelasId;
        this.santriId = santriId;
        this.status = status;
        this.catatan = catatan;
        this.updatedAt = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ AbsensiEntity(String str, long j, String str2, String str3, String str4, String str5, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            str = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
        }
        this(str, j, str2, str3, str4, (i & 32) != 0 ? "" : str5, (i & 64) != 0 ? System.currentTimeMillis() : j2);
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

    public final String getSantriId() {
        return this.santriId;
    }

    public final String getStatus() {
        return this.status;
    }

    public final String getCatatan() {
        return this.catatan;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
