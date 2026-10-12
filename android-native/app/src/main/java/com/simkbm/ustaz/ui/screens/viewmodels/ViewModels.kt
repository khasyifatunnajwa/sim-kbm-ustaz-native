package com.simkbm.ustaz.ui.screens.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simkbm.ustaz.data.local.*
import com.simkbm.ustaz.data.repository.*
import com.simkbm.ustaz.util.DateUtils
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

// ==================== DASHBOARD VIEWMODEL ====================

data class DashboardUiState(
    val totalMurid: Int = 0,
    val totalKelas: Int = 0,
    val hadirHariIni: Int = 0,
    val jadwalHariIni: List<JadwalEntity> = emptyList(),
    val pengumumanAktif: List<PengumumanEntity> = emptyList(),
    val agendaMendatang: List<AgendaEntity> = emptyList()
)

class DashboardViewModel(
    private val kelasRepo: KelasRepository,
    private val muridRepo: MuridRepository,
    private val jadwalRepo: JadwalRepository,
    private val absensiRepo: AbsensiRepository,
    private val pengumumanRepo: PengumumanRepository,
    private val agendaRepo: AgendaRepository
) : ViewModel() {

    private val _state = MutableStateFlow(DashboardUiState())
    val state: StateFlow<DashboardUiState> = _state.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Get total kelas
            kelasRepo.getAll().collect { kelas ->
                _state.value = _state.value.copy(totalKelas = kelas.size)
            }
        }
        viewModelScope.launch {
            // Get total murid
            muridRepo.getAll().collect { murid ->
                _state.value = _state.value.copy(totalMurid = murid.size)
            }
        }
        viewModelScope.launch {
            // Get jadwal hari ini
            val hariIni = DateUtils.hariIni()
            jadwalRepo.getByHari(hariIni).collect { jadwal ->
                _state.value = _state.value.copy(jadwalHariIni = jadwal)
            }
        }
        viewModelScope.launch {
            // Get absensi hari ini
            val today = DateUtils.today()
            absensiRepo.getByTanggal(today).collect { absensi ->
                val hadir = absensi.count { it.status == "Hadir" }
                _state.value = _state.value.copy(hadirHariIni = hadir)
            }
        }
        viewModelScope.launch {
            pengumumanRepo.getActive().collect { pengumuman ->
                _state.value = _state.value.copy(pengumumanAktif = pengumuman.take(3))
            }
        }
        viewModelScope.launch {
            val today = DateUtils.today()
            agendaRepo.getUpcoming(today).collect { agenda ->
                _state.value = _state.value.copy(agendaMendatang = agenda.take(3))
            }
        }
    }
}

// ==================== JADWAL VIEWMODEL ====================

data class JadwalUiState(
    val jadwal: List<JadwalEntity> = emptyList(),
    val filterHari: String = "",
    val isLoading: Boolean = false
)

class JadwalViewModel(
    private val repo: JadwalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(JadwalUiState())
    val state: StateFlow<JadwalUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { jadwal ->
                _state.value = _state.value.copy(jadwal = jadwal)
            }
        }
    }

    fun setFilterHari(hari: String) {
        _state.value = _state.value.copy(filterHari = hari)
    }

    fun addJadwal(jadwal: JadwalEntity) {
        viewModelScope.launch {
            repo.insert(jadwal)
        }
    }

    fun updateJadwal(jadwal: JadwalEntity) {
        viewModelScope.launch {
            repo.update(jadwal)
        }
    }

    fun deleteJadwal(id: String) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }

    val filteredJadwal: List<JadwalEntity>
        get() = if (_state.value.filterHari.isEmpty()) {
            _state.value.jadwal
        } else {
            _state.value.jadwal.filter { it.hari == _state.value.filterHari }
        }
}

// ==================== MURID VIEWMODEL ====================

data class MuridUiState(
    val murid: List<MuridEntity> = emptyList(),
    val searchQuery: String = "",
    val filterKelasId: String = "",
    val isLoading: Boolean = false
)

