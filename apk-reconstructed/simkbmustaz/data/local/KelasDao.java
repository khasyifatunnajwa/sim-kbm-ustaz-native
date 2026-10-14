package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: DataEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J\u0016\u0010\u0006\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\tJ\u0016\u0010\n\u001a\u00020\u00072\u0006\u0010\b\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\t¨\u0006\u000bÀ\u0006\u0003"}, d2 = {"Lcom/rork/simkbmustaz/data/local/KelasDao;", "", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/rork/simkbmustaz/data/local/KelasEntity;", "upsert", "", "kelas", "(Lcom/rork/simkbmustaz/data/local/KelasEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public interface KelasDao {
    Object delete(KelasEntity kelasEntity, Continuation<? super Unit> continuation);

    Flow<List<KelasEntity>> observeAll();

    Object upsert(KelasEntity kelasEntity, Continuation<? super Unit> continuation);
}
