package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.JurnalKbmEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JurnalViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B3\u0012\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003\u0012\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\u0004\b\t\u0010\nJ\u000f\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003HÆ\u0003J\u000f\u0010\u0011\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003HÆ\u0003J\u000b\u0010\u0012\u001a\u0004\u0018\u00010\bHÆ\u0003J5\u0010\u0013\u001a\u00020\u00002\u000e\b\u0002\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\u000e\b\u0002\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\bHÆ\u0001J\u0014\u0010\u0014\u001a\u00020\u00152\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0017\u001a\u00020\u0018HÖ\u0081\u0004J\n\u0010\u0019\u001a\u00020\bHÖ\u0081\u0004R\u0017\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\fR\u0017\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\fR\u0013\u0010\u0007\u001a\u0004\u0018\u00010\b¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u001a"}, d2 = {"Lcom/rork/simkbmustaz/ui/viewmodel/JurnalUiState;", "", "jurnalList", "", "Lcom/rork/simkbmustaz/data/local/JurnalKbmEntity;", "kelasList", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "filterKelasId", "", "<init>", "(Ljava/util/List;Ljava/util/List;Ljava/lang/String;)V", "getJurnalList", "()Ljava/util/List;", "getKelasList", "getFilterKelasId", "()Ljava/lang/String;", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class JurnalUiState {
    public static final int $stable = 8;
    private final String filterKelasId;
    private final List<JurnalKbmEntity> jurnalList;
    private final List<KelasEntity> kelasList;

    public JurnalUiState() {
        this(null, null, null, 7, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static /* synthetic */ JurnalUiState copy$default(JurnalUiState jurnalUiState, List list, List list2, String str, int i, Object obj) {
        if ((i & 1) != 0) {
            list = jurnalUiState.jurnalList;
        }
        if ((i & 2) != 0) {
            list2 = jurnalUiState.kelasList;
        }
        if ((i & 4) != 0) {
            str = jurnalUiState.filterKelasId;
        }
        return jurnalUiState.copy(list, list2, str);
    }

    public final List<JurnalKbmEntity> component1() {
        return this.jurnalList;
    }

    public final List<KelasEntity> component2() {
        return this.kelasList;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getFilterKelasId() {
        return this.filterKelasId;
    }

    public final JurnalUiState copy(List<JurnalKbmEntity> jurnalList, List<KelasEntity> kelasList, String filterKelasId) {
        Intrinsics.checkNotNullParameter(jurnalList, "jurnalList");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        return new JurnalUiState(jurnalList, kelasList, filterKelasId);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof JurnalUiState)) {
            return false;
        }
        JurnalUiState jurnalUiState = (JurnalUiState) other;
        return Intrinsics.areEqual(this.jurnalList, jurnalUiState.jurnalList) && Intrinsics.areEqual(this.kelasList, jurnalUiState.kelasList) && Intrinsics.areEqual(this.filterKelasId, jurnalUiState.filterKelasId);
    }

    public int hashCode() {
        int iHashCode = ((this.jurnalList.hashCode() * 31) + this.kelasList.hashCode()) * 31;
        String str = this.filterKelasId;
        return iHashCode + (str == null ? 0 : str.hashCode());
    }

    public String toString() {
        return "JurnalUiState(jurnalList=" + this.jurnalList + ", kelasList=" + this.kelasList + ", filterKelasId=" + this.filterKelasId + ")";
    }

    public JurnalUiState(List<JurnalKbmEntity> jurnalList, List<KelasEntity> kelasList, String str) {
        Intrinsics.checkNotNullParameter(jurnalList, "jurnalList");
        Intrinsics.checkNotNullParameter(kelasList, "kelasList");
        this.jurnalList = jurnalList;
        this.kelasList = kelasList;
        this.filterKelasId = str;
    }

    public /* synthetic */ JurnalUiState(List list, List list2, String str, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? CollectionsKt.emptyList() : list, (i & 2) != 0 ? CollectionsKt.emptyList() : list2, (i & 4) != 0 ? null : str);
    }

    public final List<JurnalKbmEntity> getJurnalList() {
        return this.jurnalList;
    }

    public final List<KelasEntity> getKelasList() {
        return this.kelasList;
    }

    public final String getFilterKelasId() {
        return this.filterKelasId;
    }
}