class MuridViewModel(
    private val repo: MuridRepository
) : ViewModel() {

    private val _state = MutableStateFlow(MuridUiState())
    val state: StateFlow<MuridUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { murid ->
                _state.value = _state.value.copy(murid = murid)
            }
        }
    }

    fun setSearchQuery(query: String) {
        _state.value = _state.value.copy(searchQuery = query)
    }

    fun setFilterKelas(kelasId: String) {
        _state.value = _state.value.copy(filterKelasId = kelasId)
    }

    fun addMurid(murid: MuridEntity) {
        viewModelScope.launch {
            repo.insert(murid)
        }
    }

    fun updateMurid(murid: MuridEntity) {
        viewModelScope.launch {
            repo.update(murid)
        }
    }

    fun deleteMurid(id: String) {
        viewModelScope.launch {
            repo.delete(id)
        }
    }

    val filteredMurid: List<MuridEntity>
        get() {
            var result = _state.value.murid
            if (_state.value.searchQuery.isNotEmpty()) {
                result = result.filter {
                    it.nama.contains(_state.value.searchQuery, ignoreCase = true) ||
                    (it.nis ?: "").contains(_state.value.searchQuery)
                }
            }
            if (_state.value.filterKelasId.isNotEmpty()) {
                result = result.filter { it.kelas_id == _state.value.filterKelasId }
            }
            return result
        }
}

// ==================== ABSENSI VIEWMODEL ====================

data class AbsensiUiState(
    val absensi: List<AbsensiEntity> = emptyList(),
    val selectedKelasId: String = "",
    val selectedTanggal: String = "",
    val isLoading: Boolean = false
)

class AbsensiViewModel(
    private val repo: AbsensiRepository,
    private val muridRepo: MuridRepository
) : ViewModel() {

    private val _state = MutableStateFlow(AbsensiUiState())
    val state: StateFlow<AbsensiUiState> = _state.asStateFlow()

    init {
        _state.value = _state.value.copy(selectedTanggal = DateUtils.today())
    }

    fun setSelectedKelas(kelasId: String) {
        _state.value = _state.value.copy(selectedKelasId = kelasId)
        loadAbsensi()
    }

    fun setSelectedTanggal(tanggal: String) {
        _state.value = _state.value.copy(selectedTanggal = tanggal)
        loadAbsensi()
    }

    private fun loadAbsensi() {
        if (_state.value.selectedKelasId.isNotEmpty()) {
            viewModelScope.launch {
                repo.getByTanggalAndKelas(
                    _state.value.selectedTanggal,
                    _state.value.selectedKelasId
                ).collect { absensi ->
                    _state.value = _state.value.copy(absensi = absensi)
                }
            }
        }
    }

    fun setStatus(muridId: String, status: String) {
        viewModelScope.launch {
            val existing = _state.value.absensi.find { it.murid_id == muridId }
            if (existing != null) {
                repo.update(existing.copy(status = status))
            } else {
                repo.insert(
                    AbsensiEntity(
                        id = "",
                        murid_id = muridId,
                        kelas_id = _state.value.selectedKelasId,
                        tanggal = _state.value.selectedTanggal,
                        status = status,
                        jam_datang = DateUtils.now().substringAfter("T").take(5)
                    )
                )
            }
        }
    }
}

// ==================== JURNAL VIEWMODEL ====================

data class JurnalUiState(
    val jurnal: List<JurnalEntity> = emptyList(),
    val isLoading: Boolean = false
)

class JurnalViewModel(
    private val repo: JurnalRepository
) : ViewModel() {

    private val _state = MutableStateFlow(JurnalUiState())
    val state: StateFlow<JurnalUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { jurnal ->
                _state.value = _state.value.copy(jurnal = jurnal)
            }
        }
    }

    fun addJurnal(jurnal: JurnalEntity) {
        viewModelScope.launch { repo.insert(jurnal) }
    }

    fun updateJurnal(jurnal: JurnalEntity) {
        viewModelScope.launch { repo.update(jurnal) }
    }

    fun deleteJurnal(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== NILAI VIEWMODEL ====================

data class NilaiUiState(
    val nilai: List<NilaiEntity> = emptyList(),
    val filterMapel: String = "",
    val isLoading: Boolean = false
)

class NilaiViewModel(
    private val repo: NilaiRepository
) : ViewModel() {

    private val _state = MutableStateFlow(NilaiUiState())
    val state: StateFlow<NilaiUiState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { nilai ->
                _state.value = _state.value.copy(nilai = nilai)
            }
        }
    }

    fun setFilterMapel(mapel: String) {
        _state.value = _state.value.copy(filterMapel = mapel)
    }

    fun addNilai(nilai: NilaiEntity) {
        viewModelScope.launch { repo.insert(nilai) }
    }

    fun updateNilai(nilai: NilaiEntity) {
        viewModelScope.launch { repo.update(nilai) }
    }

    fun deleteNilai(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }

    val filteredNilai: List<NilaiEntity>
        get() = if (_state.value.filterMapel.isEmpty()) {
            _state.value.nilai
        } else {
            _state.value.nilai.filter { it.pelajaran == _state.value.filterMapel }
        }
}

