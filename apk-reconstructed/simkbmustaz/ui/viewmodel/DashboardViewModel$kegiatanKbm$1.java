package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.AbsensiEntity;
import com.rork.simkbmustaz.data.local.AgendaEntity;
import com.rork.simkbmustaz.data.local.JadwalEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Triple;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: DashboardViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a&\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00020\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00022\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00050\u0002H\n"}, d2 = {"<anonymous>", "Lkotlin/Triple;", "", "Lcom/rork/simkbmustaz/data/local/JadwalEntity;", "Lcom/rork/simkbmustaz/data/local/AbsensiEntity;", "Lcom/rork/simkbmustaz/data/local/AgendaEntity;", "jadwal", "absensiHariIni", "agenda"}, k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
@DebugMetadata(c = "com.rork.simkbmustaz.ui.viewmodel.DashboardViewModel$kegiatanKbm$1", f = "DashboardViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, nl = {}, s = {}, v = 2)
final class DashboardViewModel$kegiatanKbm$1 extends SuspendLambda implements Function4<List<? extends JadwalEntity>, List<? extends AbsensiEntity>, List<? extends AgendaEntity>, Continuation<? super Triple<? extends List<? extends JadwalEntity>, ? extends List<? extends AbsensiEntity>, ? extends List<? extends AgendaEntity>>>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    DashboardViewModel$kegiatanKbm$1(Continuation<? super DashboardViewModel$kegiatanKbm$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(List<? extends JadwalEntity> list, List<? extends AbsensiEntity> list2, List<? extends AgendaEntity> list3, Continuation<? super Triple<? extends List<? extends JadwalEntity>, ? extends List<? extends AbsensiEntity>, ? extends List<? extends AgendaEntity>>> continuation) {
        return invoke2((List<JadwalEntity>) list, (List<AbsensiEntity>) list2, (List<AgendaEntity>) list3, (Continuation<? super Triple<? extends List<JadwalEntity>, ? extends List<AbsensiEntity>, ? extends List<AgendaEntity>>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(List<JadwalEntity> list, List<AbsensiEntity> list2, List<AgendaEntity> list3, Continuation<? super Triple<? extends List<JadwalEntity>, ? extends List<AbsensiEntity>, ? extends List<AgendaEntity>>> continuation) {
        DashboardViewModel$kegiatanKbm$1 dashboardViewModel$kegiatanKbm$1 = new DashboardViewModel$kegiatanKbm$1(continuation);
        dashboardViewModel$kegiatanKbm$1.L$0 = list;
        dashboardViewModel$kegiatanKbm$1.L$1 = list2;
        dashboardViewModel$kegiatanKbm$1.L$2 = list3;
        return dashboardViewModel$kegiatanKbm$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        List list3 = (List) this.L$2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Triple(list, list2, list3);
    }
}
