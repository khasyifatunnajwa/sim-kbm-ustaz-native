package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.JurnalKbmEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import java.util.ArrayList;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.internal.Intrinsics;

/* JADX INFO: compiled from: JurnalViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\b\u0010\u0007\u001a\u0004\u0018\u00010\bH\n"}, d2 = {"<anonymous>", "Lcom/rork/simkbmustaz/ui/viewmodel/JurnalUiState;", "jurnal", "", "Lcom/rork/simkbmustaz/data/local/JurnalKbmEntity;", "kelas", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "filterKelasId", ""}, k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
@DebugMetadata(c = "com.rork.simkbmustaz.ui.viewmodel.JurnalViewModel$uiState$1", f = "JurnalViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, nl = {}, s = {}, v = 2)
final class JurnalViewModel$uiState$1 extends SuspendLambda implements Function4<List<? extends JurnalKbmEntity>, List<? extends KelasEntity>, String, Continuation<? super JurnalUiState>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    JurnalViewModel$uiState$1(Continuation<? super JurnalViewModel$uiState$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(List<? extends JurnalKbmEntity> list, List<? extends KelasEntity> list2, String str, Continuation<? super JurnalUiState> continuation) {
        return invoke2((List<JurnalKbmEntity>) list, (List<KelasEntity>) list2, str, continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(List<JurnalKbmEntity> list, List<KelasEntity> list2, String str, Continuation<? super JurnalUiState> continuation) {
        JurnalViewModel$uiState$1 jurnalViewModel$uiState$1 = new JurnalViewModel$uiState$1(continuation);
        jurnalViewModel$uiState$1.L$0 = list;
        jurnalViewModel$uiState$1.L$1 = list2;
        jurnalViewModel$uiState$1.L$2 = str;
        return jurnalViewModel$uiState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        String str = (String) this.L$2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        ArrayList arrayList = new ArrayList();
        for (Object obj2 : list) {
            JurnalKbmEntity jurnalKbmEntity = (JurnalKbmEntity) obj2;
            if (str == null || Intrinsics.areEqual(jurnalKbmEntity.getKelasId(), str)) {
                arrayList.add(obj2);
            }
        }
        return new JurnalUiState(arrayList, list2, str);
    }
}
