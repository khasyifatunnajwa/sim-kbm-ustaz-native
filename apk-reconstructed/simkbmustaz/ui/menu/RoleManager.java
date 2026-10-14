package com.rork.simkbmustaz.ui.menu;

import android.content.Context;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.ui.tooling.preview.AndroidUiModes;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: RoleManager.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\f\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R+\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8F@BX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lcom/rork/simkbmustaz/ui/menu/RoleManager;", "", "<init>", "()V", "PREFS", "", "KEY_ROLE", "<set-?>", "Lcom/rork/simkbmustaz/ui/menu/UserRole;", "role", "getRole", "()Lcom/rork/simkbmustaz/ui/menu/UserRole;", "setRole", "(Lcom/rork/simkbmustaz/ui/menu/UserRole;)V", "role$delegate", "Landroidx/compose/runtime/MutableState;", "load", "", "context", "Landroid/content/Context;", "newRole", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final class RoleManager {
    public static final int $stable = 0;
    private static final String KEY_ROLE = "user_role";
    private static final String PREFS = "sim_kbm_prefs";
    public static final RoleManager INSTANCE = new RoleManager();

    /* JADX INFO: renamed from: role$delegate, reason: from kotlin metadata */
    private static final MutableState role = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(UserRole.ADMIN, null, 2, null);

    private RoleManager() {
    }

    private final void setRole(UserRole userRole) {
        role.setValue(userRole);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final UserRole getRole() {
        return (UserRole) role.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0037  */
    public final void load(Context context) {
        Object objM9347constructorimpl;
        UserRole userRole;
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getSharedPreferences(PREFS, 0).getString(KEY_ROLE, null);
        if (string != null) {
            try {
                Result.Companion companion = Result.INSTANCE;
                objM9347constructorimpl = Result.m9347constructorimpl(UserRole.valueOf(string));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM9347constructorimpl = Result.m9347constructorimpl(ResultKt.createFailure(th));
            }
            userRole = (UserRole) (Result.m9353isFailureimpl(objM9347constructorimpl) ? null : objM9347constructorimpl);
            if (userRole == null) {
                userRole = UserRole.ADMIN;
            }
        } else {
            userRole = UserRole.ADMIN;
        }
        setRole(userRole);
    }

    public final void setRole(Context context, UserRole newRole) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(newRole, "newRole");
        setRole(newRole);
        context.getSharedPreferences(PREFS, 0).edit().putString(KEY_ROLE, newRole.name()).apply();
    }
}
