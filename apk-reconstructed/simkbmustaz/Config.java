package com.rork.simkbmustaz;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.Map;
import kotlin.Metadata;
import kotlin.TuplesKt;
import kotlin.collections.MapsKt;

/* JADX INFO: compiled from: Config.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0007\n\u0002\u0010$\n\u0002\b\u0003\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003R\u000e\u0010\u0004\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0005X\u0086T¢\u0006\u0002\n\u0000R\u001d\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u0005\u0012\u0004\u0012\u00020\u00050\r¢\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000f¨\u0006\u0010"}, d2 = {"Lcom/rork/simkbmustaz/Config;", "", "<init>", "()V", "EXPO_PUBLIC_PROJECT_ID", "", "EXPO_PUBLIC_RORK_API_BASE_URL", "EXPO_PUBLIC_RORK_AUTH_URL", "EXPO_PUBLIC_RORK_FUNCTIONS_URL", "EXPO_PUBLIC_RORK_TOOLKIT_SECRET_KEY", "EXPO_PUBLIC_TEAM_ID", "EXPO_PUBLIC_TOOLKIT_URL", "allValues", "", "getAllValues", "()Ljava/util/Map;", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final class Config {
    public static final Config INSTANCE = new Config();
    public static final String EXPO_PUBLIC_PROJECT_ID = "edpqdjo95q5o7a6p50t36";
    public static final String EXPO_PUBLIC_RORK_API_BASE_URL = "https://dev-edpqdjo95q5o7a6p50t36.rorktest.dev";
    public static final String EXPO_PUBLIC_RORK_AUTH_URL = "https://api.rork.com";
    public static final String EXPO_PUBLIC_RORK_FUNCTIONS_URL = "https://sim-kbm-ustaz-backend.rork.app";
    public static final String EXPO_PUBLIC_RORK_TOOLKIT_SECRET_KEY = "rork_sk_v3tzd5ur834kwye2p3pii28mgw4of8iq";
    public static final String EXPO_PUBLIC_TEAM_ID = "a760808b-031f-4303-a9a2-0315a579df7e";
    public static final String EXPO_PUBLIC_TOOLKIT_URL = "https://toolkit.rork.com";
    private static final Map<String, String> allValues = MapsKt.mapOf(TuplesKt.to("EXPO_PUBLIC_PROJECT_ID", EXPO_PUBLIC_PROJECT_ID), TuplesKt.to("EXPO_PUBLIC_RORK_API_BASE_URL", EXPO_PUBLIC_RORK_API_BASE_URL), TuplesKt.to("EXPO_PUBLIC_RORK_AUTH_URL", EXPO_PUBLIC_RORK_AUTH_URL), TuplesKt.to("EXPO_PUBLIC_RORK_FUNCTIONS_URL", EXPO_PUBLIC_RORK_FUNCTIONS_URL), TuplesKt.to("EXPO_PUBLIC_RORK_TOOLKIT_SECRET_KEY", EXPO_PUBLIC_RORK_TOOLKIT_SECRET_KEY), TuplesKt.to("EXPO_PUBLIC_TEAM_ID", EXPO_PUBLIC_TEAM_ID), TuplesKt.to("EXPO_PUBLIC_TOOLKIT_URL", EXPO_PUBLIC_TOOLKIT_URL));
    public static final int $stable = 8;

    private Config() {
    }

    public final Map<String, String> getAllValues() {
        return allValues;
    }
}
