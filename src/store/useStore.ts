import { create } from 'zustand';
import { persist } from 'zustand/middleware';

export type ActiveTab = 'dashboard' | 'jadwal' | 'murid' | 'absensi' | 'jurnal' | 'nilai' | 'sikap' | 'catatan' | 'soal' | 'izin' | 'rapor' | 'pengumuman' | 'admin' | 'profil' | 'pengaturan' | 'kalender' | 'buku-saku';

export interface UserProfile {
  id: string;
  nama_lengkap: string;
  nama_panggilan: string;
  email: string;
  role: 'admin' | 'operator' | 'ustaz';
  foto?: string;
  nomor_whatsapp?: string;
}

export interface Kelas { id: string; nama_kelas: string; tingkat?: string; gender?: string; warna?: string; is_active: boolean; }
export interface Murid { id: string; nama: string; kelas_id?: string; nis?: string; jenis_kelamin?: string; tempat_lahir?: string; tanggal_lahir?: string; alamat?: string; nama_wali?: string; no_hp_wali?: string; status: string; is_active: boolean; }
export interface JadwalMengajar { id: string; hari: string; jam_mulai: string; jam_selesai: string; kelas: string; pelajaran: string; ruangan?: string; is_active: boolean; }
export interface AbsensiRecord { id: string; murid_id: string; kelas_id: string; tanggal: string; status: string; keterangan?: string; jam_datang?: string; }
export interface JurnalRecord { id: string; tanggal: string; kelas: string; pelajaran: string; materi?: string; target?: string; realisasi?: string; metode?: string; catatan?: string; }
export interface NilaiRecord { id: string; murid_id: string; pelajaran: string; jenis: string; skor: number; tanggal: string; }
export interface SikapRecord { id: string; murid_id: string; tanggal: string; disiplin: number; adab: number; kerajinan: number; kejujuran: number; tanggung_jawab: number; catatan?: string; }
export interface CatatanRecord { id: string; kategori: string; judul: string; isi?: string; tanggal_waktu?: string; status: string; }
export interface SoalRecord { id: string; pelajaran: string; kelas: string; pertanyaan: string; jawaban?: string; tingkat?: string; }
export interface IzinRecord { id: string; nama_ustaz: string; jenis_izin: string; tanggal_mulai: string; tanggal_selesai?: string; mata_pelajaran?: string; kelas?: string; guru_pengganti?: string; catatan?: string; status: string; }
export interface PengumumanRecord { id: string; judul: string; isi?: string; prioritas: string; tanggal: string; aktif: boolean; }
export interface AgendaRecord { id: string; judul: string; isi?: string; tanggal: string; jam?: string; warna?: string; }

interface AppState {
  isLoggedIn: boolean;
  user: UserProfile | null;
  activeTab: ActiveTab;
  kelas: Kelas[];
  murid: Murid[];
  jadwal: JadwalMengajar[];
  absensi: AbsensiRecord[];
  jurnal: JurnalRecord[];
  nilai: NilaiRecord[];
  sikap: SikapRecord[];
  catatan: CatatanRecord[];
  soal: SoalRecord[];
  izin: IzinRecord[];
  pengumuman: PengumumanRecord[];
  agenda: AgendaRecord[];
  tema: 'light' | 'dark';
  namaSekolah: string;
  login: (user: UserProfile) => void;
  logout: () => void;
  setActiveTab: (tab: ActiveTab) => void;
  addKelas: (data: Kelas) => void;
  updateKelas: (id: string, data: Partial<Kelas>) => void;
  deleteKelas: (id: string) => void;
  addMurid: (data: Murid) => void;
  updateMurid: (id: string, data: Partial<Murid>) => void;
  deleteMurid: (id: string) => void;
  addJadwal: (data: JadwalMengajar) => void;
  updateJadwal: (id: string, data: Partial<JadwalMengajar>) => void;
  deleteJadwal: (id: string) => void;
  addAbsensi: (data: AbsensiRecord) => void;
  updateAbsensi: (id: string, data: Partial<AbsensiRecord>) => void;
  addJurnal: (data: JurnalRecord) => void;
  updateJurnal: (id: string, data: Partial<JurnalRecord>) => void;
  deleteJurnal: (id: string) => void;
  addNilai: (data: NilaiRecord) => void;
  updateNilai: (id: string, data: Partial<NilaiRecord>) => void;
  deleteNilai: (id: string) => void;
  addSikap: (data: SikapRecord) => void;
  updateSikap: (id: string, data: Partial<SikapRecord>) => void;
  deleteSikap: (id: string) => void;
  addCatatan: (data: CatatanRecord) => void;
  updateCatatan: (id: string, data: Partial<CatatanRecord>) => void;
  deleteCatatan: (id: string) => void;
  addSoal: (data: SoalRecord) => void;
  updateSoal: (id: string, data: Partial<SoalRecord>) => void;
  deleteSoal: (id: string) => void;
  addIzin: (data: IzinRecord) => void;
  updateIzin: (id: string, data: Partial<IzinRecord>) => void;
  deleteIzin: (id: string) => void;
  addPengumuman: (data: PengumumanRecord) => void;
  updatePengumuman: (id: string, data: Partial<PengumumanRecord>) => void;
  deletePengumuman: (id: string) => void;
  addAgenda: (data: AgendaRecord) => void;
  updateAgenda: (id: string, data: Partial<AgendaRecord>) => void;
  deleteAgenda: (id: string) => void;
  setTema: (tema: 'light' | 'dark') => void;
  setNamaSekolah: (nama: string) => void;
  updateProfile: (data: Partial<UserProfile>) => void;
}

