package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DataEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010\t\n\u0002\b\u0016\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BW\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u0012\b\b\u0002\u0010\n\u001a\u00020\u000b¢\u0006\u0004\b\f\u0010\rJ\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001e\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001f\u001a\u00020\u000bHÆ\u0003JY\u0010 \u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00032\b\b\u0002\u0010\n\u001a\u00020\u000bHÆ\u0001J\u0014\u0010!\u001a\u00020\"2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010$\u001a\u00020%HÖ\u0081\u0004J\n\u0010&\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000fR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000fR\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000fR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000fR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u000fR\u0011\u0010\t\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u000fR\u0011\u0010\n\u001a\u00020\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017¨\u0006'"}, d2 = {"Lcom/rork/simkbmustaz/data/local/ProfilEntity;", "", "id", "", "namaLengkap", "jabatan", "nomorTelepon", NotificationCompat.CATEGORY_EMAIL, "lembaga", "alamat", "updatedAt", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;J)V", "getId", "()Ljava/lang/String;", "getNamaLengkap", "getJabatan", "getNomorTelepon", "getEmail", "getLembaga", "getAlamat", "getUpdatedAt", "()J", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class ProfilEntity {
    public static final int $stable = 0;
    private final String alamat;
    private final String email;
    private final String id;
    private final String jabatan;
    private final String lembaga;
    private final String namaLengkap;
    private final String nomorTelepon;
    private final long updatedAt;

    public ProfilEntity() {
        this(null, null, null, null, null, null, null, 0L, 255, null);
    }

    public static /* synthetic */ ProfilEntity copy$default(ProfilEntity profilEntity, String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, int i, Object obj) {
        if ((i & 1) != 0) {
            str = profilEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = profilEntity.namaLengkap;
        }
        if ((i & 4) != 0) {
            str3 = profilEntity.jabatan;
        }
        if ((i & 8) != 0) {
            str4 = profilEntity.nomorTelepon;
        }
        if ((i & 16) != 0) {
            str5 = profilEntity.email;
        }
        if ((i & 32) != 0) {
            str6 = profilEntity.lembaga;
        }
        if ((i & 64) != 0) {
            str7 = profilEntity.alamat;
        }
        if ((i & 128) != 0) {
            j = profilEntity.updatedAt;
        }
        long j2 = j;
        String str8 = str6;
        String str9 = str7;
        String str10 = str5;
        String str11 = str3;
        return profilEntity.copy(str, str2, str11, str4, str10, str8, str9, j2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getNamaLengkap() {
        return this.namaLengkap;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getJabatan() {
        return this.jabatan;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getNomorTelepon() {
        return this.nomorTelepon;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getEmail() {
        return this.email;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getLembaga() {
        return this.lembaga;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final String getAlamat() {
        return this.alamat;
    }

    /* JADX INFO: renamed from: component8, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final ProfilEntity copy(String id, String namaLengkap, String jabatan, String nomorTelepon, String email, String lembaga, String alamat, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namaLengkap, "namaLengkap");
        Intrinsics.checkNotNullParameter(jabatan, "jabatan");
        Intrinsics.checkNotNullParameter(nomorTelepon, "nomorTelepon");
        Intrinsics.checkNotNullParameter(email, "email");
        Intrinsics.checkNotNullParameter(lembaga, "lembaga");
        Intrinsics.checkNotNullParameter(alamat, "alamat");
        return new ProfilEntity(id, namaLengkap, jabatan, nomorTelepon, email, lembaga, alamat, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof ProfilEntity)) {
            return false;
        }
        ProfilEntity profilEntity = (ProfilEntity) other;
        return Intrinsics.areEqual(this.id, profilEntity.id) && Intrinsics.areEqual(this.namaLengkap, profilEntity.namaLengkap) && Intrinsics.areEqual(this.jabatan, profilEntity.jabatan) && Intrinsics.areEqual(this.nomorTelepon, profilEntity.nomorTelepon) && Intrinsics.areEqual(this.email, profilEntity.email) && Intrinsics.areEqual(this.lembaga, profilEntity.lembaga) && Intrinsics.areEqual(this.alamat, profilEntity.alamat) && this.updatedAt == profilEntity.updatedAt;
    }

    public int hashCode() {
        return (((((((((((((this.id.hashCode() * 31) + this.namaLengkap.hashCode()) * 31) + this.jabatan.hashCode()) * 31) + this.nomorTelepon.hashCode()) * 31) + this.email.hashCode()) * 31) + this.lembaga.hashCode()) * 31) + this.alamat.hashCode()) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "ProfilEntity(id=" + this.id + ", namaLengkap=" + this.namaLengkap + ", jabatan=" + this.jabatan + ", nomorTelepon=" + this.nomorTelepon + ", email=" + this.email + ", lembaga=" + this.lembaga + ", alamat=" + this.alamat + ", updatedAt=" + this.updatedAt + ")";
    }

    public ProfilEntity(String id, String namaLengkap, String jabatan, String nomorTelepon, String email, String lembaga, String alamat, long j) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(namaLengkap, "namaLengkap");
        Intrinsics.checkNotNullParameter(jabatan, "jabatan");
        Intrinsics.checkNotNullParameter(nomorTelepon, "nomorTelepon");
        Intrinsics.checkNotNullParameter(email, "email");
        Intrinsics.checkNotNullParameter(lembaga, "lembaga");
        Intrinsics.checkNotNullParameter(alamat, "alamat");
        this.id = id;
        this.namaLengkap = namaLengkap;
        this.jabatan = jabatan;
        this.nomorTelepon = nomorTelepon;
        this.email = email;
        this.lembaga = lembaga;
        this.alamat = alamat;
        this.updatedAt = j;
    }

    public /* synthetic */ ProfilEntity(String str, String str2, String str3, String str4, String str5, String str6, String str7, long j, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? "profil" : str, (i & 2) != 0 ? "" : str2, (i & 4) != 0 ? "" : str3, (i & 8) != 0 ? "" : str4, (i & 16) != 0 ? "" : str5, (i & 32) != 0 ? "" : str6, (i & 64) != 0 ? "" : str7, (i & 128) != 0 ? System.currentTimeMillis() : j);
    }

    public final String getId() {
        return this.id;
    }

    public final String getNamaLengkap() {
        return this.namaLengkap;
    }

    public final String getJabatan() {
        return this.jabatan;
    }

    public final String getNomorTelepon() {
        return this.nomorTelepon;
    }

    public final String getEmail() {
        return this.email;
    }

    public final String getLembaga() {
        return this.lembaga;
    }

    public final String getAlamat() {
        return this.alamat;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
