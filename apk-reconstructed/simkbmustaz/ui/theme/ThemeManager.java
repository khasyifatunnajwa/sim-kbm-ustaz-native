package com.rork.simkbmustaz.ui.theme;

import android.content.Context;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.SnapshotStateKt__SnapshotStateKt;
import androidx.compose.ui.tooling.preview.AndroidUiModes;
import kotlin.Metadata;
import kotlin.Result;
import kotlin.ResultKt;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Theme.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u000e\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013J\u0016\u0010\f\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u00132\u0006\u0010\u0014\u001a\u00020\bR\u000e\u0010\u0004\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0082T¢\u0006\u0002\n\u0000R+\u0010\t\u001a\u00020\b2\u0006\u0010\u0007\u001a\u00020\b8F@BX\u0086\u008e\u0002¢\u0006\u0012\n\u0004\b\u000e\u0010\u000f\u001a\u0004\b\n\u0010\u000b\"\u0004\b\f\u0010\r¨\u0006\u0015"}, d2 = {"Lcom/rork/simkbmustaz/ui/theme/ThemeManager;", "", "<init>", "()V", "PREFS", "", "KEY_MODE", "<set-?>", "Lcom/rork/simkbmustaz/ui/theme/ThemeMode;", "mode", "getMode", "()Lcom/rork/simkbmustaz/ui/theme/ThemeMode;", "setMode", "(Lcom/rork/simkbmustaz/ui/theme/ThemeMode;)V", "mode$delegate", "Landroidx/compose/runtime/MutableState;", "load", "", "context", "Landroid/content/Context;", "newMode", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final class ThemeManager {
    public static final int $stable = 0;
    private static final String KEY_MODE = "theme_mode";
    private static final String PREFS = "sim_kbm_prefs";
    public static final ThemeManager INSTANCE = new ThemeManager();

    /* JADX INFO: renamed from: mode$delegate, reason: from kotlin metadata */
    private static final MutableState mode = SnapshotStateKt__SnapshotStateKt.mutableStateOf$default(ThemeMode.SYSTEM, null, 2, null);

    private ThemeManager() {
    }

    private final void setMode(ThemeMode themeMode) {
        mode.setValue(themeMode);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ThemeMode getMode() {
        return (ThemeMode) mode.getValue();
    }

    /* JADX WARN: Code duplicated, block: B:14:0x0037  */
    public final void load(Context context) {
        Object objM9347constructorimpl;
        ThemeMode themeMode;
        Intrinsics.checkNotNullParameter(context, "context");
        String string = context.getSharedPreferences(PREFS, 0).getString(KEY_MODE, null);
        if (string != null) {
            try {
                Result.Companion companion = Result.INSTANCE;
                objM9347constructorimpl = Result.m9347constructorimpl(ThemeMode.valueOf(string));
            } catch (Throwable th) {
                Result.Companion companion2 = Result.INSTANCE;
                objM9347constructorimpl = Result.m9347constructorimpl(ResultKt.createFailure(th));
            }
            themeMode = (ThemeMode) (Result.m9353isFailureimpl(objM9347constructorimpl) ? null : objM9347constructorimpl);
            if (themeMode == null) {
                themeMode = ThemeMode.SYSTEM;
            }
        } else {
            themeMode = ThemeMode.SYSTEM;
        }
        setMode(themeMode);
    }

    public final void setMode(Context context, ThemeMode newMode) {
        Intrinsics.checkNotNullParameter(context, "context");
        Intrinsics.checkNotNullParameter(newMode, "newMode");
        setMode(newMode);
        context.getSharedPreferences(PREFS, 0).edit().putString(KEY_MODE, newMode.name()).apply();
    }
}
