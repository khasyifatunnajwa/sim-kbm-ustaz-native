package com.rork.simkbmustaz.ui.menu;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import io.ktor.http.LinkHeader;
import kotlin.Metadata;
import kotlin.enums.EnumEntries;
import kotlin.enums.EnumEntriesKt;

/* JADX INFO: compiled from: MenuData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0000\n\u0002\u0010\u000e\n\u0002\b\t\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0011\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003¢\u0006\u0004\b\u0004\u0010\u0005R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007j\u0002\b\bj\u0002\b\tj\u0002\b\nj\u0002\b\u000b¨\u0006\f"}, d2 = {"Lcom/rork/simkbmustaz/ui/menu/MenuGroup;", "", LinkHeader.Parameters.Title, "", "<init>", "(Ljava/lang/String;ILjava/lang/String;)V", "getTitle", "()Ljava/lang/String;", "UTAMA", "KBM", "PERANGKAT", "ADMIN", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public enum MenuGroup {
    UTAMA("Menu Utama"),
    KBM("Kegiatan KBM"),
    PERANGKAT("Perangkat Pembelajaran & Informasi"),
    ADMIN("Administrasi & Akun");

    private static final /* synthetic */ EnumEntries $ENTRIES = EnumEntriesKt.enumEntries(values());
    private final String title;

    public static EnumEntries<MenuGroup> getEntries() {
        return $ENTRIES;
    }

    MenuGroup(String str) {
        this.title = str;
    }

    public final String getTitle() {
        return this.title;
    }
}
