package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.core.app.NotificationCompat;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: SantriViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\r\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B)\u0012\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0003\u0012\b\b\u0002\u0010\u0005\u001a\u00020\u0003¢\u0006\u0004\b\u0006\u0010\u0007J\u000b\u0010\f\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\u000b\u0010\r\u001a\u0004\u0018\u00010\u0003HÆ\u0003J\t\u0010\u000e\u001a\u00020\u0003HÆ\u0003J+\u0010\u000f\u001a\u00020\u00002\n\b\u0002\u0010\u0002\u001a\u0004\u0018\u00010\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u0003HÆ\u0001J\u0014\u0010\u0010\u001a\u00020\u00112\b\u0010\u0012\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u0013\u001a\u00020\u0014HÖ\u0081\u0004J\n\u0010\u0015\u001a\u00020\u0003HÖ\u0081\u0004R\u0013\u0010\u0002\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0013\u0010\u0004\u001a\u0004\u0018\u00010\u0003¢\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\t¨\u0006\u0016"}, d2 = {"Lcom/rork/simkbmustaz/ui/viewmodel/FilterSantri;", "", "kelasId", "", NotificationCompat.CATEGORY_STATUS, "kataKunci", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V", "getKelasId", "()Ljava/lang/String;", "getStatus", "getKataKunci", "component1", "component2", "component3", "copy", "equals", "", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class FilterSantri {
    public static final int $stable = 0;
    private final String kataKunci;
    private final String kelasId;
    private final String status;

    public FilterSantri() {
        this(null, null, null, 7, null);
    }

    public static /* synthetic */ FilterSantri copy$default(FilterSantri filterSantri, String str, String str2, String str3, int i, Object obj) {
        if ((i & 1) != 0) {
            str = filterSantri.kelasId;
        }
        if ((i & 2) != 0) {
            str2 = filterSantri.status;
        }
        if ((i & 4) != 0) {
            str3 = filterSantri.kataKunci;
        }
        return filterSantri.copy(str, str2, str3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getKelasId() {
        return this.kelasId;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getStatus() {
        return this.status;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getKataKunci() {
        return this.kataKunci;
    }

    public final FilterSantri copy(String kelasId, String status, String kataKunci) {
        Intrinsics.checkNotNullParameter(kataKunci, "kataKunci");
        return new FilterSantri(kelasId, status, kataKunci);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof FilterSantri)) {
            return false;
        }
        FilterSantri filterSantri = (FilterSantri) other;
        return Intrinsics.areEqual(this.kelasId, filterSantri.kelasId) && Intrinsics.areEqual(this.status, filterSantri.status) && Intrinsics.areEqual(this.kataKunci, filterSantri.kataKunci);
    }

    public int hashCode() {
        String str = this.kelasId;
        int iHashCode = (str == null ? 0 : str.hashCode()) * 31;
        String str2 = this.status;
        return ((iHashCode + (str2 != null ? str2.hashCode() : 0)) * 31) + this.kataKunci.hashCode();
    }

    public String toString() {
        return "FilterSantri(kelasId=" + this.kelasId + ", status=" + this.status + ", kataKunci=" + this.kataKunci + ")";
    }

    public FilterSantri(String str, String str2, String kataKunci) {
        Intrinsics.checkNotNullParameter(kataKunci, "kataKunci");
        this.kelasId = str;
        this.status = str2;
        this.kataKunci = kataKunci;
    }

    public final String getKelasId() {
        return this.kelasId;
    }

    public final String getStatus() {
        return this.status;
    }

    public /* synthetic */ FilterSantri(String str, String str2, String str3, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? null : str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? "" : str3);
    }

    public final String getKataKunci() {
        return this.kataKunci;
    }
}
