package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\t\n\u0002\b\u0019\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BI\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0007\u001a\u00020\u0003\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0005\u0012\b\b\u0002\u0010\n\u001a\u00020\u0005¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0005HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0019\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0005HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0005HÆ\u0003JO\u0010\u001d\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\b\b\u0002\u0010\u0007\u001a\u00020\u00032\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u00052\b\b\u0002\u0010\n\u001a\u00020\u0005HÆ\u0001J\u0014\u0010\u001e\u001a\u00020\u001f2\b\u0010 \u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010!\u001a\u00020\"HÖ\u0081\u0004J\n\u0010#\u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u000eR\u0011\u0010\u0007\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u000eR\u0011\u0010\b\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u000eR\u0011\u0010\t\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0010R\u0011\u0010\n\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0010¨\u0006$"}, d2 = {"Lcom/rork/simkbmustaz/data/local/AgendaEntity;", "", "id", "", "tanggal", "", "judul", "waktu", "keterangan", "createdAt", "updatedAt", "<init>", "(Ljava/lang/String;JLjava/lang/String;Ljava/lang/String;Ljava/lang/String;JJ)V", "getId", "()Ljava/lang/String;", "getTanggal", "()J", "getJudul", "getWaktu", "getKeterangan", "getCreatedAt", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class AgendaEntity {
    public static final int $stable = 0;
    private final long createdAt;
    private final String id;
    private final String judul;
    private final String keterangan;
    private final long tanggal;
    private final long updatedAt;
    private final String waktu;

    public static /* synthetic */ AgendaEntity copy$default(AgendaEntity agendaEntity, String str, long j, String str2, String str3, String str4, long j2, long j3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = agendaEntity.id;
        }
        if ((i & 2) != 0) {
            j = agendaEntity.tanggal;
        }
        if ((i & 4) != 0) {
            str2 = agendaEntity.judul;
        }
        if ((i & 8) != 0) {
            str3 = agendaEntity.waktu;
        }
        if ((i & 16) != 0) {
            str4 = agendaEntity.keterangan;
        }
        if ((i & 32) != 0) {
            j2 = agendaEntity.createdAt;
        }
        if ((i & 64) != 0) {
            j3 = agendaEntity.updatedAt;
        }
        long j4 = j3;
        String str5 = str4;
        String str6 = str2;
        return agendaEntity.copy(str, j, str6, str3, str5, j2, j4);
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
    public final String getJudul() {
        return this.judul;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final String getWaktu() {
        return this.waktu;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final String getKeterangan() {
        return this.keterangan;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final AgendaEntity copy(String id, long tanggal, String judul, String waktu, String keterangan, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(judul, "judul");
        Intrinsics.checkNotNullParameter(waktu, "waktu");
        Intrinsics.checkNotNullParameter(keterangan, "keterangan");
        return new AgendaEntity(id, tanggal, judul, waktu, keterangan, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof AgendaEntity)) {
            return false;
        }
        AgendaEntity agendaEntity = (AgendaEntity) other;
        return Intrinsics.areEqual(this.id, agendaEntity.id) && this.tanggal == agendaEntity.tanggal && Intrinsics.areEqual(this.judul, agendaEntity.judul) && Intrinsics.areEqual(this.waktu, agendaEntity.waktu) && Intrinsics.areEqual(this.keterangan, agendaEntity.keterangan) && this.createdAt == agendaEntity.createdAt && this.updatedAt == agendaEntity.updatedAt;
    }

    public int hashCode() {
        return (((((((((((this.id.hashCode() * 31) + Long.hashCode(this.tanggal)) * 31) + this.judul.hashCode()) * 31) + this.waktu.hashCode()) * 31) + this.keterangan.hashCode()) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "AgendaEntity(id=" + this.id + ", tanggal=" + this.tanggal + ", judul=" + this.judul + ", waktu=" + this.waktu + ", keterangan=" + this.keterangan + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public AgendaEntity(String id, long j, String judul, String waktu, String keterangan, long j2, long j3) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(judul, "judul");
        Intrinsics.checkNotNullParameter(waktu, "waktu");
        Intrinsics.checkNotNullParameter(keterangan, "keterangan");
        this.id = id;
        this.tanggal = j;
        this.judul = judul;
        this.waktu = waktu;
        this.keterangan = keterangan;
        this.createdAt = j2;
        this.updatedAt = j3;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ AgendaEntity(String str, long j, String str2, String str3, String str4, long j2, long j3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            str = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
        }
        this(str, j, str2, (i & 8) != 0 ? "" : str3, (i & 16) != 0 ? "" : str4, (i & 32) != 0 ? System.currentTimeMillis() : j2, (i & 64) != 0 ? System.currentTimeMillis() : j3);
    }

    public final String getId() {
        return this.id;
    }

    public final long getTanggal() {
        return this.tanggal;
    }

    public final String getJudul() {
        return this.judul;
    }

    public final String getWaktu() {
        return this.waktu;
    }

    public final String getKeterangan() {
        return this.keterangan;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