// ==================== SIKAP VIEWMODEL ====================

class SikapViewModel(
    private val repo: SikapRepository
) : ViewModel() {

    private val _sikap = MutableStateFlow<List<SikapEntity>>(emptyList())
    val sikap: StateFlow<List<SikapEntity>> = _sikap.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _sikap.value = it }
        }
    }

    fun add(sikap: SikapEntity) {
        viewModelScope.launch { repo.insert(sikap) }
    }

    fun update(sikap: SikapEntity) {
        viewModelScope.launch { repo.update(sikap) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== CATATAN VIEWMODEL ====================

class CatatanViewModel(
    private val repo: CatatanRepository
) : ViewModel() {

    private val _catatan = MutableStateFlow<List<CatatanEntity>>(emptyList())
    val catatan: StateFlow<List<CatatanEntity>> = _catatan.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _catatan.value = it }
        }
    }

    fun add(catatan: CatatanEntity) {
        viewModelScope.launch { repo.insert(catatan) }
    }

    fun update(catatan: CatatanEntity) {
        viewModelScope.launch { repo.update(catatan) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== SOAL VIEWMODEL ====================

class SoalViewModel(
    private val repo: SoalRepository
) : ViewModel() {

    private val _soal = MutableStateFlow<List<SoalEntity>>(emptyList())
    val soal: StateFlow<List<SoalEntity>> = _soal.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _soal.value = it }
        }
    }

    fun add(soal: SoalEntity) {
        viewModelScope.launch { repo.insert(soal) }
    }

    fun update(soal: SoalEntity) {
        viewModelScope.launch { repo.update(soal) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== IZIN VIEWMODEL ====================

class IzinViewModel(
    private val repo: IzinRepository
) : ViewModel() {

    private val _izin = MutableStateFlow<List<IzinEntity>>(emptyList())
    val izin: StateFlow<List<IzinEntity>> = _izin.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _izin.value = it }
        }
    }

    fun add(izin: IzinEntity) {
        viewModelScope.launch { repo.insert(izin) }
    }

    fun update(izin: IzinEntity) {
        viewModelScope.launch { repo.update(izin) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== PENGUMUMAN VIEWMODEL ====================

class PengumumanViewModel(
    private val repo: PengumumanRepository
) : ViewModel() {

    private val _pengumuman = MutableStateFlow<List<PengumumanEntity>>(emptyList())
    val pengumuman: StateFlow<List<PengumumanEntity>> = _pengumuman.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _pengumuman.value = it }
        }
    }

    fun add(pengumuman: PengumumanEntity) {
        viewModelScope.launch { repo.insert(pengumuman) }
    }

    fun update(pengumuman: PengumumanEntity) {
        viewModelScope.launch { repo.update(pengumuman) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}

// ==================== AGENDA VIEWMODEL ====================

class AgendaViewModel(
    private val repo: AgendaRepository
) : ViewModel() {

    private val _agenda = MutableStateFlow<List<AgendaEntity>>(emptyList())
    val agenda: StateFlow<List<AgendaEntity>> = _agenda.asStateFlow()

    init {
        viewModelScope.launch {
            repo.getAll().collect { _agenda.value = it }
        }
    }

    fun add(agenda: AgendaEntity) {
        viewModelScope.launch { repo.insert(agenda) }
    }

    fun update(agenda: AgendaEntity) {
        viewModelScope.launch { repo.update(agenda) }
    }

    fun delete(id: String) {
        viewModelScope.launch { repo.delete(id) }
    }
}
