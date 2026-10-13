package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.Hari;
import com.rork.simkbmustaz.data.local.JadwalEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import com.rork.simkbmustaz.util.Waktu;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.comparisons.ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JadwalViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00006\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u000e\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B1\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0006\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\t\u0010\u0013\u001a\u00020\u0006HÆ\u0003J\u000f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\b0\u0003HÆ\u0003J3\u0010\u0015\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00062\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003HÆ\u0001J\u0014\u0010\u0016\u001a\u00020\u00172\b\u0010\u0018\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0019\u001a\u00020\u001aHÖ\u0081\u0004J\n\u0010\u001b\u001a\u00020\u001cHÖ\u0081\u0004R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0011\u0010\u0005\u001a\u00020\u0006¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\fR\u0017\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\f¨\u0006\u001d"}, d2 = {"Lcom/rork/simkbmustaz/ui/viewmodel/JadwalUiState;", "", "semuaJadwal", "", "Lcom/rork/simkbmustaz/data/local/JadwalEntity;", "hariDipilih", "Lcom/rork/simkbmustaz/data/local/Hari;", "kelasList", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "<init>", "(Ljava/util/List;Lcom/rork/simkbmustaz/data/local/Hari;Ljava/util/List;)V", "getSemuaJadwal", "()Ljava/util/List;", "getHariDipilih", "()Lcom/rork/simkbmustaz/data/local/Hari;", "getKelasList", "jadwalHariIni", "getJadwalHariIni", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class JadwalUiState {
    public static final int $stable = 8;
    private final Hari hariDipilih;
    private final List<JadwalEntity> jadwalHariIni;
    private final List<KelasEntity> kelasList;
    private final List<JadwalEntity> semuaJadwal;

    public JadwalUiState() {
        this(null, null, null, 7, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JadwalUiState copy$default(JadwalUiState jadwalUiState, List list, Hari hari, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = jadwalUiState.semuaJadwal;
        }
        if ((i & 2) != 0) {
            hari = jadwalUiState.hariDipilih;
        }
        if ((i & 4) != 0) {
            list2 = jadwalUiState.kelasList;
        }
        return jadwalUiState.copy(list, hari, list2);
    }

    public final List<JadwalEntity> component1() {
        return this.semuaJadwal;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final Hari getHariDipilih() {
        return this.hariDipilih;
    }

    public final List<KelasEntity> component3() {
        return this.kelasList;
    }

    public final JadwalUiState copy(List<JadwalEntity> semuaJadwal, Hari hariDipilih, List<KelasEntity> kelasList) {
        Intrinsics.checkNotNullParameter(semuaJadwal, "semuaJadwal");
        Intrinsics.checkNotNullParameter(hariDipilih, "hariDipilih");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        return new JadwalUiState(semuaJadwal, hariDipilih, kelasList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JadwalUiState)) {
            return false;
        }
        JadwalUiState jadwalUiState = (JadwalUiState) other;
        return Intrinsics.areEqual(this.semuaJadwal, jadwalUiState.semuaJadwal) && this.hariDipilih == jadwalUiState.hariDipilih && Intrinsics.areEqual(this.kelasList, jadwalUiState.kelasList);
    }

    public int hashCode() {
        return (((this.semuaJadwal.hashCode() * 31) + this.hariDipilih.hashCode()) * 31) + this.kelasList.hashCode();
    }

    public String toString() {
        return "JadwalUiState(semuaJadwal=" + this.semuaJadwal + ", hariDipilih=" + this.hariDipilih + ", kelasList=" + this.kelasList + ")";
    }

    public JadwalUiState(List<JadwalEntity> semuaJadwal, Hari hariDipilih, List<KelasEntity> kelasList) {
        Intrinsics.checkNotNullParameter(semuaJadwal, "semuaJadwal");
        Intrinsics.checkNotNullParameter(hariDipilih, "hariDipilih");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        this.semuaJadwal = semuaJadwal;
        this.hariDipilih = hariDipilih;
        this.kelasList = kelasList;
        ArrayList arrayList = new ArrayList();
        for (Object obj : semuaJadwal) {
            if (Intrinsics.areEqual(((JadwalEntity) obj).getHari(), this.hariDipilih.name())) {
                arrayList.add(obj);
            }
        }
        this.jadwalHariIni = CollectionsKt.sortedWith(arrayList, new Comparator() { // from class: com.rork.simkbmustaz.ui.viewmodel.JadwalUiState$special$$inlined$sortedBy$1
            /* JADX WARN: Multi-variable type inference failed */
            @Override // java.util.Comparator
            public final int compare(T t, T t2) {
                return ComparisonsKt.compareValues(((JadwalEntity) t).getJamMulai(), ((JadwalEntity) t2).getJamMulai());
            }
        });
    }

    public /* synthetic */ JadwalUiState(List list, Hari hari, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? Hari.INSTANCE.dari(Waktu.INSTANCE.hariIni()) : hari, (i & 4) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final List<JadwalEntity> getSemuaJadwal() {
        return this.semuaJadwal;
    }

    public final Hari getHariDipilih() {
        return this.hariDipilih;
    }

    public final List<KelasEntity> getKelasList() {
        return this.kelasList;
    }

    public final List<JadwalEntity> getJadwalHariIni() {
        return this.jadwalHariIni;
    }
}
