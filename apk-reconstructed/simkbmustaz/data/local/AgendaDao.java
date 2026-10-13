package com.rork.simkbmustaz.data.local;

import androidx.compose.ui.tooling.preview.AndroidUiModes;
import java.util.List;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlinx.coroutines.flow.Flow;

/* JADX INFO: compiled from: KbmEntities.kt */
/* JADX INFO: loaded from: classes3.dex */
@Metadata(d1 = {"\u0000.\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0004\bg\u0018\u00002\u00020\u0001J\u0014\u0010\u0002\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u0003H'J&\u0010\u0006\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00050\u00040\u00032\u0006\u0010\u0007\u001a\u00020\b2\b\b\u0002\u0010\t\u001a\u00020\nH'J\u0016\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u000eJ\u0016\u0010\u000f\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\u0005H§@¢\u0006\u0002\u0010\u000e¨\u0006\u0010À\u0006\u0003"}, d2 = {"Lcom/rork/simkbmustaz/data/local/AgendaDao;", "", "observeAll", "Lkotlinx/coroutines/flow/Flow;", "", "Lcom/rork/simkbmustaz/data/local/AgendaEntity;", "observeMendatang", "mulaiDari", "", "batas", "", "upsert", "", "agenda", "(Lcom/rork/simkbmustaz/data/local/AgendaEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "delete", "app_release"}, k = 1, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
public interface AgendaDao {
    Object delete(AgendaEntity agendaEntity, Continuation<? super Unit> continuation);

    Flow<List<AgendaEntity>> observeAll();

    Flow<List<AgendaEntity>> observeMendatang(long mulaiDari, int batas);

    Object upsert(AgendaEntity agendaEntity, Continuation<? super Unit> continuation);

    /* JADX INFO: compiled from: KbmEntities.kt */
    @Metadata(k = 3, mv = {2, 3, 0}, xi = AndroidUiModes.UI_MODE_NIGHT_MASK)
    public static final class DefaultImpls {
    }

    static /* synthetic */ Flow observeMendatang$default(AgendaDao agendaDao, long j, int i, int i2, Object obj) {
        if (obj != null) {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: observeMendatang");
        }
        if ((i2 & 2) != 0) {
            i = 3;
        }
        return agendaDao.observeMendatang(j, i);
    }
}
