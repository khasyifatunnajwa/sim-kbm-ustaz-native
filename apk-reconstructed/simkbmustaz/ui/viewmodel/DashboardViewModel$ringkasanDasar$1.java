package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.KelasEntity;
import com.rork.simkbmustaz.data.local.SantriEntity;
import com.rork.simkbmustaz.data.local.TahunAjaranEntity;
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
@Metadata(d1 = {"\u0000\u001a\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0010\u0000\u001a\"\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u0002\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00040\u0002\u0012\u0006\u0012\u0004\u0018\u00010\u00050\u00012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u00022\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00040\u00022\b\u0010\b\u001a\u0004\u0018\u00010\u0005H\n"}, d2 = {"<anonymous>", "Lkotlin/Triple;", "", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "Lcom/rork/simkbmustaz/data/local/SantriEntity;", "Lcom/rork/simkbmustaz/data/local/TahunAjaranEntity;", "kelas", "santri", "periode"}, k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
@DebugMetadata(c = "com.rork.simkbmustaz.ui.viewmodel.DashboardViewModel$ringkasanDasar$1", f = "DashboardViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, nl = {}, s = {}, v = 2)
final class DashboardViewModel$ringkasanDasar$1 extends SuspendLambda implements Function4<List<? extends KelasEntity>, List<? extends SantriEntity>, TahunAjaranEntity, Continuation<? super Triple<? extends List<? extends KelasEntity>, ? extends List<? extends SantriEntity>, ? extends TahunAjaranEntity>>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    DashboardViewModel$ringkasanDasar$1(Continuation<? super DashboardViewModel$ringkasanDasar$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(List<? extends KelasEntity> list, List<? extends SantriEntity> list2, TahunAjaranEntity tahunAjaranEntity, Continuation<? super Triple<? extends List<? extends KelasEntity>, ? extends List<? extends SantriEntity>, ? extends TahunAjaranEntity>> continuation) {
        return invoke2((List<KelasEntity>) list, (List<SantriEntity>) list2, tahunAjaranEntity, (Continuation<? super Triple<? extends List<KelasEntity>, ? extends List<SantriEntity>, TahunAjaranEntity>>) continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(List<KelasEntity> list, List<SantriEntity> list2, TahunAjaranEntity tahunAjaranEntity, Continuation<? super Triple<? extends List<KelasEntity>, ? extends List<SantriEntity>, TahunAjaranEntity>> continuation) {
        DashboardViewModel$ringkasanDasar$1 dashboardViewModel$ringkasanDasar$1 = new DashboardViewModel$ringkasanDasar$1(continuation);
        dashboardViewModel$ringkasanDasar$1.L$0 = list;
        dashboardViewModel$ringkasanDasar$1.L$1 = list2;
        dashboardViewModel$ringkasanDasar$1.L$2 = tahunAjaranEntity;
        return dashboardViewModel$ringkasanDasar$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        TahunAjaranEntity tahunAjaranEntity = (TahunAjaranEntity) this.L$2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new Triple(list, list2, tahunAjaranEntity);
    }
}
