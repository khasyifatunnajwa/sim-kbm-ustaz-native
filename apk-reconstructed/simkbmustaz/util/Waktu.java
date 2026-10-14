package com.rork.simkbmustaz.util;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: Waktu.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000*\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0004\bÇ\u0002\u0018\u00002\u00020\u0001B\t\b\u0002¢\u0006\u0004\b\u0002\u0010\u0003J\u0006\u0010\b\u001a\u00020\tJ\u000e\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\tJ\u0006\u0010\r\u001a\u00020\u000bJ\u0006\u0010\u000e\u001a\u00020\u000bJ\u000e\u0010\u000f\u001a\u00020\t2\u0006\u0010\u0010\u001a\u00020\u000bJ\u0016\u0010\u0011\u001a\u00020\u00122\u0006\u0010\f\u001a\u00020\t2\u0006\u0010\u0013\u001a\u00020\u0012J\u0018\u0010\u0014\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u000b2\b\b\u0002\u0010\u0013\u001a\u00020\u0012J\u000e\u0010\u0015\u001a\u00020\u00122\u0006\u0010\u0010\u001a\u00020\u000bR\u0011\u0010\u0004\u001a\u00020\u0005¢\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007¨\u0006\u0016"}, d2 = {"Lcom/rork/simkbmustaz/util/Waktu;", "", "<init>", "()V", "ZONA", "Ljava/time/ZoneId;", "getZONA", "()Ljava/time/ZoneId;", "hariIni", "Ljava/time/LocalDate;", "awalHari", "", "tanggal", "awalHariIni", "awalBesok", "keLocalDate", "millis", "format", "", "pola", "formatTanggal", "formatTanggalPendek", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public final class Waktu {
    public static final int $stable;
    public static final Waktu INSTANCE = new Waktu();
    private static final ZoneId ZONA;

    private Waktu() {
    }

    static {
        ZoneId zoneIdOf = ZoneId.of("Asia/Jakarta");
        Intrinsics.checkNotNullExpressionValue(zoneIdOf, "of(...)");
        ZONA = zoneIdOf;
        $stable = 8;
    }

    public final ZoneId getZONA() {
        return ZONA;
    }

    public final LocalDate hariIni() {
        LocalDate localDateNow = LocalDate.now(ZONA);
        Intrinsics.checkNotNullExpressionValue(localDateNow, "now(...)");
        return localDateNow;
    }

    public final long awalHari(LocalDate tanggal) {
        Intrinsics.checkNotNullParameter(tanggal, "tanggal");
        return tanggal.atStartOfDay(ZONA).toInstant().toEpochMilli();
    }

    public final long awalHariIni() {
        return awalHari(hariIni());
    }

    public final long awalBesok() {
        LocalDate localDatePlusDays = hariIni().plusDays(1L);
        Intrinsics.checkNotNullExpressionValue(localDatePlusDays, "plusDays(...)");
        return awalHari(localDatePlusDays);
    }

    public final LocalDate keLocalDate(long millis) {
        LocalDate localDate = Instant.ofEpochMilli(millis).atZone(ZONA).toLocalDate();
        Intrinsics.checkNotNullExpressionValue(localDate, "toLocalDate(...)");
        return localDate;
    }

    public final String format(LocalDate tanggal, String pola) {
        Intrinsics.checkNotNullParameter(tanggal, "tanggal");
        Intrinsics.checkNotNullParameter(pola, "pola");
        String str = tanggal.format(DateTimeFormatter.ofPattern(pola, new Locale("id", "ID")));
        Intrinsics.checkNotNullExpressionValue(str, "format(...)");
        return str;
    }

    public static /* synthetic */ String formatTanggal$default(Waktu waktu, long j, String str, int i, Object obj) {
        if ((i & 2) != 0) {
            str = "EEEE, d MMMM yyyy";
        }
        return waktu.formatTanggal(j, str);
    }

    public final String formatTanggal(long millis, String pola) {
        Intrinsics.checkNotNullParameter(pola, "pola");
        return format(keLocalDate(millis), pola);
    }

    public final String formatTanggalPendek(long millis) {
        return formatTanggal(millis, "d MMM yyyy");
    }
}
