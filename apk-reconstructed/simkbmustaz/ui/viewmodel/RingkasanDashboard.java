package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.AgendaEntity;
import com.rork.simkbmustaz.data.local.JadwalEntity;
import com.rork.simkbmustaz.data.local.StatusKehadiran;
import com.rork.simkbmustaz.data.local.TahunAjaranEntity;
import java.util.List;
import java.util.Map;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.collections.MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DashboardViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000B\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010$\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0014\n\u0002\u0010\u000b\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B]\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0004\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b\u0012\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000b\u0012\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\u0004\b\u000f\u0010\u0010J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\u000b\u0010\u001d\u001a\u0004\u0018\u00010\u0006HÆ\u0003J\u000f\u0010\u001e\u001a\b\u0012\u0004\u0012\u00020\t0\bHÆ\u0003J\u0015\u0010\u001f\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000bHÆ\u0003J\u000f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u000e0\bHÆ\u0003J_\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\n\b\u0002\u0010\u0005\u001a\u0004\u0018\u00010\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b2\u0014\b\u0002\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000b2\u000e\b\u0002\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\bHÆ\u0001J\u0014\u0010\"\u001a\u00020#2\b\u0010$\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010%\u001a\u00020\u0003HÖ\u0081\u0004J\n\u0010&\u001a\u00020'HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0012R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0012R\u0013\u0010\u0005\u001a\u0004\u0018\u00010\u0006¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\t0\b¢\u0006\b\n\u0000\u001a\u0004\b\u0016\u0010\u0017R\u001d\u0010\n\u001a\u000e\u0012\u0004\u0012\u00020\f\u0012\u0004\u0012\u00020\u00030\u000b¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u000e0\b¢\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0017¨\u0006("}, d2 = {"Lcom/rork/simkbmustaz/ui/viewmodel/RingkasanDashboard;", "", "jumlahKelas", "", "jumlahSantriAktif", "periodeAktif", "Lcom/rork/simkbmustaz/data/local/TahunAjaranEntity;", "jadwalHariIni", "", "Lcom/rork/simkbmustaz/data/local/JadwalEntity;", "absensiHariIni", "", "Lcom/rork/simkbmustaz/data/local/StatusKehadiran;", "agendaMendatang", "Lcom/rork/simkbmustaz/data/local/AgendaEntity;", "<init>", "(IILcom/rork/simkbmustaz/data/local/TahunAjaranEntity;Ljava/util/List;Ljava/util/Map;Ljava/util/List;)V", "getJumlahKelas", "()I", "getJumlahSantriAktif", "getPeriodeAktif", "()Lcom/rork/simkbmustaz/data/local/TahunAjaranEntity;", "getJadwalHariIni", "()Ljava/util/List;", "getAbsensiHariIni", "()Ljava/util/Map;", "getAgendaMendatang", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "", "other", "hashCode", "toString", "", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class RingkasanDashboard {
    public static final int $stable = 8;
    private final Map<StatusKehadiran, Integer> absensiHariIni;
    private final List<AgendaEntity> agendaMendatang;
    private final List<JadwalEntity> jadwalHariIni;
    private final int jumlahKelas;
    private final int jumlahSantriAktif;
    private final TahunAjaranEntity periodeAktif;

    public RingkasanDashboard() {
        this(0, 0, null, null, null, null, 63, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ RingkasanDashboard copy$default(RingkasanDashboard ringkasanDashboard, int i, int i2, TahunAjaranEntity tahunAjaranEntity, List list, Map map, List list2, int i3, Object obj) {
        if ((i3 & 1) != 0) {
            i = ringkasanDashboard.jumlahKelas;
        }
        if ((i3 & 2) != 0) {
            i2 = ringkasanDashboard.jumlahSantriAktif;
        }
        if ((i3 & 4) != 0) {
            tahunAjaranEntity = ringkasanDashboard.periodeAktif;
        }
        if ((i3 & 8) != 0) {
            list = ringkasanDashboard.jadwalHariIni;
        }
        if ((i3 & 16) != 0) {
            map = ringkasanDashboard.absensiHariIni;
        }
        if ((i3 & 32) != 0) {
            list2 = ringkasanDashboard.agendaMendatang;
        }
        Map map2 = map;
        List list3 = list2;
        return ringkasanDashboard.copy(i, i2, tahunAjaranEntity, list, map2, list3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final int getJumlahKelas() {
        return this.jumlahKelas;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final int getJumlahSantriAktif() {
        return this.jumlahSantriAktif;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final TahunAjaranEntity getPeriodeAktif() {
        return this.periodeAktif;
    }

    public final List<JadwalEntity> component4() {
        return this.jadwalHariIni;
    }

    public final Map<StatusKehadiran, Integer> component5() {
        return this.absensiHariIni;
    }

    public final List<AgendaEntity> component6() {
        return this.agendaMendatang;
    }

    public final RingkasanDashboard copy(int jumlahKelas, int jumlahSantriAktif, TahunAjaranEntity periodeAktif, List<JadwalEntity> jadwalHariIni, Map<StatusKehadiran, Integer> absensiHariIni, List<AgendaEntity> agendaMendatang) {
        Intrinsics.checkNotNullParameter(jadwalHariIni, "jadwalHariIni");
        Intrinsics.checkNotNullParameter(absensiHariIni, "absensiHariIni");
        Intrinsics.checkNotNullParameter(agendaMendatang, "agendaMendatang");
        return new RingkasanDashboard(jumlahKelas, jumlahSantriAktif, periodeAktif, jadwalHariIni, absensiHariIni, agendaMendatang);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof RingkasanDashboard)) {
            return false;
        }
        RingkasanDashboard ringkasanDashboard = (RingkasanDashboard) other;
        return this.jumlahKelas == ringkasanDashboard.jumlahKelas && this.jumlahSantriAktif == ringkasanDashboard.jumlahSantriAktif && Intrinsics.areEqual(this.periodeAktif, ringkasanDashboard.periodeAktif) && Intrinsics.areEqual(this.jadwalHariIni, ringkasanDashboard.jadwalHariIni) && Intrinsics.areEqual(this.absensiHariIni, ringkasanDashboard.absensiHariIni) && Intrinsics.areEqual(this.agendaMendatang, ringkasanDashboard.agendaMendatang);
    }

    public int hashCode() {
        int iHashCode = ((Integer.hashCode(this.jumlahKelas) * 31) + Integer.hashCode(this.jumlahSantriAktif)) * 31;
        TahunAjaranEntity tahunAjaranEntity = this.periodeAktif;
        return ((((((iHashCode + (tahunAjaranEntity == null ? 0 : tahunAjaranEntity.hashCode())) * 31) + this.jadwalHariIni.hashCode()) * 31) + this.absensiHariIni.hashCode()) * 31) + this.agendaMendatang.hashCode();
    }

    public String toString() {
        return "RingkasanDashboard(jumlahKelas=" + this.jumlahKelas + ", jumlahSantriAktif=" + this.jumlahSantriAktif + ", periodeAktif=" + this.periodeAktif + ", jadwalHariIni=" + this.jadwalHariIni + ", absensiHariIni=" + this.absensiHariIni + ", agendaMendatang=" + this.agendaMendatang + ")";
    }

    public RingkasanDashboard(int i, int i2, TahunAjaranEntity tahunAjaranEntity, List<JadwalEntity> jadwalHariIni, Map<StatusKehadiran, Integer> absensiHariIni, List<AgendaEntity> agendaMendatang) {
        Intrinsics.checkNotNullParameter(jadwalHariIni, "jadwalHariIni");
        Intrinsics.checkNotNullParameter(absensiHariIni, "absensiHariIni");
        Intrinsics.checkNotNullParameter(agendaMendatang, "agendaMendatang");
        this.jumlahKelas = i;
        this.jumlahSantriAktif = i2;
        this.periodeAktif = tahunAjaranEntity;
        this.jadwalHariIni = jadwalHariIni;
        this.absensiHariIni = absensiHariIni;
        this.agendaMendatang = agendaMendatang;
    }

    public final int getJumlahKelas() {
        return this.jumlahKelas;
    }

    public final int getJumlahSantriAktif() {
        return this.jumlahSantriAktif;
    }

    public final TahunAjaranEntity getPeriodeAktif() {
        return this.periodeAktif;
    }

    public /* synthetic */ RingkasanDashboard(int i, int i2, TahunAjaranEntity tahunAjaranEntity, List list, Map map, List list2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? 0 : i, (i3 & 2) != 0 ? 0 : i2, (i3 & 4) != 0 ? null : tahunAjaranEntity, (i3 & 8) != 0 ? CollectionsKt.emptyList() : list, (i3 & 16) != 0 ? MapsKt.emptyMap() : map, (i3 & 32) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final List<JadwalEntity> getJadwalHariIni() {
        return this.jadwalHariIni;
    }

    public final Map<StatusKehadiran, Integer> getAbsensiHariIni() {
        return this.absensiHariIni;
    }

    public final List<AgendaEntity> getAgendaMendatang() {
        return this.agendaMendatang;
    }
}
