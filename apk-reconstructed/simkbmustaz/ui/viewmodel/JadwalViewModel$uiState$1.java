package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.Hari;
import com.rork.simkbmustaz.data.local.JadwalEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function4;

/* JADX INFO: compiled from: JadwalViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u001c\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u00032\u0006\u0010\u0007\u001a\u00020\bH\n"}, d2 = {"<anonymous>", "Lcom/rork/simkbmustaz/ui/viewmodel/JadwalUiState;", "jadwal", "", "Lcom/rork/simkbmustaz/data/local/JadwalEntity;", "kelas", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "hari", "Lcom/rork/simkbmustaz/data/local/Hari;"}, k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
@DebugMetadata(c = "com.rork.simkbmustaz.ui.viewmodel.JadwalViewModel$uiState$1", f = "JadwalViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, nl = {}, s = {}, v = 2)
final class JadwalViewModel$uiState$1 extends SuspendLambda implements Function4<List<? extends JadwalEntity>, List<? extends KelasEntity>, Hari, Continuation<? super JadwalUiState>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    /* synthetic */ Object L$2;
    int label;

    JadwalViewModel$uiState$1(Continuation<? super JadwalViewModel$uiState$1> continuation) {
        super(4, continuation);
    }

    @Override // kotlin.jvm.functions.Function4
    public /* bridge */ /* synthetic */ Object invoke(List<? extends JadwalEntity> list, List<? extends KelasEntity> list2, Hari hari, Continuation<? super JadwalUiState> continuation) {
        return invoke2((List<JadwalEntity>) list, (List<KelasEntity>) list2, hari, continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(List<JadwalEntity> list, List<KelasEntity> list2, Hari hari, Continuation<? super JadwalUiState> continuation) {
        JadwalViewModel$uiState$1 jadwalViewModel$uiState$1 = new JadwalViewModel$uiState$1(continuation);
        jadwalViewModel$uiState$1.L$0 = list;
        jadwalViewModel$uiState$1.L$1 = list2;
        jadwalViewModel$uiState$1.L$2 = hari;
        return jadwalViewModel$uiState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        Hari hari = (Hari) this.L$2;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new JadwalUiState(list, hari, list2);
    }
}
