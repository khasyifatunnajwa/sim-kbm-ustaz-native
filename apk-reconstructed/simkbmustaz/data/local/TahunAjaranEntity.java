package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.UUID;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: DataEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\u0015\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001B?\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0007\u0012\b\b\u0002\u0010\b\u001a\u00020\t\u0012\b\b\u0002\u0010\n\u001a\u00020\t¢\u0006\u0004\b\u000b\u0010\fJ\t\u0010\u0015\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0016\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0017\u001a\u00020\u0003HÆ\u0003J\t\u0010\u0018\u001a\u00020\u0007HÆ\u0003J\t\u0010\u0019\u001a\u00020\tHÆ\u0003J\t\u0010\u001a\u001a\u00020\tHÆ\u0003JE\u0010\u001b\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\tHÆ\u0001J\u0014\u0010\u001c\u001a\u00020\u00072\b\u0010\u001d\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010\u001e\u001a\u00020\u001fHÖ\u0081\u0004J\n\u0010 \u001a\u00020\u0003HÖ\u0081\u0004R\u0016\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004¢\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000eR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u000eR\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u000eR\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0011R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u0011\u0010\n\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0013¨\u0006!"}, d2 = {"Lcom/rork/simkbmustaz/data/local/TahunAjaranEntity;", "", "id", "", "tahun", "semester", "isActive", "", "createdAt", "", "updatedAt", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZJJ)V", "getId", "()Ljava/lang/String;", "getTahun", "getSemester", "()Z", "getCreatedAt", "()J", "getUpdatedAt", "component1", "component2", "component3", "component4", "component5", "component6", "copy", "equals", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class TahunAjaranEntity {
    public static final int $stable = 0;
    private final long createdAt;
    private final String id;
    private final boolean isActive;
    private final String semester;
    private final String tahun;
    private final long updatedAt;

    public static /* synthetic */ TahunAjaranEntity copy$default(TahunAjaranEntity tahunAjaranEntity, String str, String str2, String str3, boolean z, long j, long j2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = tahunAjaranEntity.id;
        }
        if ((i & 2) != 0) {
            str2 = tahunAjaranEntity.tahun;
        }
        if ((i & 4) != 0) {
            str3 = tahunAjaranEntity.semester;
        }
        if ((i & 8) != 0) {
            z = tahunAjaranEntity.isActive;
        }
        if ((i & 16) != 0) {
            j = tahunAjaranEntity.createdAt;
        }
        if ((i & 32) != 0) {
            j2 = tahunAjaranEntity.updatedAt;
        }
        long j3 = j2;
        long j4 = j;
        return tahunAjaranEntity.copy(str, str2, str3, z, j4, j3);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getId() {
        return this.id;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getTahun() {
        return this.tahun;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getSemester() {
        return this.semester;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final boolean getIsActive() {
        return this.isActive;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final long getCreatedAt() {
        return this.createdAt;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final long getUpdatedAt() {
        return this.updatedAt;
    }

    public final TahunAjaranEntity copy(String id, String tahun, String semester, boolean isActive, long createdAt, long updatedAt) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(tahun, "tahun");
        Intrinsics.checkNotNullParameter(semester, "semester");
        return new TahunAjaranEntity(id, tahun, semester, isActive, createdAt, updatedAt);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TahunAjaranEntity)) {
            return false;
        }
        TahunAjaranEntity tahunAjaranEntity = (TahunAjaranEntity) other;
        return Intrinsics.areEqual(this.id, tahunAjaranEntity.id) && Intrinsics.areEqual(this.tahun, tahunAjaranEntity.tahun) && Intrinsics.areEqual(this.semester, tahunAjaranEntity.semester) && this.isActive == tahunAjaranEntity.isActive && this.createdAt == tahunAjaranEntity.createdAt && this.updatedAt == tahunAjaranEntity.updatedAt;
    }

    public int hashCode() {
        return (((((((((this.id.hashCode() * 31) + this.tahun.hashCode()) * 31) + this.semester.hashCode()) * 31) + Boolean.hashCode(this.isActive)) * 31) + Long.hashCode(this.createdAt)) * 31) + Long.hashCode(this.updatedAt);
    }

    public String toString() {
        return "TahunAjaranEntity(id=" + this.id + ", tahun=" + this.tahun + ", semester=" + this.semester + ", isActive=" + this.isActive + ", createdAt=" + this.createdAt + ", updatedAt=" + this.updatedAt + ")";
    }

    public TahunAjaranEntity(String id, String tahun, String semester, boolean z, long j, long j2) {
        Intrinsics.checkNotNullParameter(id, "id");
        Intrinsics.checkNotNullParameter(tahun, "tahun");
        Intrinsics.checkNotNullParameter(semester, "semester");
        this.id = id;
        this.tahun = tahun;
        this.semester = semester;
        this.isActive = z;
        this.createdAt = j;
        this.updatedAt = j2;
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ TahunAjaranEntity(String str, String str2, String str3, boolean z, long j, long j2, int i, DefaultConstructorMarker defaultConstructorMarker) {
        if ((i & 1) != 0) {
            str = UUID.randomUUID().toString();
            Intrinsics.checkNotNullExpressionValue(str, "toString(...)");
        }
        this(str, str2, str3, (i & 8) != 0 ? false : z, (i & 16) != 0 ? System.currentTimeMillis() : j, (i & 32) != 0 ? System.currentTimeMillis() : j2);
    }

    public final String getId() {
        return this.id;
    }

    public final String getTahun() {
        return this.tahun;
    }

    public final String getSemester() {
        return this.semester;
    }

    public final boolean isActive() {
        return this.isActive;
    }

    public final long getCreatedAt() {
        return this.createdAt;
    }

    public final long getUpdatedAt() {
        return this.updatedAt;
    }
}
