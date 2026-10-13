package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DataEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BK\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\tHÆ\u0003J\t\u0010\u001c\u001a\u00020\tHÆ\u0003JO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tHÆ\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010!\u001a\u00020\"HÖ\u0081\u0004J\n\u0010#\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0014¨\u0006$"}, d2 = {"Lcom/rork/simkbmustaz/data/local/KelasEntity;", "", "id", "", "nama", "lembaga", "tingkat", "jenisKelamin", "createdAt", "", "updatedAt", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getNama", "getLembaga", "getTingkat", "getJenisKelamin", "getCreatedAt", "()J", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class KelasEntity {
    public static final int $stable = 0;
    private final long createdAt;
    private final String id;
    private final String jenisKelamin;
    private final String lembaga;
    private final String nama;
    private final String tingkat;
    private final long updatedAt;

    public static /* synthetic */ KelasEntity copy$default(KelasEntity kelasEntity, String str, String str2, String str3, String str4, String str5, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = kelasEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = kelasEntity.nama;
        }
        if ((i & 4) != 0) {
            str3 = kelasEntity.lembaga;
        }
        if ((i & 8) != 0) {
            str4 = kelasEntity.tingkat;
        }
        if ((i & 16) != 0) {
            str5 = kelasEntity.jenisKelamin;
        }
        if ((i & 32) != 0) {
            j = kelasEntity.createdAt;
        }
        if ((i & 64) != 0) {
            j2 = kelasEntity.updatedAt;
        }
        long j3 = j2;
        long j4 = j;
        String str6 = str5;
        String str7 = str3;
        return kelasEntity.copy(str, str2, str7, str4, str6, j4, j3);
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
    public final String getLembaga() {
        return this.lembaga;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getTingkat() {
        return this.tingkat;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getJenisKelamin() {
        return this.jenisKelamin;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final KelasEntity copy(String id, String nama, String lembaga, String tingkat, String jenisKelamin, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nama, "nama");
        Intrinsics.checkNotNullParameter(lembaga, "lembaga");
        Intrinsics.checkNotNullParameter(tingkat, "tingkat");
        Intrinsics.checkNotNullParameter(jenisKelamin, "jenisKelamin");
        return new KelasEntity(id, nama, lembaga, tingkat, jenisKelamin, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof KelasEntity)) {
            return false;
        }
        KelasEntity kelasEntity = (KelasEntity) other;
        return Intrinsics.areEqual(this.id, kelasEntity.id) && Intrinsics.areEqual(this.nama, kelasEntity.nama) && Intrinsics.areEqual(this.lembaga, kelasEntity.lembaga) && Intrinsics.areEqual(this.tingkat, kelasEntity.tingkat) && Intrinsics.areEqual(this.jenisKelamin, kelasEntity.jenisKelamin) && this.createdAt == kelasEntity.createdAt && this.updatedAt == kelasEntity.updatedAt;
    }

    public int hashCode() {
        return (((((((((((this.id.hashCode() * 31) + this.nama.hashCode()) * 31) + this.lembaga.hashCode()) * 31) + this.tingkat.hashCode()) * 31) + this.jenisKelamin.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "KelasEntity(id=" + this.id + ", nama=" + this.nama + ", lembaga=" + this.lembaga + ", tingkat=" + this.tingkat + ", jenisKelamin=" + this.jenisKelamin + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public KelasEntity(String id, String nama, String lembaga, String tingkat, String jenisKelamin, long j, long j2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(nama, "nama");
        Intrinsics.checkNotNullParameter(lembaga, "lembaga");
        Intrinsics.checkNotNullParameter(tingkat, "tingkat");
        Intrinsics.checkNotNullParameter(jenisKelamin, "jenisKelamin");
        this.id = id;
        this.nama = nama;
        this.lembaga = lembaga;
        this.tingkat = tingkat;
        this.jenisKelamin = jenisKelamin;
        this.createdAt = j;
        this.updatedAt = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ KelasEntity(String str, String str2, String str3, String str4, String str5, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            str = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
        }
        this(str, str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? System.currentTimeMillis() : j, (i & 64) != 0 ? System.currentTimeMillis() : j2);
    }

    public final String getId() {
        return this.id;
    }

    public final String getNama() {
        return this.nama;
    }

    public final String getLembaga() {
        return this.lembaga;
    }

    public final String getTingkat() {
        return this.tingkat;
    }

    public final String getJenisKelamin() {
        return this.jenisKelamin;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
