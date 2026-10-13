package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import androidx.exifinterface.media.ExifInterface;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u000b\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0019\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003¢\u0006\u0004\b\u0005\u0010\u0006R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\bR\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\t\u0010\bj\u0002\b\nj\u0002\b\u000bj\u0002\b\fj\u0002\b\r¨\u0006\u000e"}, d2 = {"Lcom/rork/simkbmustaz/data/local/StatusKehadiran;", "", "huruf", "", "label", "<init>", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;)V", "getHuruf", "()Ljava/lang/String;", "getLabel", "HADIR", "SAKIT", "IZIN", "ALPA", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public enum StatusKehadiran {
    HADIR("H", "Hadir"),
    SAKIT(ExifInterface.LATITUDE_SOUTH, "Sakit"),
    IZIN("I", "Izin"),
    ALPA(ExifInterface.GPS_MEASUREMENT_IN_PROGRESS, "Alpa");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String huruf;
    private final String label;

    public static EnumEntries<StatusKehadiran> getEntries() {
        return $ENTRIES;
    }

    StatusKehadiran(String str, String str2) {
        this.huruf = str;
        this.label = str2;
    }

    public final String getHuruf() {
        return this.huruf;
    }

    public final String getLabel() {
        return this.label;
    }
}
