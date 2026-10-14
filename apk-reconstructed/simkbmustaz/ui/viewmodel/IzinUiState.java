package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.IzinMengajarEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: IzinMengajarViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\t\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u0087\b\u0018\u00002\u00020\u0001B'\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\u0004\b\u0007\u0010\bJ\u000f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\r\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J)\u0010\u000e\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0001J\u0014\u0010\u000f\u001a\u00020\u00102\b\u0010\u0011\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0012\u001a\u00020\u0013HÖ\u0081\u0004J\n\u0010\u0014\u001a\u00020\u0015HÖ\u0081\u0004R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\nR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\n¨\u0006\u0016"}, d2 = {"Lcom/rork/simkbmustaz/ui/viewmodel/IzinUiState;", "", "izinList", "", "Lcom/rork/simkbmustaz/data/local/IzinMengajarEntity;", "kelasList", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "<init>", "(Ljava/util/List;Ljava/util/List;)V", "getIzinList", "()Ljava/util/List;", "getKelasList", "component1", "component2", "copy", "equals", "", "other", "hashCode", "", "toString", "", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class IzinUiState {
    public static final int $stable = 8;
    private final List<IzinMengajarEntity> izinList;
    private final List<KelasEntity> kelasList;

    /* JADX WARN: Multi-variable type inference failed */
    public IzinUiState() {
        this(null, 0 == true ? 1 : 0, 3, 0 == true ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ IzinUiState copy$default(IzinUiState izinUiState, List list, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            list = izinUiState.izinList;
        }
        if ((i & 2) != 0) {
            list2 = izinUiState.kelasList;
        }
        return izinUiState.copy(list, list2);
    }

    public final List<IzinMengajarEntity> component1() {
        return this.izinList;
    }

    public final List<KelasEntity> component2() {
        return this.kelasList;
    }

    public final IzinUiState copy(List<IzinMengajarEntity> izinList, List<KelasEntity> kelasList) {
        Intrinsics.checkNotNullParameter(izinList, "izinList");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        return new IzinUiState(izinList, kelasList);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof IzinUiState)) {
            return false;
        }
        IzinUiState izinUiState = (IzinUiState) other;
        return Intrinsics.areEqual(this.izinList, izinUiState.izinList) && Intrinsics.areEqual(this.kelasList, izinUiState.kelasList);
    }

    public int hashCode() {
        return (this.izinList.hashCode() * 31) + this.kelasList.hashCode();
    }

    public String toString() {
        return "IzinUiState(izinList=" + this.izinList + ", kelasList=" + this.kelasList + ")";
    }

    public IzinUiState(List<IzinMengajarEntity> izinList, List<KelasEntity> kelasList) {
        Intrinsics.checkNotNullParameter(izinList, "izinList");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        this.izinList = izinList;
        this.kelasList = kelasList;
    }

    public /* synthetic */ IzinUiState(List list, List list2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2);
    }

    public final List<IzinMengajarEntity> getIzinList() {
        return this.izinList;
    }

    public final List<KelasEntity> getKelasList() {
        return this.kelasList;
    }
}
