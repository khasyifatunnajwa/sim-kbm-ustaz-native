package com.rork.simkbmustaz.ui.viewmodel;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import com.rork.simkbmustaz.data.local.IzinMengajarEntity;
import com.rork.simkbmustaz.data.local.KelasEntity;
import java.util.List;
import kotlin.Metadata;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.IntrinsicsKt;
import kotlin.coroutines.jvm.internal.DebugMetadata;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function3;

/* JADX INFO: compiled from: IzinMengajarViewModel.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000\u0016\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00040\u00032\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00060\u0003H\n"}, d2 = {"<anonymous>", "Lcom/rork/simkbmustaz/ui/viewmodel/IzinUiState;", "izin", "", "Lcom/rork/simkbmustaz/data/local/IzinMengajarEntity;", "kelas", "Lcom/rork/simkbmustaz/data/local/KelasEntity;"}, k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
@DebugMetadata(c = "com.rork.simkbmustaz.ui.viewmodel.IzinMengajarViewModel$uiState$1", f = "IzinMengajarViewModel.kt", i = {}, l = {}, m = "invokeSuspend", n = {}, nl = {}, s = {}, v = 2)
final class IzinMengajarViewModel$uiState$1 extends SuspendLambda implements Function3<List<? extends IzinMengajarEntity>, List<? extends KelasEntity>, Continuation<? super IzinUiState>, Object> {
    /* synthetic */ Object L$0;
    /* synthetic */ Object L$1;
    int label;

    IzinMengajarViewModel$uiState$1(Continuation<? super IzinMengajarViewModel$uiState$1> continuation) {
        super(3, continuation);
    }

    @Override // kotlin.jvm.functions.Function3
    public /* bridge */ /* synthetic */ Object invoke(List<? extends IzinMengajarEntity> list, List<? extends KelasEntity> list2, Continuation<? super IzinUiState> continuation) {
        return invoke2((List<IzinMengajarEntity>) list, (List<KelasEntity>) list2, continuation);
    }

    /* JADX INFO: renamed from: invoke, reason: avoid collision after fix types in other method */
    public final Object invoke2(List<IzinMengajarEntity> list, List<KelasEntity> list2, Continuation<? super IzinUiState> continuation) {
        IzinMengajarViewModel$uiState$1 izinMengajarViewModel$uiState$1 = new IzinMengajarViewModel$uiState$1(continuation);
        izinMengajarViewModel$uiState$1.L$0 = list;
        izinMengajarViewModel$uiState$1.L$1 = list2;
        return izinMengajarViewModel$uiState$1.invokeSuspend(Unit.INSTANCE);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        List list = (List) this.L$0;
        List list2 = (List) this.L$1;
        IntrinsicsKt.getCOROUTINE_SUSPENDED();
        if (this.label != 0) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        ResultKt.throwOnFailure(obj);
        return new IzinUiState(list, list2);
    }
}