const uid = () => crypto.randomUUID ? crypto.randomUUID() : Math.random().toString(36).slice(2);

export const useStore = create<AppState>()(
  persist(
    (set) => ({
      isLoggedIn: false, user: null, activeTab: 'dashboard',
      kelas: [], murid: [], jadwal: [], absensi: [], jurnal: [],
      nilai: [], sikap: [], catatan: [], soal: [], izin: [],
      pengumuman: [], agenda: [], tema: 'light', namaSekolah: 'Madrasah',

      login: (user) => set({ isLoggedIn: true, user }),
      logout: () => set({ isLoggedIn: false, user: null, activeTab: 'dashboard' }),
      setActiveTab: (activeTab) => set({ activeTab }),

      addKelas: (data) => set((s) => ({ kelas: [...s.kelas, { ...data, id: data.id || uid() }] })),
      updateKelas: (id, data) => set((s) => ({ kelas: s.kelas.map(k => k.id === id ? { ...k, ...data } : k) })),
      deleteKelas: (id) => set((s) => ({ kelas: s.kelas.filter(k => k.id !== id) })),

      addMurid: (data) => set((s) => ({ murid: [...s.murid, { ...data, id: data.id || uid() }] })),
      updateMurid: (id, data) => set((s) => ({ murid: s.murid.map(m => m.id === id ? { ...m, ...data } : m) })),
      deleteMurid: (id) => set((s) => ({ murid: s.murid.filter(m => m.id !== id) })),

      addJadwal: (data) => set((s) => ({ jadwal: [...s.jadwal, { ...data, id: data.id || uid() }] })),
      updateJadwal: (id, data) => set((s) => ({ jadwal: s.jadwal.map(j => j.id === id ? { ...j, ...data } : j) })),
      deleteJadwal: (id) => set((s) => ({ jadwal: s.jadwal.filter(j => j.id !== id) })),

      addAbsensi: (data) => set((s) => ({ absensi: [...s.absensi, { ...data, id: data.id || uid() }] })),
      updateAbsensi: (id, data) => set((s) => ({ absensi: s.absensi.map(a => a.id === id ? { ...a, ...data } : a) })),

      addJurnal: (data) => set((s) => ({ jurnal: [...s.jurnal, { ...data, id: data.id || uid() }] })),
      updateJurnal: (id, data) => set((s) => ({ jurnal: s.jurnal.map(j => j.id === id ? { ...j, ...data } : j) })),
      deleteJurnal: (id) => set((s) => ({ jurnal: s.jurnal.filter(j => j.id !== id) })),

      addNilai: (data) => set((s) => ({ nilai: [...s.nilai, { ...data, id: data.id || uid() }] })),
      updateNilai: (id, data) => set((s) => ({ nilai: s.nilai.map(n => n.id === id ? { ...n, ...data } : n) })),
      deleteNilai: (id) => set((s) => ({ nilai: s.nilai.filter(n => n.id !== id) })),

      addSikap: (data) => set((s) => ({ sikap: [...s.sikap, { ...data, id: data.id || uid() }] })),
      updateSikap: (id, data) => set((s) => ({ sikap: s.sikap.map(x => x.id === id ? { ...x, ...data } : x) })),
      deleteSikap: (id) => set((s) => ({ sikap: s.sikap.filter(x => x.id !== id) })),

      addCatatan: (data) => set((s) => ({ catatan: [...s.catatan, { ...data, id: data.id || uid() }] })),
      updateCatatan: (id, data) => set((s) => ({ catatan: s.catatan.map(c => c.id === id ? { ...c, ...data } : c) })),
      deleteCatatan: (id) => set((s) => ({ catatan: s.catatan.filter(c => c.id !== id) })),

      addSoal: (data) => set((s) => ({ soal: [...s.soal, { ...data, id: data.id || uid() }] })),
      updateSoal: (id, data) => set((s) => ({ soal: s.soal.map(x => x.id === id ? { ...x, ...data } : x) })),
      deleteSoal: (id) => set((s) => ({ soal: s.soal.filter(x => x.id !== id) })),

      addIzin: (data) => set((s) => ({ izin: [...s.izin, { ...data, id: data.id || uid() }] })),
      updateIzin: (id, data) => set((s) => ({ izin: s.izin.map(x => x.id === id ? { ...x, ...data } : x) })),
      deleteIzin: (id) => set((s) => ({ izin: s.izin.filter(x => x.id !== id) })),

      addPengumuman: (data) => set((s) => ({ pengumuman: [...s.pengumuman, { ...data, id: data.id || uid() }] })),
      updatePengumuman: (id, data) => set((s) => ({ pengumuman: s.pengumuman.map(x => x.id === id ? { ...x, ...data } : x) })),
      deletePengumuman: (id) => set((s) => ({ pengumuman: s.pengumuman.filter(x => x.id !== id) })),

      addAgenda: (data) => set((s) => ({ agenda: [...s.agenda, { ...data, id: data.id || uid() }] })),
      updateAgenda: (id, data) => set((s) => ({ agenda: s.agenda.map(x => x.id === id ? { ...x, ...data } : x) })),
      deleteAgenda: (id) => set((s) => ({ agenda: s.agenda.filter(x => x.id !== id) })),

      setTema: (tema) => set({ tema }),
      setNamaSekolah: (namaSekolah) => set({ namaSekolah }),
      updateProfile: (data) => set((s) => ({ user: s.user ? { ...s.user, ...data } : null })),
    }),
    { name: 'sim-kbm-ustaz-storage' }
  )
);
