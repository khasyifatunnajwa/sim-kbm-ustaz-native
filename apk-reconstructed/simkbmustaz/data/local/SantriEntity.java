package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.core.app.NotificationCompat;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DataEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001Ba\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b\u0012\b\b\u0002\u0010\f\u001a\u00020\u000b¢\u0006\u0004\b\r\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\u0003HÆ\u0003J\t\u0010!\u001a\u00020\u000bHÆ\u0003J\t\u0010\"\u001a\u00020\u000bHÆ\u0003Je\u0010#\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\n\b\u0002\u0010\u0006\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000b2\b\b\u0002\u0010\f\u001a\u00020\u000bHÆ\u0001J\u0014\u0010$\u001a\u00020%2\b\u0010&\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010'\u001a\u00020(HÖ\u0081\u0004J\n\u0010)\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0013\u0010\u0006\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0010R\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010R\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0010R\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0011\u0010\f\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018¨\u0006*"}, d2 = {"Lcom/rork/simkbmustaz/data/local/SantriEntity;", "", "id", "", "nama", "nomorInduk", "kelasId", "jenisKelamin", NotificationCompat.CATEGORY_STATUS, "catatan", "createdAt", "", "updatedAt", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getNama", "getNomorInduk", "getKelasId", "getJenisKelamin", "getStatus", "getCatatan", "getCreatedAt", "()J", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class SantriEntity {
    public static final int $stable = 0;
    private final String catatan;
    private final long createdAt;
    private final String id;
    private final String jenisKelamin;
    private final String kelasId;
    private final String nama;
    private final String nomorInduk;
    private final String status;
    private final long updatedAt;

    public static /* synthetic */ SantriEntity copy$default(SantriEntity santriEntity, String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = santriEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = santriEntity.nama;
        }
        if ((i & 4) != 0) {
            str3 = santriEntity.nomorInduk;
        }
        if ((i & 8) != 0) {
            str4 = santriEntity.kelasId;
        }
        if ((i & 16) != 0) {
            str5 = santriEntity.jenisKelamin;
        }
        if ((i & 32) != 0) {
            str6 = santriEntity.status;
        }
        if ((i & 64) != 0) {
            str7 = santriEntity.catatan;
        }
        if ((i & 128) != 0) {
            j = santriEntity.createdAt;
        }
        if ((i & 256) != 0) {
            j2 = santriEntity.updatedAt;
        }
        long j3 = j2;
        long j4 = j;
        String str8 = str6;
        String str9 = str7;
        String str10 = str5;
        String str11 = str3;
        return santriEntity.copy(str, str2, str11, str4, str10, str8, str9, j4, j3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getNama() {
        return this.nama;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getNomorInduk() {
        return this.nomorInduk;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getKelasId() {
        return this.kelasId;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getJenisKelamin() {
        return this.jenisKelamin;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getCatatan() {
        return this.catatan;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* JADX INFO: renamed from: component9, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final SantriEntity copy(String id, String nama, String nomorInduk, String kelasId, String jenisKelamin, String status, String catatan, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nama, "nama");
        Intrinsics.checkNotNullParameter(nomorInduk, "nomorInduk");
        Intrinsics.checkNotNullParameter(jenisKelamin, "jenisKelamin");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        return new SantriEntity(id, nama, nomorInduk, kelasId, jenisKelamin, status, catatan, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof SantriEntity)) {
            return false;
        }
        SantriEntity santriEntity = (SantriEntity) other;
        return Intrinsics.areEqual(this.id, santriEntity.id) && Intrinsics.areEqual(this.nama, santriEntity.nama) && Intrinsics.areEqual(this.nomorInduk, santriEntity.nomorInduk) && Intrinsics.areEqual(this.kelasId, santriEntity.kelasId) && Intrinsics.areEqual(this.jenisKelamin, santriEntity.jenisKelamin) && Intrinsics.areEqual(this.status, santriEntity.status) && Intrinsics.areEqual(this.catatan, santriEntity.catatan) && this.createdAt == santriEntity.createdAt && this.updatedAt == santriEntity.updatedAt;
    }

    public int hashCode() {
        int iHashCode = ((((this.id.hashCode() * 31) + this.nama.hashCode()) * 31) + this.nomorInduk.hashCode()) * 31;
        String str = this.kelasId;
        return ((((((((((iHashCode + (str == null ? 0 : str.hashCode())) * 31) + this.jenisKelamin.hashCode()) * 31) + this.status.hashCode()) * 31) + this.catatan.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "SantriEntity(id=" + this.id + ", nama=" + this.nama + ", nomorInduk=" + this.nomorInduk + ", kelasId=" + this.kelasId + ", jenisKelamin=" + this.jenisKelamin + ", status=" + this.status + ", catatan=" + this.catatan + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public SantriEntity(String id, String nama, String nomorInduk, String str, String jenisKelamin, String status, String catatan, long j, long j2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nama, "nama");
        Intrinsics.checkNotNullParameter(nomorInduk, "nomorInduk");
        Intrinsics.checkNotNullParameter(jenisKelamin, "jenisKelamin");
        Intrinsics.checkNotNullParameter(status, "status");
        Intrinsics.checkNotNullParameter(catatan, "catatan");
        this.id = id;
        this.nama = nama;
        this.nomorInduk = nomorInduk;
        this.kelasId = str;
        this.jenisKelamin = jenisKelamin;
        this.status = status;
        this.catatan = catatan;
        this.createdAt = j;
        this.updatedAt = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ SantriEntity(String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        String str8;
        if ((i & 1) != 0) {
            String string = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(string, "toString(...)");
            str8 = string;
        } else {
            str8 = str;
        }
        this(str8, str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "AKTIF" : str6, (i & 64) != 0 ? "" : str7, (i & 128) != 0 ? System.currentTimeMillis() : j, (i & 256) != 0 ? System.currentTimeMillis() : j2);
    }

    public final String getId() {
        return this.id;
    }

    public final String getNama() {
        return this.nama;
    }

    public final String getNomorInduk() {
        return this.nomorInduk;
    }

    public final String getKelasId() {
        return this.kelasId;
    }

    public final String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public final String getStatus() {
        return this.status;
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
