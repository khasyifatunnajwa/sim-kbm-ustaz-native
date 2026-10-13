package com.rork.simkbmustaz.ui.menu;

import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.tooling.preview.AndroidUiModes;
import io.ktor.http.LinkHeader;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: MenuData.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0018\n\u0002\u0010\b\n\u0002\b\u0002\b\u0087\b\u0018\u00002\u00020\u0001BA\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u0012\u0006\u0010\b\u001a\u00020\t\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\b\b\u0002\u0010\u000b\u001a\u00020\f¢\u0006\u0004\b\r\u0010\u000eJ\t\u0010\u001a\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001b\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001c\u001a\u00020\u0003HÆ\u0003J\t\u0010\u001d\u001a\u00020\u0007HÆ\u0003J\t\u0010\u001e\u001a\u00020\tHÆ\u0003J\t\u0010\u001f\u001a\u00020\u0003HÆ\u0003J\t\u0010 \u001a\u00020\fHÆ\u0003JO\u0010!\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\b\b\u0002\u0010\u0004\u001a\u00020\u00032\b\b\u0002\u0010\u0005\u001a\u00020\u00032\b\b\u0002\u0010\u0006\u001a\u00020\u00072\b\b\u0002\u0010\b\u001a\u00020\t2\b\b\u0002\u0010\n\u001a\u00020\u00032\b\b\u0002\u0010\u000b\u001a\u00020\fHÆ\u0001J\u0014\u0010\"\u001a\u00020\f2\b\u0010#\u001a\u0004\u0018\u00010\u0001HÖ\u0083\u0004J\n\u0010$\u001a\u00020%HÖ\u0081\u0004J\n\u0010&\u001a\u00020\u0003HÖ\u0081\u0004R\u0011\u0010\u0002\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0010R\u0011\u0010\u0004\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0011\u0010\u0010R\u0011\u0010\u0005\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0010R\u0011\u0010\u0006\u001a\u00020\u0007¢\u0006\b\n\u0000\u001a\u0004\b\u0013\u0010\u0014R\u0011\u0010\b\u001a\u00020\t¢\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0011\u0010\n\u001a\u00020\u0003¢\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0010R\u0011\u0010\u000b\u001a\u00020\f¢\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019¨\u0006'"}, d2 = {"Lcom/rork/simkbmustaz/ui/menu/MenuItem;", "", "route", "", LinkHeader.Parameters.Title, "subtitle", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "group", "Lcom/rork/simkbmustaz/ui/menu/MenuGroup;", "plannedStage", "adminOnly", "", "<init>", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Landroidx/compose/ui/graphics/vector/ImageVector;Lcom/rork/simkbmustaz/ui/menu/MenuGroup;Ljava/lang/String;Z)V", "getRoute", "()Ljava/lang/String;", "getTitle", "getSubtitle", "getIcon", "()Landroidx/compose/ui/graphics/vector/ImageVector;", "getGroup", "()Lcom/rork/simkbmustaz/ui/menu/MenuGroup;", "getPlannedStage", "getAdminOnly", "()Z", "component1", "component2", "component3", "component4", "component5", "component6", "component7", "copy", "equals", "other", "hashCode", "", "toString", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final /* data */ class MenuItem {
    public static final int $stable = 0;
    private final boolean adminOnly;
    private final MenuGroup group;
    private final ImageVector icon;
    private final String plannedStage;
    private final String route;
    private final String subtitle;
    private final String title;

    public static /* synthetic */ MenuItem copy$default(MenuItem menuItem, String str, String str2, String str3, ImageVector imageVector, MenuGroup menuGroup, String str4, boolean z, int i, Object obj) {
        if ((i & 1) != 0) {
            str = menuItem.route;
        }
        if ((i & 2) != 0) {
            str2 = menuItem.title;
        }
        if ((i & 4) != 0) {
            str3 = menuItem.subtitle;
        }
        if ((i & 8) != 0) {
            imageVector = menuItem.icon;
        }
        if ((i & 16) != 0) {
            menuGroup = menuItem.group;
        }
        if ((i & 32) != 0) {
            str4 = menuItem.plannedStage;
        }
        if ((i & 64) != 0) {
            z = menuItem.adminOnly;
        }
        String str5 = str4;
        boolean z2 = z;
        MenuGroup menuGroup2 = menuGroup;
        String str6 = str3;
        return menuItem.copy(str, str2, str6, imageVector, menuGroup2, str5, z2);
    }

    /* JADX INFO: renamed from: component1, reason: from getter */
    public final String getRoute() {
        return this.route;
    }

    /* JADX INFO: renamed from: component2, reason: from getter */
    public final String getTitle() {
        return this.title;
    }

    /* JADX INFO: renamed from: component3, reason: from getter */
    public final String getSubtitle() {
        return this.subtitle;
    }

    /* JADX INFO: renamed from: component4, reason: from getter */
    public final ImageVector getIcon() {
        return this.icon;
    }

    /* JADX INFO: renamed from: component5, reason: from getter */
    public final MenuGroup getGroup() {
        return this.group;
    }

    /* JADX INFO: renamed from: component6, reason: from getter */
    public final String getPlannedStage() {
        return this.plannedStage;
    }

    /* JADX INFO: renamed from: component7, reason: from getter */
    public final boolean getAdminOnly() {
        return this.adminOnly;
    }

    public final MenuItem copy(String route, String title, String subtitle, ImageVector icon, MenuGroup group, String plannedStage, boolean adminOnly) {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(subtitle, "subtitle");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(plannedStage, "plannedStage");
        return new MenuItem(route, title, subtitle, icon, group, plannedStage, adminOnly);
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof MenuItem)) {
            return false;
        }
        MenuItem menuItem = (MenuItem) other;
        return Intrinsics.areEqual(this.route, menuItem.route) && Intrinsics.areEqual(this.title, menuItem.title) && Intrinsics.areEqual(this.subtitle, menuItem.subtitle) && Intrinsics.areEqual(this.icon, menuItem.icon) && this.group == menuItem.group && Intrinsics.areEqual(this.plannedStage, menuItem.plannedStage) && this.adminOnly == menuItem.adminOnly;
    }

    public int hashCode() {
        return (((((((((((this.route.hashCode() * 31) + this.title.hashCode()) * 31) + this.subtitle.hashCode()) * 31) + this.icon.hashCode()) * 31) + this.group.hashCode()) * 31) + this.plannedStage.hashCode()) * 31) + Boolean.hashCode(this.adminOnly);
    }

    public String toString() {
        return "MenuItem(route=" + this.route + ", title=" + this.title + ", subtitle=" + this.subtitle + ", icon=" + this.icon + ", group=" + this.group + ", plannedStage=" + this.plannedStage + ", adminOnly=" + this.adminOnly + ")";
    }

    public MenuItem(String route, String title, String subtitle, ImageVector icon, MenuGroup group, String plannedStage, boolean z) {
        Intrinsics.checkNotNullParameter(route, "route");
        Intrinsics.checkNotNullParameter(title, "title");
        Intrinsics.checkNotNullParameter(subtitle, "subtitle");
        Intrinsics.checkNotNullParameter(icon, "icon");
        Intrinsics.checkNotNullParameter(group, "group");
        Intrinsics.checkNotNullParameter(plannedStage, "plannedStage");
        this.route = route;
        this.title = title;
        this.subtitle = subtitle;
        this.icon = icon;
        this.group = group;
        this.plannedStage = plannedStage;
        this.adminOnly = z;
    }

    public /* synthetic */ MenuItem(String str, String str2, String str3, ImageVector imageVector, MenuGroup menuGroup, String str4, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, str3, imageVector, menuGroup, str4, (i & 64) != 0 ? false : z);
    }

    public final String getRoute() {
        return this.route;
    }

    public final String getTitle() {
        return this.title;
    }

    public final String getSubtitle() {
        return this.subtitle;
    }

    public final ImageVector getIcon() {
        return this.icon;
    }

    public final MenuGroup getGroup() {
        return this.group;
    }

    public final String getPlannedStage() {
        return this.plannedStage;
    }

    public final boolean getAdminOnly() {
        return this.adminOnly;
    }
}
