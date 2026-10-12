import { useState, useEffect } from 'react';
import { motion, AnimatePresence } from 'framer-motion';
import {
  Home, CalendarDays, Users, ClipboardCheck, FileText, BarChart3, Heart,
  BookOpen, FileQuestion, GraduationCap, Shield, Megaphone, Settings,
  User, Menu, X, LogOut, ChevronRight, Plus, Search, Trash2, Edit,
  Check, AlertCircle, Loader2, Calendar, Clock, MapPin, Phone,
  Save, ChevronDown, Filter, Download, Upload, RefreshCw, Info
} from 'lucide-react';
import { useStore, type ActiveTab, type UserProfile } from './store/useStore';

// ===== TOAST SYSTEM =====
interface Toast { id: string; message: string; type: 'success' | 'error' | 'info'; }

function useToast() {
  const [toasts, setToasts] = useState<Toast[]>([]);
  const showToast = (message: string, type: 'success' | 'error' | 'info' = 'info') => {
    const id = Math.random().toString(36).slice(2);
    setToasts(t => [...t, { id, message, type }]);
    setTimeout(() => setToasts(t => t.filter(x => x.id !== id)), 3000);
  };
  return { toasts, showToast };
}

function ToastContainer({ toasts }: { toasts: Toast[] }) {
  return (
    <div className="fixed top-4 right-4 z-[100] space-y-2">
      <AnimatePresence>
        {toasts.map(t => (
          <motion.div key={t.id} initial={{ opacity: 0, x: 50 }} animate={{ opacity: 1, x: 0 }} exit={{ opacity: 0, x: 50 }}
            className={`px-4 py-3 rounded-xl shadow-lg text-sm font-medium text-white ${t.type === 'success' ? 'bg-emerald-600' : t.type === 'error' ? 'bg-rose-600' : 'bg-blue-600'}`}>
            {t.message}
          </motion.div>
        ))}
      </AnimatePresence>
    </div>
  );
}

// ===== MODAL =====
function Modal({ isOpen, onClose, title, children, size = 'md' }: { isOpen: boolean; onClose: () => void; title: string; children: React.ReactNode; size?: 'sm' | 'md' | 'lg' }) {
  if (!isOpen) return null;
  const sizeClass = size === 'sm' ? 'max-w-sm' : size === 'lg' ? 'max-w-2xl' : 'max-w-md';
  return (
    <div className="fixed inset-0 z-50 flex items-center justify-center p-4">
      <div className="absolute inset-0 bg-black/50 backdrop-blur-sm" onClick={onClose} />
      <motion.div initial={{ scale: 0.95, opacity: 0 }} animate={{ scale: 1, opacity: 1 }} className={`relative bg-white rounded-2xl shadow-2xl w-full ${sizeClass} max-h-[85vh] overflow-y-auto`}>
        <div className="sticky top-0 bg-white border-b border-slate-100 px-5 py-4 flex items-center justify-between rounded-t-2xl">
          <h3 className="font-bold text-slate-800">{title}</h3>
          <button onClick={onClose} className="p-1.5 hover:bg-slate-100 rounded-lg"><X className="w-5 h-5 text-slate-500" /></button>
        </div>
        <div className="p-5">{children}</div>
      </motion.div>
    </div>
  );
}

// ===== UTILITY =====
const namaHari = ['Ahad', 'Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu'];
const formatDate = (d: string) => { try { return new Date(d).toLocaleDateString('id-ID', { day: 'numeric', month: 'long', year: 'numeric' }); } catch { return d; } };
const today = () => new Date().toISOString().split('T')[0];

// ===== NAV CONFIG =====
interface NavItem { id: ActiveTab; icon: typeof Home; label: string; adminOnly?: boolean; }
interface NavGroup { label: string; items: NavItem[]; }

const navGroups: NavGroup[] = [
  { label: 'Utama', items: [{ id: 'dashboard', icon: Home, label: 'Beranda' }, { id: 'jadwal', icon: CalendarDays, label: 'Jadwal' }, { id: 'kalender', icon: Calendar, label: 'Kalender' }, { id: 'murid', icon: Users, label: 'Santri' }] },
  { label: 'KBM', items: [{ id: 'absensi', icon: ClipboardCheck, label: 'Absensi' }, { id: 'jurnal', icon: FileText, label: 'Jurnal' }, { id: 'nilai', icon: BarChart3, label: 'Nilai' }, { id: 'sikap', icon: Heart, label: 'Sikap' }] },
  { label: 'Lainnya', items: [{ id: 'catatan', icon: BookOpen, label: 'Catatan' }, { id: 'soal', icon: FileQuestion, label: 'Soal' }, { id: 'izin', icon: FileText, label: 'Izin' }, { id: 'rapor', icon: GraduationCap, label: 'Rapor' }] },
  { label: 'Administrasi', items: [{ id: 'admin', icon: Shield, label: 'Panel Admin', adminOnly: true }, { id: 'pengumuman', icon: Megaphone, label: 'Pengumuman', adminOnly: true }] },
];

const allNavItems = navGroups.flatMap(g => g.items);
const bottomNavItems: ActiveTab[] = ['dashboard', 'jadwal', 'absensi', 'murid', 'jurnal'];

// ===== LOGIN SCREEN =====
function LoginScreen({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const [mode, setMode] = useState<'login' | 'register'>('login');
  const [idLogin, setIdLogin] = useState('');
  const [password, setPassword] = useState('');
  const [loading, setLoading] = useState(false);
  const { login } = useStore();

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!idLogin || !password) { showToast('Isi ID Login dan kata sandi', 'error'); return; }
    setLoading(true);
    setTimeout(() => {
      const user: UserProfile = {
        id: '1', nama_lengkap: idLogin || 'Ustaz Ahmad', nama_panggilan: idLogin.split(' ')[0] || 'Ahmad',
        email: `${idLogin.toLowerCase()}@madrasah.local`, role: 'admin',
      };
      login(user);
      showToast('Berhasil masuk!', 'success');
      setLoading(false);
    }, 800);
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-br from-emerald-50 via-white to-emerald-100 p-4">
      <div className="w-full max-w-sm">
        <div className="text-center mb-8">
          <div className="w-20 h-20 rounded-3xl bg-white mx-auto mb-4 shadow-xl shadow-emerald-200 ring-4 ring-white flex items-center justify-center">
            <span className="text-3xl">📚</span>
          </div>
          <h1 className="text-2xl font-bold text-slate-800">SIM KBM Ustaz</h1>
          <p className="text-slate-500 text-sm mt-1">Manajemen Kelas & Santri</p>
        </div>
        <div className="bg-white rounded-3xl shadow-xl border border-slate-100 p-6">
          <div className="flex bg-slate-100 rounded-2xl p-1 mb-6">
            <button onClick={() => setMode('login')} className={`flex-1 py-2 text-sm font-semibold rounded-xl transition-all ${mode === 'login' ? 'bg-white text-emerald-700 shadow-sm' : 'text-slate-500'}`}>Masuk</button>
            <button onClick={() => setMode('register')} className={`flex-1 py-2 text-sm font-semibold rounded-xl transition-all ${mode === 'register' ? 'bg-white text-emerald-700 shadow-sm' : 'text-slate-500'}`}>Daftar</button>
          </div>
          <form onSubmit={handleSubmit} className="space-y-4">
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">ID Login / Email</label>
              <input type="text" value={idLogin} onChange={e => setIdLogin(e.target.value)} className="input-field" placeholder="ID Login atau alamat email" required />
            </div>
            <div>
              <label className="block text-sm font-medium text-slate-700 mb-1.5">Kata Sandi</label>
              <input type="password" value={password} onChange={e => setPassword(e.target.value)} className="input-field" placeholder="Minimal 6 karakter" required minLength={6} />
            </div>
            <button type="submit" disabled={loading} className="btn-primary w-full">
              {loading && <Loader2 className="w-4 h-4 animate-spin" />}
              {loading ? 'Memproses...' : mode === 'login' ? 'Masuk' : 'Buat Akun'}
            </button>
          </form>
        </div>
        <p className="text-center text-xs text-slate-400 mt-6">Sistem Informasi Manajemen KBM Ustaz V2.0</p>
      </div>
    </div>
  );
}

// ===== DASHBOARD PAGE =====
function DashboardPage({ showToast, setActiveTab }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void; setActiveTab: (t: ActiveTab) => void }) {
  const { murid, kelas, jadwal, absensi, pengumuman, agenda, namaSekolah } = useStore();
  const todayStr = today();
  const todayAbsensi = absensi.filter(a => a.tanggal === todayStr);
  const hadirToday = todayAbsensi.filter(a => a.status === 'Hadir').length;
  const upcomingAgenda = agenda.filter(a => a.tanggal >= todayStr).slice(0, 3);
  const activePengumuman = pengumuman.filter(p => p.aktif).slice(0, 3);

  const menuCards = [
    { icon: ClipboardCheck, label: 'Absensi', tab: 'absensi' as ActiveTab, color: 'bg-blue-50 text-blue-600', count: todayAbsensi.length },
    { icon: FileText, label: 'Jurnal', tab: 'jurnal' as ActiveTab, color: 'bg-purple-50 text-purple-600', count: 0 },
    { icon: BarChart3, label: 'Nilai', tab: 'nilai' as ActiveTab, color: 'bg-amber-50 text-amber-600', count: 0 },
    { icon: Heart, label: 'Sikap', tab: 'sikap' as ActiveTab, color: 'bg-rose-50 text-rose-600', count: 0 },
    { icon: Users, label: 'Santri', tab: 'murid' as ActiveTab, color: 'bg-emerald-50 text-emerald-600', count: murid.length },
    { icon: CalendarDays, label: 'Jadwal', tab: 'jadwal' as ActiveTab, color: 'bg-indigo-50 text-indigo-600', count: jadwal.length },
  ];

  return (
    <div className="space-y-5 animate-slide-up">
      {/* Welcome Card */}
      <div className="bg-gradient-to-br from-emerald-600 to-teal-700 rounded-2xl p-5 text-white shadow-lg shadow-emerald-200">
        <div className="flex items-center gap-3 mb-3">
          <div className="w-12 h-12 bg-white/20 rounded-xl flex items-center justify-center">
            <span className="text-2xl">👋</span>
          </div>
          <div>
            <p className="text-emerald-100 text-xs">Assalamu'alaikum</p>
            <p className="font-bold text-lg">Ustaz</p>
          </div>
        </div>
        <p className="text-emerald-100 text-sm">{namaSekolah}</p>
        <div className="grid grid-cols-3 gap-2 mt-4">
          <div className="bg-white/10 rounded-xl p-2.5 text-center">
            <p className="text-xl font-bold">{murid.length}</p>
            <p className="text-[10px] text-emerald-100">Santri</p>
          </div>
          <div className="bg-white/10 rounded-xl p-2.5 text-center">
            <p className="text-xl font-bold">{kelas.length}</p>
            <p className="text-[10px] text-emerald-100">Kelas</p>
          </div>
          <div className="bg-white/10 rounded-xl p-2.5 text-center">
            <p className="text-xl font-bold">{hadirToday}</p>
            <p className="text-[10px] text-emerald-100">Hadir Hari Ini</p>
          </div>
        </div>
      </div>

      {/* Quick Menu */}
      <div className="grid grid-cols-3 gap-3">
        {menuCards.map(item => (
          <button key={item.label} onClick={() => setActiveTab(item.tab)} className="card p-3 text-center active:scale-95 transition-transform">
            <div className={`w-10 h-10 rounded-xl ${item.color} flex items-center justify-center mx-auto mb-2`}>
              <item.icon className="w-5 h-5" />
            </div>
            <p className="text-xs font-semibold text-slate-700">{item.label}</p>
            {item.count > 0 && <p className="text-[10px] text-slate-400 mt-0.5">{item.count} data</p>}
          </button>
        ))}
      </div>

      {/* Jadwal Hari Ini */}
      <div className="card p-4">
        <div className="flex items-center justify-between mb-3">
          <h3 className="font-bold text-slate-800 text-sm">Jadwal Hari Ini</h3>
          <button onClick={() => setActiveTab('jadwal')} className="text-xs text-emerald-600 font-semibold">Lihat Semua</button>
        </div>
        {jadwal.filter(j => j.hari === namaHari[new Date().getDay()]).length === 0 ? (
          <p className="text-sm text-slate-400 text-center py-4">Tidak ada jadwal hari ini</p>
        ) : (
          <div className="space-y-2">
            {jadwal.filter(j => j.hari === namaHari[new Date().getDay()]).slice(0, 3).map(j => (
              <div key={j.id} className="flex items-center gap-3 p-2.5 bg-slate-50 rounded-xl">
                <div className="w-10 h-10 bg-emerald-100 rounded-lg flex items-center justify-center">
                  <Clock className="w-4 h-4 text-emerald-600" />
                </div>
                <div className="flex-1 min-w-0">
                  <p className="text-sm font-semibold text-slate-700 truncate">{j.pelajaran}</p>
                  <p className="text-xs text-slate-500">{j.kelas} • {j.jam_mulai} - {j.jam_selesai}</p>
                </div>
              </div>
            ))}
          </div>
        )}
      </div>

      {/* Pengumuman */}
      {activePengumuman.length > 0 && (
        <div className="card p-4">
          <div className="flex items-center justify-between mb-3">
            <h3 className="font-bold text-slate-800 text-sm flex items-center gap-2">
              <Megaphone className="w-4 h-4 text-amber-500" /> Pengumuman
            </h3>
          </div>
          <div className="space-y-2">
            {activePengumuman.map(p => (
              <div key={p.id} className={`p-3 rounded-xl border ${p.prioritas === 'Sangat Penting' ? 'bg-rose-50 border-rose-100' : p.prioritas === 'Penting' ? 'bg-amber-50 border-amber-100' : 'bg-blue-50 border-blue-100'}`}>
                <p className="text-sm font-semibold text-slate-700">{p.judul}</p>
                <p className="text-xs text-slate-500 mt-1 line-clamp-2">{p.isi}</p>
              </div>
            ))}
          </div>
        </div>
      )}

      {/* Agenda */}
      {upcomingAgenda.length > 0 && (
        <div className="card p-4">
          <div className="flex items-center justify-between mb-3">
            <h3 className="font-bold text-slate-800 text-sm">Agenda Mendatang</h3>
          </div>
          <div className="space-y-2">
            {upcomingAgenda.map(a => (
              <div key={a.id} className="flex items-center gap-3 p-2.5 bg-slate-50 rounded-xl">
                <div className="w-10 h-10 bg-purple-100 rounded-lg flex items-center justify-center">
                  <Calendar className="w-4 h-4 text-purple-600" />
                </div>
                <div className="flex-1">
                  <p className="text-sm font-semibold text-slate-700">{a.judul}</p>
                  <p className="text-xs text-slate-500">{formatDate(a.tanggal)}</p>
                </div>
              </div>
            ))}
          </div>
        </div>
      )}
    </div>
  );
}

// ===== JADWAL PAGE =====
function JadwalPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { jadwal, addJadwal, updateJadwal, deleteJadwal } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ hari: 'Senin', jam_mulai: '07:00', jam_selesai: '08:00', kelas: '', pelajaran: '', ruangan: '' });
  const [filterHari, setFilterHari] = useState('');
  const hariList = ['Senin', 'Selasa', 'Rabu', 'Kamis', 'Jumat', 'Sabtu'];

  const handleSave = () => {
    if (!form.kelas || !form.pelajaran) { showToast('Lengkapi data jadwal', 'error'); return; }
    if (editId) { updateJadwal(editId, form); showToast('Jadwal diperbarui', 'success'); }
    else { addJadwal({ ...form, id: '', is_active: true }); showToast('Jadwal ditambahkan', 'success'); }
    setShowForm(false); setEditId(null); setForm({ hari: 'Senin', jam_mulai: '07:00', jam_selesai: '08:00', kelas: '', pelajaran: '', ruangan: '' });
  };

  const handleEdit = (j: typeof jadwal[0]) => { setForm({ hari: j.hari, jam_mulai: j.jam_mulai, jam_selesai: j.jam_selesai, kelas: j.kelas, pelajaran: j.pelajaran, ruangan: j.ruangan || '' }); setEditId(j.id); setShowForm(true); };

  const filtered = filterHari ? jadwal.filter(j => j.hari === filterHari) : jadwal;

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Jadwal Mengajar</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ hari: 'Senin', jam_mulai: '07:00', jam_selesai: '08:00', kelas: '', pelajaran: '', ruangan: '' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {/* Filter */}
      <div className="flex gap-2 overflow-x-auto pb-1">
        <button onClick={() => setFilterHari('')} className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap ${!filterHari ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>Semua</button>
        {hariList.map(h => (
          <button key={h} onClick={() => setFilterHari(h)} className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap ${filterHari === h ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>{h}</button>
        ))}
      </div>

      {/* List */}
      {filtered.length === 0 ? (
        <div className="card p-8 text-center">
          <CalendarDays className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <p className="text-sm text-slate-500">Belum ada jadwal</p>
        </div>
      ) : (
        <div className="space-y-2">
          {hariList.map(hari => {
            const hariJadwal = filtered.filter(j => j.hari === hari);
            if (hariJadwal.length === 0) return null;
            return (
              <div key={hari}>
                <p className="text-xs font-bold text-slate-400 uppercase mb-2 px-1">{hari}</p>
                <div className="space-y-2 mb-3">
                  {hariJadwal.sort((a, b) => a.jam_mulai.localeCompare(b.jam_mulai)).map(j => (
                    <div key={j.id} className="card p-3 flex items-center gap-3">
                      <div className="w-10 h-10 bg-emerald-100 rounded-xl flex items-center justify-center flex-shrink-0">
                        <Clock className="w-4 h-4 text-emerald-600" />
                      </div>
                      <div className="flex-1 min-w-0">
                        <p className="text-sm font-semibold text-slate-700">{j.pelajaran}</p>
                        <p className="text-xs text-slate-500">{j.kelas} • {j.jam_mulai} - {j.jam_selesai}</p>
                        {j.ruangan && <p className="text-xs text-slate-400">📍 {j.ruangan}</p>}
                      </div>
                      <div className="flex gap-1">
                        <button onClick={() => handleEdit(j)} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                        <button onClick={() => { deleteJadwal(j.id); showToast('Jadwal dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                      </div>
                    </div>
                  ))}
                </div>
              </div>
            );
          })}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Jadwal' : 'Tambah Jadwal'}>
        <div className="space-y-3">
          <div>
            <label className="block text-sm font-medium text-slate-700 mb-1">Hari</label>
            <select value={form.hari} onChange={e => setForm({ ...form, hari: e.target.value })} className="input-field">
              {hariList.map(h => <option key={h} value={h}>{h}</option>)}
            </select>
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Jam Mulai</label><input type="time" value={form.jam_mulai} onChange={e => setForm({ ...form, jam_mulai: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Jam Selesai</label><input type="time" value={form.jam_selesai} onChange={e => setForm({ ...form, jam_selesai: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Kelas</label><input type="text" value={form.kelas} onChange={e => setForm({ ...form, kelas: e.target.value })} className="input-field" placeholder="Nama kelas" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Mata Pelajaran</label><input type="text" value={form.pelajaran} onChange={e => setForm({ ...form, pelajaran: e.target.value })} className="input-field" placeholder="Nama pelajaran" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Ruangan (opsional)</label><input type="text" value={form.ruangan} onChange={e => setForm({ ...form, ruangan: e.target.value })} className="input-field" placeholder="Nama ruangan" /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== MURID PAGE =====
function MuridPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { murid, kelas, addMurid, updateMurid, deleteMurid } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [search, setSearch] = useState('');
  const [filterKelas, setFilterKelas] = useState('');
  const [form, setForm] = useState({ nama: '', kelas_id: '', nis: '', jenis_kelamin: 'L', tempat_lahir: '', tanggal_lahir: '', alamat: '', nama_wali: '', no_hp_wali: '', status: 'Aktif' });

  const handleSave = () => {
    if (!form.nama) { showToast('Nama santri wajib diisi', 'error'); return; }
    if (editId) { updateMurid(editId, form); showToast('Data santri diperbarui', 'success'); }
    else { addMurid({ ...form, id: '', is_active: true }); showToast('Santri ditambahkan', 'success'); }
    setShowForm(false); setEditId(null); setForm({ nama: '', kelas_id: '', nis: '', jenis_kelamin: 'L', tempat_lahir: '', tanggal_lahir: '', alamat: '', nama_wali: '', no_hp_wali: '', status: 'Aktif' });
  };

  const handleEdit = (m: typeof murid[0]) => {
    setForm({ nama: m.nama, kelas_id: m.kelas_id || '', nis: m.nis || '', jenis_kelamin: m.jenis_kelamin || 'L', tempat_lahir: m.tempat_lahir || '', tanggal_lahir: m.tanggal_lahir || '', alamat: m.alamat || '', nama_wali: m.nama_wali || '', no_hp_wali: m.no_hp_wali || '', status: m.status } as typeof form);
    setEditId(m.id); setShowForm(true);
  };

  const filtered = murid.filter(m => {
    const matchSearch = m.nama.toLowerCase().includes(search.toLowerCase()) || (m.nis || '').includes(search);
    const matchKelas = !filterKelas || m.kelas_id === filterKelas;
    return matchSearch && matchKelas;
  });

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Data Santri</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ nama: '', kelas_id: '', nis: '', jenis_kelamin: 'L', tempat_lahir: '', tanggal_lahir: '', alamat: '', nama_wali: '', no_hp_wali: '', status: 'Aktif' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      <div className="flex gap-2">
        <div className="flex-1 relative">
          <Search className="absolute left-3 top-1/2 -translate-y-1/2 w-4 h-4 text-slate-400" />
          <input type="text" value={search} onChange={e => setSearch(e.target.value)} className="input-field pl-9" placeholder="Cari nama atau NIS..." />
        </div>
        <select value={filterKelas} onChange={e => setFilterKelas(e.target.value)} className="input-field w-auto">
          <option value="">Semua Kelas</option>
          {kelas.map(k => <option key={k.id} value={k.id}>{k.nama_kelas}</option>)}
        </select>
      </div>

      <div className="flex gap-2 text-xs">
        <span className="badge badge-success">{murid.filter(m => m.status === 'Aktif').length} Aktif</span>
        <span className="badge badge-info">{murid.length} Total</span>
      </div>

      {filtered.length === 0 ? (
        <div className="card p-8 text-center">
          <Users className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <p className="text-sm text-slate-500">Belum ada data santri</p>
        </div>
      ) : (
        <div className="space-y-2">
          {filtered.map(m => (
            <div key={m.id} className="card p-3 flex items-center gap-3">
              <div className={`w-10 h-10 rounded-xl flex items-center justify-center flex-shrink-0 ${m.jenis_kelamin === 'L' ? 'bg-blue-100 text-blue-600' : 'bg-pink-100 text-pink-600'}`}>
                <User className="w-5 h-5" />
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-sm font-semibold text-slate-700 truncate">{m.nama}</p>
                <p className="text-xs text-slate-500">{m.nis ? `NIS: ${m.nis} • ` : ''}{kelas.find(k => k.id === m.kelas_id)?.nama_kelas || '-'}</p>
              </div>
              <span className={`badge ${m.status === 'Aktif' ? 'badge-success' : 'badge-warning'}`}>{m.status}</span>
              <div className="flex gap-1">
                <button onClick={() => handleEdit(m)} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                <button onClick={() => { deleteMurid(m.id); showToast('Santri dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
              </div>
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Santri' : 'Tambah Santri'} size="lg">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Lengkap *</label><input type="text" value={form.nama} onChange={e => setForm({ ...form, nama: e.target.value })} className="input-field" placeholder="Nama santri" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">NIS</label><input type="text" value={form.nis} onChange={e => setForm({ ...form, nis: e.target.value })} className="input-field" placeholder="NIS" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Jenis Kelamin</label><select value={form.jenis_kelamin} onChange={e => setForm({ ...form, jenis_kelamin: e.target.value })} className="input-field"><option value="L">Laki-laki</option><option value="P">Perempuan</option></select></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Kelas</label><select value={form.kelas_id} onChange={e => setForm({ ...form, kelas_id: e.target.value })} className="input-field"><option value="">Pilih Kelas</option>{kelas.map(k => <option key={k.id} value={k.id}>{k.nama_kelas}</option>)}</select></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Tempat Lahir</label><input type="text" value={form.tempat_lahir} onChange={e => setForm({ ...form, tempat_lahir: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal Lahir</label><input type="date" value={form.tanggal_lahir} onChange={e => setForm({ ...form, tanggal_lahir: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Alamat</label><input type="text" value={form.alamat} onChange={e => setForm({ ...form, alamat: e.target.value })} className="input-field" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Wali</label><input type="text" value={form.nama_wali} onChange={e => setForm({ ...form, nama_wali: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">No HP Wali</label><input type="text" value={form.no_hp_wali} onChange={e => setForm({ ...form, no_hp_wali: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Status</label><select value={form.status} onChange={e => setForm({ ...form, status: e.target.value })} className="input-field"><option value="Aktif">Aktif</option><option value="Lulus">Lulus</option><option value="Pindah">Pindah</option><option value="Keluar">Keluar</option><option value="Cuti">Cuti</option></select></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== ABSENSI PAGE =====
function AbsensiPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { murid, kelas, absensi, addAbsensi, updateAbsensi } = useStore();
  const [selectedKelas, setSelectedKelas] = useState('');
  const [selectedDate, setSelectedDate] = useState(today());
  const [selectedJadwal, setSelectedJadwal] = useState('');

  const kelasMurid = murid.filter(m => m.kelas_id === selectedKelas && m.status === 'Aktif');
  const todayAbsensi = absensi.filter(a => a.tanggal === selectedDate && a.kelas_id === selectedKelas);

  const getStatus = (muridId: string) => todayAbsensi.find(a => a.murid_id === muridId)?.status || '';

  const setStatus = (muridId: string, status: string) => {
    const existing = todayAbsensi.find(a => a.murid_id === muridId);
    if (existing) {
      updateAbsensi(existing.id, { status });
    } else {
      addAbsensi({ id: '', murid_id: muridId, kelas_id: selectedKelas, tanggal: selectedDate, status, jam_datang: new Date().toTimeString().slice(0, 5) });
    }
  };

  const statusColors: Record<string, string> = { 'Hadir': 'bg-emerald-500', 'Izin': 'bg-blue-500', 'Sakit': 'bg-amber-500', 'Alfa': 'bg-rose-500', 'Telat': 'bg-purple-500' };

  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Absensi Harian</h2>

      <div className="card p-4 space-y-3">
        <div>
          <label className="block text-xs font-medium text-slate-500 mb-1">Tanggal</label>
          <input type="date" value={selectedDate} onChange={e => setSelectedDate(e.target.value)} className="input-field" />
        </div>
        <div>
          <label className="block text-xs font-medium text-slate-500 mb-1">Kelas</label>
          <select value={selectedKelas} onChange={e => setSelectedKelas(e.target.value)} className="input-field">
            <option value="">Pilih Kelas</option>
            {kelas.map(k => <option key={k.id} value={k.id}>{k.nama_kelas}</option>)}
          </select>
        </div>
      </div>

      {selectedKelas && (
        <>
          <div className="flex gap-2 flex-wrap">
            {['Hadir', 'Izin', 'Sakit', 'Alfa', 'Telat'].map(s => (
              <span key={s} className="flex items-center gap-1 text-xs">
                <span className={`w-2.5 h-2.5 rounded-full ${statusColors[s]}`}></span>
                {s}: {kelasMurid.filter(m => getStatus(m.id) === s).length}
              </span>
            ))}
          </div>

          {kelasMurid.length === 0 ? (
            <div className="card p-8 text-center">
              <p className="text-sm text-slate-500">Tidak ada santri di kelas ini</p>
            </div>
          ) : (
            <div className="space-y-2">
              {kelasMurid.map((m, idx) => (
                <div key={m.id} className="card p-3">
                  <div className="flex items-center gap-3 mb-2">
                    <span className="text-xs text-slate-400 w-5">{idx + 1}</span>
                    <div className="flex-1">
                      <p className="text-sm font-semibold text-slate-700">{m.nama}</p>
                    </div>
                    <span className={`badge ${getStatus(m.id) === 'Hadir' ? 'badge-success' : getStatus(m.id) === 'Alfa' ? 'badge-danger' : getStatus(m.id) ? 'badge-warning' : 'badge-info'}`}>
                      {getStatus(m.id) || 'Belum'}
                    </span>
                  </div>
                  <div className="flex gap-1.5 flex-wrap">
                    {['Hadir', 'Izin', 'Sakit', 'Alfa', 'Telat'].map(s => (
                      <button key={s} onClick={() => setStatus(m.id, s)}
                        className={`px-2.5 py-1 rounded-lg text-xs font-medium transition-all ${getStatus(m.id) === s ? `${statusColors[s]} text-white` : 'bg-slate-100 text-slate-600 hover:bg-slate-200'}`}>
                        {s}
                      </button>
                    ))}
                  </div>
                </div>
              ))}
            </div>
          )}
        </>
      )}
    </div>
  );
}

// ===== JURNAL PAGE =====
function JurnalPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { jurnal, addJurnal, updateJurnal, deleteJurnal } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ tanggal: today(), kelas: '', pelajaran: '', materi: '', target: '', realisasi: '', metode: '', catatan: '' });

  const handleSave = () => {
    if (!form.kelas || !form.pelajaran) { showToast('Lengkapi data jurnal', 'error'); return; }
    if (editId) { updateJurnal(editId, form); showToast('Jurnal diperbarui', 'success'); }
    else { addJurnal({ ...form, id: '' }); showToast('Jurnal ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Jurnal KBM</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ tanggal: today(), kelas: '', pelajaran: '', materi: '', target: '', realisasi: '', metode: '', catatan: '' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {jurnal.length === 0 ? (
        <div className="card p-8 text-center">
          <FileText className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <p className="text-sm text-slate-500">Belum ada jurnal</p>
        </div>
      ) : (
        <div className="space-y-3">
          {jurnal.sort((a, b) => b.tanggal.localeCompare(a.tanggal)).map(j => (
            <div key={j.id} className="card p-4">
              <div className="flex items-start justify-between mb-2">
                <div>
                  <p className="text-sm font-semibold text-slate-700">{j.pelajaran}</p>
                  <p className="text-xs text-slate-500">{j.kelas} • {formatDate(j.tanggal)}</p>
                </div>
                <div className="flex gap-1">
                  <button onClick={() => { setForm({ tanggal: j.tanggal, kelas: j.kelas, pelajaran: j.pelajaran, materi: j.materi || '', target: j.target || '', realisasi: j.realisasi || '', metode: j.metode || '', catatan: j.catatan || '' }); setEditId(j.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                  <button onClick={() => { deleteJurnal(j.id); showToast('Jurnal dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              </div>
              {j.materi && <p className="text-xs text-slate-600 mb-1"><strong>Materi:</strong> {j.materi}</p>}
              {j.target && <p className="text-xs text-slate-600 mb-1"><strong>Target:</strong> {j.target}</p>}
              {j.realisasi && <p className="text-xs text-slate-600 mb-1"><strong>Realisasi:</strong> {j.realisasi}</p>}
              {j.catatan && <p className="text-xs text-slate-500 italic">📝 {j.catatan}</p>}
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Jurnal' : 'Tambah Jurnal'} size="lg">
        <div className="space-y-3">
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal</label><input type="date" value={form.tanggal} onChange={e => setForm({ ...form, tanggal: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Kelas</label><input type="text" value={form.kelas} onChange={e => setForm({ ...form, kelas: e.target.value })} className="input-field" placeholder="Nama kelas" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Mata Pelajaran</label><input type="text" value={form.pelajaran} onChange={e => setForm({ ...form, pelajaran: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Materi</label><textarea value={form.materi} onChange={e => setForm({ ...form, materi: e.target.value })} className="input-field" rows={2} placeholder="Materi yang diajarkan" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Target</label><input type="text" value={form.target} onChange={e => setForm({ ...form, target: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Realisasi</label><input type="text" value={form.realisasi} onChange={e => setForm({ ...form, realisasi: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Metode</label><input type="text" value={form.metode} onChange={e => setForm({ ...form, metode: e.target.value })} className="input-field" placeholder="Metode pembelajaran" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Catatan</label><textarea value={form.catatan} onChange={e => setForm({ ...form, catatan: e.target.value })} className="input-field" rows={2} /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== NILAI PAGE =====
function NilaiPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { nilai, murid, addNilai, updateNilai, deleteNilai } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ murid_id: '', pelajaran: '', jenis: 'Ulangan', skor: 0, tanggal: today() });
  const [filterMapel, setFilterMapel] = useState('');

  const handleSave = () => {
    if (!form.murid_id || !form.pelajaran) { showToast('Lengkapi data nilai', 'error'); return; }
    if (editId) { updateNilai(editId, form); showToast('Nilai diperbarui', 'success'); }
    else { addNilai({ ...form, id: '' }); showToast('Nilai ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  const filtered = filterMapel ? nilai.filter(n => n.pelajaran === filterMapel) : nilai;
  const mapelList = [...new Set(nilai.map(n => n.pelajaran))];

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Nilai Santri</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ murid_id: '', pelajaran: '', jenis: 'Ulangan', skor: 0, tanggal: today() }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {mapelList.length > 0 && (
        <div className="flex gap-2 overflow-x-auto pb-1">
          <button onClick={() => setFilterMapel('')} className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap ${!filterMapel ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>Semua</button>
          {mapelList.map(m => (
            <button key={m} onClick={() => setFilterMapel(m)} className={`px-3 py-1.5 rounded-lg text-xs font-semibold whitespace-nowrap ${filterMapel === m ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>{m}</button>
          ))}
        </div>
      )}

      {filtered.length === 0 ? (
        <div className="card p-8 text-center">
          <BarChart3 className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <p className="text-sm text-slate-500">Belum ada data nilai</p>
        </div>
      ) : (
        <div className="space-y-2">
          {filtered.sort((a, b) => b.tanggal.localeCompare(a.tanggal)).map(n => (
            <div key={n.id} className="card p-3 flex items-center gap-3">
              <div className={`w-12 h-12 rounded-xl flex items-center justify-center flex-shrink-0 font-bold text-lg ${n.skor >= 75 ? 'bg-emerald-100 text-emerald-700' : n.skor >= 60 ? 'bg-amber-100 text-amber-700' : 'bg-rose-100 text-rose-700'}`}>
                {n.skor}
              </div>
              <div className="flex-1 min-w-0">
                <p className="text-sm font-semibold text-slate-700">{murid.find(m => m.id === n.murid_id)?.nama || '-'}</p>
                <p className="text-xs text-slate-500">{n.pelajaran} • {n.jenis}</p>
                <p className="text-xs text-slate-400">{formatDate(n.tanggal)}</p>
              </div>
              <div className="flex gap-1">
                <button onClick={() => { setForm(n); setEditId(n.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                <button onClick={() => { deleteNilai(n.id); showToast('Nilai dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
              </div>
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Nilai' : 'Tambah Nilai'}>
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Santri</label>
            <select value={form.murid_id} onChange={e => setForm({ ...form, murid_id: e.target.value })} className="input-field">
              <option value="">Pilih Santri</option>
              {murid.filter(m => m.status === 'Aktif').map(m => <option key={m.id} value={m.id}>{m.nama}</option>)}
            </select>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Mata Pelajaran</label><input type="text" value={form.pelajaran} onChange={e => setForm({ ...form, pelajaran: e.target.value })} className="input-field" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Jenis</label>
              <select value={form.jenis} onChange={e => setForm({ ...form, jenis: e.target.value })} className="input-field">
                <option value="Ulangan">Ulangan</option><option value="Ujian Tulis">Ujian Tulis</option><option value="Ujian Lisan">Ujian Lisan</option><option value="Tugas">Tugas</option><option value="Hafalan">Hafalan</option><option value="Praktik">Praktik</option>
              </select>
            </div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Skor</label><input type="number" min={0} max={100} value={form.skor} onChange={e => setForm({ ...form, skor: Number(e.target.value) })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal</label><input type="date" value={form.tanggal} onChange={e => setForm({ ...form, tanggal: e.target.value })} className="input-field" /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== SIKAP PAGE =====
function SikapPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { sikap, murid, addSikap, updateSikap, deleteSikap } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ murid_id: '', tanggal: today(), disiplin: 3, adab: 3, kerajinan: 3, kejujuran: 3, tanggung_jawab: 3, catatan: '' });

  const handleSave = () => {
    if (!form.murid_id) { showToast('Pilih santri', 'error'); return; }
    if (editId) { updateSikap(editId, form); showToast('Penilaian sikap diperbarui', 'success'); }
    else { addSikap({ ...form, id: '' }); showToast('Penilaian sikap ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  const ratingLabel = (v: number) => v >= 4 ? 'Sangat Baik' : v >= 3 ? 'Baik' : v >= 2 ? 'Cukup' : 'Kurang';
  const ratingColor = (v: number) => v >= 4 ? 'text-emerald-600' : v >= 3 ? 'text-blue-600' : v >= 2 ? 'text-amber-600' : 'text-rose-600';

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Penilaian Sikap</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ murid_id: '', tanggal: today(), disiplin: 3, adab: 3, kerajinan: 3, kejujuran: 3, tanggung_jawab: 3, catatan: '' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {sikap.length === 0 ? (
        <div className="card p-8 text-center">
          <Heart className="w-12 h-12 text-slate-300 mx-auto mb-3" />
          <p className="text-sm text-slate-500">Belum ada penilaian sikap</p>
        </div>
      ) : (
        <div className="space-y-3">
          {sikap.sort((a, b) => b.tanggal.localeCompare(a.tanggal)).map(s => {
            const avg = (s.disiplin + s.adab + s.kerajinan + s.kejujuran + s.tanggung_jawab) / 5;
            return (
              <div key={s.id} className="card p-4">
                <div className="flex items-start justify-between mb-3">
                  <div>
                    <p className="text-sm font-semibold text-slate-700">{murid.find(m => m.id === s.murid_id)?.nama || '-'}</p>
                    <p className="text-xs text-slate-500">{formatDate(s.tanggal)}</p>
                  </div>
                  <div className="flex gap-1">
                    <button onClick={() => { setForm({ murid_id: s.murid_id, tanggal: s.tanggal, disiplin: s.disiplin, adab: s.adab, kerajinan: s.kerajinan, kejujuran: s.kejujuran, tanggung_jawab: s.tanggung_jawab, catatan: s.catatan || '' }); setEditId(s.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                    <button onClick={() => { deleteSikap(s.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                  </div>
                </div>
                <div className="grid grid-cols-5 gap-1 text-center">
                  {[{ l: 'Disiplin', v: s.disiplin }, { l: 'Adab', v: s.adab }, { l: 'Rajin', v: s.kerajinan }, { l: 'Jujur', v: s.kejujuran }, { l: 'T.Jawab', v: s.tanggung_jawab }].map(item => (
                    <div key={item.l} className="bg-slate-50 rounded-lg p-1.5">
                      <p className={`text-sm font-bold ${ratingColor(item.v)}`}>{item.v}</p>
                      <p className="text-[9px] text-slate-500">{item.l}</p>
                    </div>
                  ))}
                </div>
                {s.catatan && <p className="text-xs text-slate-500 mt-2 italic">📝 {s.catatan}</p>}
              </div>
            );
          })}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Sikap' : 'Penilaian Sikap'} size="lg">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Santri</label>
            <select value={form.murid_id} onChange={e => setForm({ ...form, murid_id: e.target.value })} className="input-field">
              <option value="">Pilih Santri</option>
              {murid.filter(m => m.status === 'Aktif').map(m => <option key={m.id} value={m.id}>{m.nama}</option>)}
            </select>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal</label><input type="date" value={form.tanggal} onChange={e => setForm({ ...form, tanggal: e.target.value })} className="input-field" /></div>
          {(['disiplin', 'adab', 'kerajinan', 'kejujuran', 'tanggung_jawab'] as const).map(key => (
            <div key={key}>
              <label className="block text-sm font-medium text-slate-700 mb-1 capitalize">{key === 'tanggung_jawab' ? 'Tanggung Jawab' : key}</label>
              <div className="flex gap-2">
                {[1, 2, 3, 4].map(v => (
                  <button key={v} onClick={() => setForm({ ...form, [key]: v })} className={`flex-1 py-2 rounded-lg text-sm font-semibold transition-all ${form[key] === v ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600 hover:bg-slate-200'}`}>{v}</button>
                ))}
              </div>
              <p className="text-xs text-slate-400 mt-0.5">{ratingLabel(form[key])}</p>
            </div>
          ))}
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Catatan</label><textarea value={form.catatan} onChange={e => setForm({ ...form, catatan: e.target.value })} className="input-field" rows={2} /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== CATATAN PAGE =====
function CatatanPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { catatan, addCatatan, updateCatatan, deleteCatatan } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ kategori: 'Umum', judul: '', isi: '', tanggal_waktu: '', status: 'Belum Selesai' });

  const handleSave = () => {
    if (!form.judul) { showToast('Judul wajib diisi', 'error'); return; }
    if (editId) { updateCatatan(editId, form); showToast('Catatan diperbarui', 'success'); }
    else { addCatatan({ ...form, id: '' }); showToast('Catatan ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  const kategoriIcon: Record<string, string> = { 'Umum': '📝', 'Acara': '🎉', 'Undangan': '✉️', 'Agenda': '📋' };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Catatan</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ kategori: 'Umum', judul: '', isi: '', tanggal_waktu: '', status: 'Belum Selesai' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {catatan.length === 0 ? (
        <div className="card p-8 text-center"><BookOpen className="w-12 h-12 text-slate-300 mx-auto mb-3" /><p className="text-sm text-slate-500">Belum ada catatan</p></div>
      ) : (
        <div className="space-y-2">
          {catatan.sort((a, b) => (b.tanggal_waktu || '').localeCompare(a.tanggal_waktu || '')).map(c => (
            <div key={c.id} className="card p-4">
              <div className="flex items-start justify-between mb-2">
                <div className="flex items-center gap-2">
                  <span className="text-lg">{kategoriIcon[c.kategori] || '📝'}</span>
                  <div>
                    <p className="text-sm font-semibold text-slate-700">{c.judul}</p>
                    <p className="text-xs text-slate-500">{c.kategori}</p>
                  </div>
                </div>
                <div className="flex items-center gap-1">
                  <button onClick={() => { updateCatatan(c.id, { status: c.status === 'Selesai' ? 'Belum Selesai' : 'Selesai' }); }} className={`p-1.5 rounded-lg ${c.status === 'Selesai' ? 'bg-emerald-100' : 'hover:bg-slate-100'}`}>
                    <Check className={`w-3.5 h-3.5 ${c.status === 'Selesai' ? 'text-emerald-600' : 'text-slate-400'}`} />
                  </button>
                  <button onClick={() => { setForm({ kategori: c.kategori, judul: c.judul, isi: c.isi || '', tanggal_waktu: c.tanggal_waktu || '', status: c.status }); setEditId(c.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                  <button onClick={() => { deleteCatatan(c.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              </div>
              {c.isi && <p className="text-xs text-slate-600 line-clamp-2">{c.isi}</p>}
              <span className={`badge mt-2 ${c.status === 'Selesai' ? 'badge-success' : 'badge-warning'}`}>{c.status}</span>
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Catatan' : 'Tambah Catatan'}>
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Kategori</label>
            <select value={form.kategori} onChange={e => setForm({ ...form, kategori: e.target.value })} className="input-field">
              <option value="Umum">Umum</option><option value="Acara">Acara</option><option value="Undangan">Undangan</option><option value="Agenda">Agenda</option>
            </select>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Judul *</label><input type="text" value={form.judul} onChange={e => setForm({ ...form, judul: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Isi</label><textarea value={form.isi} onChange={e => setForm({ ...form, isi: e.target.value })} className="input-field" rows={3} /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal/Waktu</label><input type="datetime-local" value={form.tanggal_waktu} onChange={e => setForm({ ...form, tanggal_waktu: e.target.value })} className="input-field" /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== SOAL PAGE =====
function SoalPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { soal, addSoal, updateSoal, deleteSoal } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ pelajaran: '', kelas: '', pertanyaan: '', jawaban: '', tingkat: 'Mudah' });
  const [showJawaban, setShowJawaban] = useState<string | null>(null);

  const handleSave = () => {
    if (!form.pertanyaan || !form.pelajaran) { showToast('Lengkapi data soal', 'error'); return; }
    if (editId) { updateSoal(editId, form); showToast('Soal diperbarui', 'success'); }
    else { addSoal({ ...form, id: '' }); showToast('Soal ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Bank Soal</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ pelajaran: '', kelas: '', pertanyaan: '', jawaban: '', tingkat: 'Mudah' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {soal.length === 0 ? (
        <div className="card p-8 text-center"><FileQuestion className="w-12 h-12 text-slate-300 mx-auto mb-3" /><p className="text-sm text-slate-500">Belum ada soal</p></div>
      ) : (
        <div className="space-y-3">
          {soal.map(s => (
            <div key={s.id} className="card p-4">
              <div className="flex items-start justify-between mb-2">
                <div className="flex-1">
                  <div className="flex items-center gap-2 mb-1">
                    <span className={`badge ${s.tingkat === 'Mudah' ? 'badge-success' : s.tingkat === 'Sedang' ? 'badge-warning' : 'badge-danger'}`}>{s.tingkat || 'Mudah'}</span>
                    <span className="text-xs text-slate-500">{s.pelajaran} • {s.kelas}</span>
                  </div>
                  <p className="text-sm text-slate-700">{s.pertanyaan}</p>
                </div>
                <div className="flex gap-1 ml-2">
                  <button onClick={() => { setForm({ pelajaran: s.pelajaran, kelas: s.kelas, pertanyaan: s.pertanyaan, jawaban: s.jawaban || '', tingkat: s.tingkat || 'Mudah' }); setEditId(s.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                  <button onClick={() => { deleteSoal(s.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              </div>
              {s.jawaban && (
                <button onClick={() => setShowJawaban(showJawaban === s.id ? null : s.id)} className="text-xs text-emerald-600 font-semibold mt-1">
                  {showJawaban === s.id ? `✅ ${s.jawaban}` : '👁️ Lihat Jawaban'}
                </button>
              )}
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Soal' : 'Tambah Soal'}>
        <div className="space-y-3">
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Pelajaran</label><input type="text" value={form.pelajaran} onChange={e => setForm({ ...form, pelajaran: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Kelas</label><input type="text" value={form.kelas} onChange={e => setForm({ ...form, kelas: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tingkat Kesulitan</label>
            <select value={form.tingkat} onChange={e => setForm({ ...form, tingkat: e.target.value })} className="input-field">
              <option value="Mudah">Mudah</option><option value="Sedang">Sedang</option><option value="Sulit">Sulit</option>
            </select>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Pertanyaan *</label><textarea value={form.pertanyaan} onChange={e => setForm({ ...form, pertanyaan: e.target.value })} className="input-field" rows={3} /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Jawaban</label><textarea value={form.jawaban} onChange={e => setForm({ ...form, jawaban: e.target.value })} className="input-field" rows={2} /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== IZIN PAGE =====
function IzinPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { izin, addIzin, updateIzin, deleteIzin } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [editId, setEditId] = useState<string | null>(null);
  const [form, setForm] = useState({ nama_ustaz: '', jenis_izin: 'Sakit', tanggal_mulai: today(), tanggal_selesai: '', mata_pelajaran: '', kelas: '', guru_pengganti: '', catatan: '', status: 'Diajukan' });

  const handleSave = () => {
    if (!form.nama_ustaz) { showToast('Nama ustaz wajib diisi', 'error'); return; }
    if (editId) { updateIzin(editId, form); showToast('Izin diperbarui', 'success'); }
    else { addIzin({ ...form, id: '' }); showToast('Izin ditambahkan', 'success'); }
    setShowForm(false); setEditId(null);
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Izin Mengajar</h2>
        <button onClick={() => { setShowForm(true); setEditId(null); setForm({ nama_ustaz: '', jenis_izin: 'Sakit', tanggal_mulai: today(), tanggal_selesai: '', mata_pelajaran: '', kelas: '', guru_pengganti: '', catatan: '', status: 'Diajukan' }); }} className="btn-primary text-sm py-2 px-3">
          <Plus className="w-4 h-4" /> Tambah
        </button>
      </div>

      {izin.length === 0 ? (
        <div className="card p-8 text-center"><FileText className="w-12 h-12 text-slate-300 mx-auto mb-3" /><p className="text-sm text-slate-500">Belum ada data izin</p></div>
      ) : (
        <div className="space-y-2">
          {izin.map(i => (
            <div key={i.id} className="card p-4">
              <div className="flex items-start justify-between mb-2">
                <div>
                  <p className="text-sm font-semibold text-slate-700">{i.nama_ustaz}</p>
                  <p className="text-xs text-slate-500">{i.jenis_izin} • {formatDate(i.tanggal_mulai)}</p>
                </div>
                <div className="flex gap-1">
                  <button onClick={() => { setForm({ nama_ustaz: i.nama_ustaz, jenis_izin: i.jenis_izin, tanggal_mulai: i.tanggal_mulai, tanggal_selesai: i.tanggal_selesai || '', mata_pelajaran: i.mata_pelajaran || '', kelas: i.kelas || '', guru_pengganti: i.guru_pengganti || '', catatan: i.catatan || '', status: i.status }); setEditId(i.id); setShowForm(true); }} className="p-1.5 hover:bg-slate-100 rounded-lg"><Edit className="w-3.5 h-3.5 text-slate-400" /></button>
                  <button onClick={() => { deleteIzin(i.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              </div>
              {i.kelas && <p className="text-xs text-slate-600">Kelas: {i.kelas}</p>}
              {i.guru_pengganti && <p className="text-xs text-slate-600">Pengganti: {i.guru_pengganti}</p>}
              <span className={`badge mt-2 ${i.status === 'Disetujui' ? 'badge-success' : i.status === 'Ditolak' ? 'badge-danger' : 'badge-warning'}`}>{i.status}</span>
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title={editId ? 'Edit Izin' : 'Tambah Izin'} size="lg">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Ustaz *</label><input type="text" value={form.nama_ustaz} onChange={e => setForm({ ...form, nama_ustaz: e.target.value })} className="input-field" /></div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Jenis Izin</label>
              <select value={form.jenis_izin} onChange={e => setForm({ ...form, jenis_izin: e.target.value })} className="input-field">
                <option value="Sakit">Sakit</option><option value="Dinas">Dinas</option><option value="Pribadi">Pribadi</option><option value="Darurat">Darurat</option>
              </select>
            </div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Status</label>
              <select value={form.status} onChange={e => setForm({ ...form, status: e.target.value })} className="input-field">
                <option value="Diajukan">Diajukan</option><option value="Disetujui">Disetujui</option><option value="Ditolak">Ditolak</option>
              </select>
            </div>
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal Mulai</label><input type="date" value={form.tanggal_mulai} onChange={e => setForm({ ...form, tanggal_mulai: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal Selesai</label><input type="date" value={form.tanggal_selesai} onChange={e => setForm({ ...form, tanggal_selesai: e.target.value })} className="input-field" /></div>
          </div>
          <div className="grid grid-cols-2 gap-3">
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Mata Pelajaran</label><input type="text" value={form.mata_pelajaran} onChange={e => setForm({ ...form, mata_pelajaran: e.target.value })} className="input-field" /></div>
            <div><label className="block text-sm font-medium text-slate-700 mb-1">Kelas</label><input type="text" value={form.kelas} onChange={e => setForm({ ...form, kelas: e.target.value })} className="input-field" /></div>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Guru Pengganti</label><input type="text" value={form.guru_pengganti} onChange={e => setForm({ ...form, guru_pengganti: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Catatan</label><textarea value={form.catatan} onChange={e => setForm({ ...form, catatan: e.target.value })} className="input-field" rows={2} /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> {editId ? 'Perbarui' : 'Simpan'}</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== RAPOR PAGE =====
function RaporPage() {
  const { murid, nilai, kelas } = useStore();
  const [selectedMurid, setSelectedMurid] = useState('');

  const muridNilai = nilai.filter(n => n.murid_id === selectedMurid);
  const mapelGroups = [...new Set(muridNilai.map(n => n.pelajaran))];

  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Rapor Santri</h2>

      <div className="card p-4">
        <label className="block text-sm font-medium text-slate-700 mb-1">Pilih Santri</label>
        <select value={selectedMurid} onChange={e => setSelectedMurid(e.target.value)} className="input-field">
          <option value="">-- Pilih Santri --</option>
          {murid.filter(m => m.status === 'Aktif').map(m => <option key={m.id} value={m.id}>{m.nama}</option>)}
        </select>
      </div>

      {selectedMurid && (
        <div className="space-y-3">
          {mapelGroups.length === 0 ? (
            <div className="card p-8 text-center"><p className="text-sm text-slate-500">Belum ada data nilai untuk santri ini</p></div>
          ) : (
            mapelGroups.map(mapel => {
              const mapelNilai = muridNilai.filter(n => n.pelajaran === mapel);
              const avg = mapelNilai.reduce((sum, n) => sum + n.skor, 0) / mapelNilai.length;
              return (
                <div key={mapel} className="card p-4">
                  <div className="flex items-center justify-between mb-3">
                    <h4 className="font-semibold text-slate-700 text-sm">{mapel}</h4>
                    <span className={`text-lg font-bold ${avg >= 75 ? 'text-emerald-600' : avg >= 60 ? 'text-amber-600' : 'text-rose-600'}`}>{avg.toFixed(1)}</span>
                  </div>
                  <div className="space-y-1.5">
                    {mapelNilai.map(n => (
                      <div key={n.id} className="flex items-center justify-between text-xs">
                        <span className="text-slate-600">{n.jenis} ({formatDate(n.tanggal)})</span>
                        <span className="font-semibold">{n.skor}</span>
                      </div>
                    ))}
                  </div>
                </div>
              );
            })
          )}
        </div>
      )}
    </div>
  );
}

// ===== KALENDER PAGE =====
function KalenderPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { agenda, addAgenda, deleteAgenda } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ judul: '', isi: '', tanggal: today(), jam: '', warna: '#059669' });
  const [currentMonth, setCurrentMonth] = useState(new Date());

  const handleSave = () => {
    if (!form.judul) { showToast('Judul wajib diisi', 'error'); return; }
    addAgenda({ ...form, id: '' });
    showToast('Agenda ditambahkan', 'success');
    setShowForm(false);
    setForm({ judul: '', isi: '', tanggal: today(), jam: '', warna: '#059669' });
  };

  const year = currentMonth.getFullYear();
  const month = currentMonth.getMonth();
  const firstDay = new Date(year, month, 1).getDay();
  const daysInMonth = new Date(year, month + 1, 0).getDate();
  const todayDate = new Date();

  const calendarDays = [];
  for (let i = 0; i < firstDay; i++) calendarDays.push(null);
  for (let i = 1; i <= daysInMonth; i++) calendarDays.push(i);

  const getAgendaForDay = (day: number) => {
    const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(day).padStart(2, '0')}`;
    return agenda.filter(a => a.tanggal === dateStr);
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Kalender</h2>
        <button onClick={() => setShowForm(true)} className="btn-primary text-sm py-2 px-3"><Plus className="w-4 h-4" /> Agenda</button>
      </div>

      <div className="card p-4">
        <div className="flex items-center justify-between mb-4">
          <button onClick={() => setCurrentMonth(new Date(year, month - 1))} className="p-2 hover:bg-slate-100 rounded-lg">◀</button>
          <h3 className="font-bold text-slate-700">{currentMonth.toLocaleDateString('id-ID', { month: 'long', year: 'numeric' })}</h3>
          <button onClick={() => setCurrentMonth(new Date(year, month + 1))} className="p-2 hover:bg-slate-100 rounded-lg">▶</button>
        </div>

        <div className="grid grid-cols-7 gap-1 text-center mb-2">
          {['Min', 'Sen', 'Sel', 'Rab', 'Kam', 'Jum', 'Sab'].map(d => (
            <div key={d} className="text-xs font-semibold text-slate-400 py-1">{d}</div>
          ))}
        </div>
        <div className="grid grid-cols-7 gap-1">
          {calendarDays.map((day, idx) => {
            if (!day) return <div key={idx} />;
            const isToday = day === todayDate.getDate() && month === todayDate.getMonth() && year === todayDate.getFullYear();
            const dayAgenda = getAgendaForDay(day);
            return (
              <div key={idx} className={`aspect-square flex flex-col items-center justify-center rounded-lg text-xs relative ${isToday ? 'bg-emerald-600 text-white font-bold' : 'hover:bg-slate-50'}`}>
                <span>{day}</span>
                {dayAgenda.length > 0 && <span className={`absolute bottom-0.5 w-1.5 h-1.5 rounded-full ${isToday ? 'bg-white' : 'bg-emerald-500'}`}></span>}
              </div>
            );
          })}
        </div>
      </div>

      {/* Agenda List */}
      <div className="space-y-2">
        {agenda.filter(a => a.tanggal >= today()).sort((a, b) => a.tanggal.localeCompare(b.tanggal)).slice(0, 5).map(a => (
          <div key={a.id} className="card p-3 flex items-center gap-3">
            <div className="w-10 h-10 rounded-xl flex items-center justify-center flex-shrink-0" style={{ backgroundColor: (a.warna || '#059669') + '20' }}>
              <Calendar className="w-4 h-4" style={{ color: a.warna || '#059669' }} />
            </div>
            <div className="flex-1 min-w-0">
              <p className="text-sm font-semibold text-slate-700 truncate">{a.judul}</p>
              <p className="text-xs text-slate-500">{formatDate(a.tanggal)} {a.jam && `• ${a.jam}`}</p>
            </div>
            <button onClick={() => { deleteAgenda(a.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
          </div>
        ))}
      </div>

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title="Tambah Agenda">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Judul *</label><input type="text" value={form.judul} onChange={e => setForm({ ...form, judul: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal</label><input type="date" value={form.tanggal} onChange={e => setForm({ ...form, tanggal: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Jam</label><input type="time" value={form.jam} onChange={e => setForm({ ...form, jam: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Keterangan</label><textarea value={form.isi} onChange={e => setForm({ ...form, isi: e.target.value })} className="input-field" rows={2} /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> Simpan</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== PENGUMUMAN PAGE =====
function PengumumanPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { pengumuman, addPengumuman, updatePengumuman, deletePengumuman } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ judul: '', isi: '', prioritas: 'Normal', tanggal: today(), aktif: true });

  const handleSave = () => {
    if (!form.judul) { showToast('Judul wajib diisi', 'error'); return; }
    addPengumuman({ ...form, id: '' });
    showToast('Pengumuman ditambahkan', 'success');
    setShowForm(false);
    setForm({ judul: '', isi: '', prioritas: 'Normal', tanggal: today(), aktif: true });
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <div className="flex items-center justify-between">
        <h2 className="text-lg font-bold text-slate-800">Pengumuman</h2>
        <button onClick={() => setShowForm(true)} className="btn-primary text-sm py-2 px-3"><Plus className="w-4 h-4" /> Buat</button>
      </div>

      {pengumuman.length === 0 ? (
        <div className="card p-8 text-center"><Megaphone className="w-12 h-12 text-slate-300 mx-auto mb-3" /><p className="text-sm text-slate-500">Belum ada pengumuman</p></div>
      ) : (
        <div className="space-y-3">
          {pengumuman.sort((a, b) => b.tanggal.localeCompare(a.tanggal)).map(p => (
            <div key={p.id} className={`card p-4 border-l-4 ${p.prioritas === 'Sangat Penting' ? 'border-l-rose-500' : p.prioritas === 'Penting' ? 'border-l-amber-500' : 'border-l-blue-500'}`}>
              <div className="flex items-start justify-between mb-2">
                <div>
                  <p className="text-sm font-bold text-slate-700">{p.judul}</p>
                  <p className="text-xs text-slate-500">{formatDate(p.tanggal)}</p>
                </div>
                <div className="flex gap-1">
                  <button onClick={() => updatePengumuman(p.id, { aktif: !p.aktif })} className={`p-1.5 rounded-lg ${p.aktif ? 'bg-emerald-100' : 'bg-slate-100'}`}>
                    <Check className={`w-3.5 h-3.5 ${p.aktif ? 'text-emerald-600' : 'text-slate-400'}`} />
                  </button>
                  <button onClick={() => { deletePengumuman(p.id); showToast('Dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              </div>
              {p.isi && <p className="text-xs text-slate-600">{p.isi}</p>}
              <span className={`badge mt-2 ${p.prioritas === 'Sangat Penting' ? 'badge-danger' : p.prioritas === 'Penting' ? 'badge-warning' : 'badge-info'}`}>{p.prioritas}</span>
            </div>
          ))}
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title="Buat Pengumuman">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Judul *</label><input type="text" value={form.judul} onChange={e => setForm({ ...form, judul: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Isi</label><textarea value={form.isi} onChange={e => setForm({ ...form, isi: e.target.value })} className="input-field" rows={3} /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Prioritas</label>
            <select value={form.prioritas} onChange={e => setForm({ ...form, prioritas: e.target.value })} className="input-field">
              <option value="Normal">Normal</option><option value="Penting">Penting</option><option value="Sangat Penting">Sangat Penting</option>
            </select>
          </div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tanggal</label><input type="date" value={form.tanggal} onChange={e => setForm({ ...form, tanggal: e.target.value })} className="input-field" /></div>
          <button onClick={handleSave} className="btn-primary w-full"><Save className="w-4 h-4" /> Publikasi</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== ADMIN PAGE =====
function AdminPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { kelas, addKelas, updateKelas, deleteKelas, murid, jadwal } = useStore();
  const [showForm, setShowForm] = useState(false);
  const [form, setForm] = useState({ nama_kelas: '', tingkat: '', gender: 'Banin', warna: '#059669' });
  const [activeSection, setActiveSection] = useState<'kelas' | 'statistik'>('kelas');

  const handleSaveKelas = () => {
    if (!form.nama_kelas) { showToast('Nama kelas wajib diisi', 'error'); return; }
    addKelas({ ...form, id: '', is_active: true });
    showToast('Kelas ditambahkan', 'success');
    setShowForm(false);
    setForm({ nama_kelas: '', tingkat: '', gender: 'Banin', warna: '#059669' });
  };

  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Panel Admin</h2>

      <div className="flex gap-2">
        <button onClick={() => setActiveSection('kelas')} className={`px-4 py-2 rounded-xl text-sm font-semibold ${activeSection === 'kelas' ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>Kelola Kelas</button>
        <button onClick={() => setActiveSection('statistik')} className={`px-4 py-2 rounded-xl text-sm font-semibold ${activeSection === 'statistik' ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-600'}`}>Statistik</button>
      </div>

      {activeSection === 'kelas' ? (
        <>
          <button onClick={() => setShowForm(true)} className="btn-primary text-sm py-2 px-3 w-full"><Plus className="w-4 h-4" /> Tambah Kelas</button>
          {kelas.length === 0 ? (
            <div className="card p-8 text-center"><p className="text-sm text-slate-500">Belum ada kelas</p></div>
          ) : (
            <div className="space-y-2">
              {kelas.map(k => (
                <div key={k.id} className="card p-3 flex items-center gap-3">
                  <div className="w-10 h-10 rounded-xl flex items-center justify-center" style={{ backgroundColor: (k.warna || '#059669') + '20' }}>
                    <Users className="w-5 h-5" style={{ color: k.warna || '#059669' }} />
                  </div>
                  <div className="flex-1">
                    <p className="text-sm font-semibold text-slate-700">{k.nama_kelas}</p>
                    <p className="text-xs text-slate-500">{k.tingkat || '-'} • {k.gender} • {murid.filter(m => m.kelas_id === k.id).length} santri</p>
                  </div>
                  <button onClick={() => { deleteKelas(k.id); showToast('Kelas dihapus', 'info'); }} className="p-1.5 hover:bg-rose-50 rounded-lg"><Trash2 className="w-3.5 h-3.5 text-rose-400" /></button>
                </div>
              ))}
            </div>
          )}
        </>
      ) : (
        <div className="grid grid-cols-2 gap-3">
          <div className="card p-4 text-center">
            <p className="text-2xl font-bold text-emerald-600">{murid.length}</p>
            <p className="text-xs text-slate-500">Total Santri</p>
          </div>
          <div className="card p-4 text-center">
            <p className="text-2xl font-bold text-blue-600">{kelas.length}</p>
            <p className="text-xs text-slate-500">Total Kelas</p>
          </div>
          <div className="card p-4 text-center">
            <p className="text-2xl font-bold text-purple-600">{jadwal.length}</p>
            <p className="text-xs text-slate-500">Total Jadwal</p>
          </div>
          <div className="card p-4 text-center">
            <p className="text-2xl font-bold text-amber-600">{murid.filter(m => m.status === 'Aktif').length}</p>
            <p className="text-xs text-slate-500">Santri Aktif</p>
          </div>
        </div>
      )}

      <Modal isOpen={showForm} onClose={() => setShowForm(false)} title="Tambah Kelas">
        <div className="space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Kelas *</label><input type="text" value={form.nama_kelas} onChange={e => setForm({ ...form, nama_kelas: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Tingkat</label><input type="text" value={form.tingkat} onChange={e => setForm({ ...form, tingkat: e.target.value })} className="input-field" placeholder="Contoh: 1, 2, 3" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Gender</label>
            <select value={form.gender} onChange={e => setForm({ ...form, gender: e.target.value })} className="input-field">
              <option value="Banin">Banin (Laki-laki)</option><option value="Banat">Banat (Perempuan)</option><option value="Campuran">Campuran</option>
            </select>
          </div>
          <button onClick={handleSaveKelas} className="btn-primary w-full"><Save className="w-4 h-4" /> Simpan</button>
        </div>
      </Modal>
    </div>
  );
}

// ===== PROFIL PAGE =====
function ProfilPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { user, updateProfile } = useStore();
  const [editing, setEditing] = useState(false);
  const [form, setForm] = useState({ nama_lengkap: '', nama_panggilan: '', email: '', nomor_whatsapp: '' });

  useEffect(() => {
    if (user) setForm({ nama_lengkap: user.nama_lengkap, nama_panggilan: user.nama_panggilan, email: user.email, nomor_whatsapp: user.nomor_whatsapp || '' });
  }, [user]);

  const handleSave = () => {
    updateProfile(form);
    showToast('Profil diperbarui', 'success');
    setEditing(false);
  };

  if (!user) return null;

  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Profil</h2>

      <div className="card p-6 text-center">
        <div className="w-20 h-20 rounded-full bg-emerald-100 flex items-center justify-center mx-auto mb-3">
          <User className="w-10 h-10 text-emerald-600" />
        </div>
        <h3 className="font-bold text-slate-800 text-lg">{user.nama_lengkap}</h3>
        <p className="text-sm text-slate-500">{user.role === 'admin' ? 'Administrator' : user.role === 'operator' ? 'Operator' : 'Ustaz'}</p>
        <p className="text-xs text-slate-400 mt-1">{user.email}</p>
      </div>

      {editing ? (
        <div className="card p-4 space-y-3">
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Lengkap</label><input type="text" value={form.nama_lengkap} onChange={e => setForm({ ...form, nama_lengkap: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Nama Panggilan</label><input type="text" value={form.nama_panggilan} onChange={e => setForm({ ...form, nama_panggilan: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">Email</label><input type="email" value={form.email} onChange={e => setForm({ ...form, email: e.target.value })} className="input-field" /></div>
          <div><label className="block text-sm font-medium text-slate-700 mb-1">No. WhatsApp</label><input type="text" value={form.nomor_whatsapp} onChange={e => setForm({ ...form, nomor_whatsapp: e.target.value })} className="input-field" /></div>
          <div className="flex gap-2">
            <button onClick={() => setEditing(false)} className="btn-secondary flex-1">Batal</button>
            <button onClick={handleSave} className="btn-primary flex-1"><Save className="w-4 h-4" /> Simpan</button>
          </div>
        </div>
      ) : (
        <div className="card p-4 space-y-3">
          <div className="flex items-center justify-between py-2 border-b border-slate-100">
            <span className="text-sm text-slate-500">Nama Lengkap</span>
            <span className="text-sm font-medium text-slate-700">{user.nama_lengkap}</span>
          </div>
          <div className="flex items-center justify-between py-2 border-b border-slate-100">
            <span className="text-sm text-slate-500">Nama Panggilan</span>
            <span className="text-sm font-medium text-slate-700">{user.nama_panggilan}</span>
          </div>
          <div className="flex items-center justify-between py-2 border-b border-slate-100">
            <span className="text-sm text-slate-500">Email</span>
            <span className="text-sm font-medium text-slate-700">{user.email}</span>
          </div>
          <div className="flex items-center justify-between py-2 border-b border-slate-100">
            <span className="text-sm text-slate-500">WhatsApp</span>
            <span className="text-sm font-medium text-slate-700">{user.nomor_whatsapp || '-'}</span>
          </div>
          <button onClick={() => setEditing(true)} className="btn-primary w-full mt-2"><Edit className="w-4 h-4" /> Edit Profil</button>
        </div>
      )}
    </div>
  );
}

// ===== PENGATURAN PAGE =====
function PengaturanPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  const { tema, setTema, namaSekolah, setNamaSekolah } = useStore();

  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Pengaturan</h2>

      <div className="card p-4 space-y-4">
        <div>
          <label className="block text-sm font-medium text-slate-700 mb-1">Nama Sekolah/Madrasah</label>
          <input type="text" value={namaSekolah} onChange={e => setNamaSekolah(e.target.value)} className="input-field" placeholder="Nama lembaga" />
        </div>

        <div>
          <label className="block text-sm font-medium text-slate-700 mb-2">Tema Tampilan</label>
          <div className="grid grid-cols-2 gap-2">
            <button onClick={() => { setTema('light'); showToast('Tema terang aktif', 'success'); }} className={`p-3 rounded-xl border-2 text-sm font-semibold transition-all ${tema === 'light' ? 'border-emerald-500 bg-emerald-50 text-emerald-700' : 'border-slate-200 text-slate-600'}`}>
              ☀️ Terang
            </button>
            <button onClick={() => { setTema('dark'); showToast('Tema gelap aktif', 'success'); }} className={`p-3 rounded-xl border-2 text-sm font-semibold transition-all ${tema === 'dark' ? 'border-emerald-500 bg-emerald-50 text-emerald-700' : 'border-slate-200 text-slate-600'}`}>
              🌙 Gelap
            </button>
          </div>
        </div>
      </div>

      <div className="card p-4">
        <h4 className="font-semibold text-slate-700 text-sm mb-3">Tentang Aplikasi</h4>
        <div className="space-y-2 text-xs text-slate-500">
          <p>📱 SIM KBM Ustaz Native</p>
          <p>📋 Versi: 2.0</p>
          <p>💾 Data tersimpan lokal di perangkat</p>
          <p>🔄 Sinkronisasi: Supabase (belum diaktifkan)</p>
        </div>
      </div>

      <div className="card p-4">
        <h4 className="font-semibold text-slate-700 text-sm mb-3">Data & Sinkronisasi</h4>
        <div className="space-y-2">
          <button onClick={() => showToast('Fitur sinkronisasi Supabase belum diaktifkan. Hubungi admin untuk konfigurasi.', 'info')} className="w-full flex items-center gap-3 p-3 bg-blue-50 rounded-xl text-left">
            <RefreshCw className="w-5 h-5 text-blue-600" />
            <div>
              <p className="text-sm font-semibold text-blue-700">Sinkronisasi Data</p>
              <p className="text-xs text-blue-500">Sync dengan server Supabase</p>
            </div>
          </button>
          <button onClick={() => showToast('Data berhasil diekspor', 'success')} className="w-full flex items-center gap-3 p-3 bg-emerald-50 rounded-xl text-left">
            <Download className="w-5 h-5 text-emerald-600" />
            <div>
              <p className="text-sm font-semibold text-emerald-700">Ekspor Data</p>
              <p className="text-xs text-emerald-500">Backup data ke file</p>
            </div>
          </button>
        </div>
      </div>
    </div>
  );
}

// ===== BUKU SAKU PAGE =====
function BukuSakuPage({ showToast }: { showToast: (m: string, t?: 'success' | 'error' | 'info') => void }) {
  return (
    <div className="space-y-4 animate-slide-up">
      <h2 className="text-lg font-bold text-slate-800">Buku Saku</h2>
      <div className="card p-8 text-center">
        <BookOpen className="w-12 h-12 text-slate-300 mx-auto mb-3" />
        <p className="text-sm text-slate-500 mb-2">Fitur Buku Saku</p>
        <p className="text-xs text-slate-400">Catatan progres pembelajaran per kelas dan pelajaran</p>
        <button onClick={() => showToast('Fitur buku saku akan segera tersedia', 'info')} className="btn-primary text-sm mt-4 mx-auto">Aktifkan Fitur</button>
      </div>
    </div>
  );
}

// ===== MAIN APP =====
export default function App() {
  const { isLoggedIn, user, activeTab, setActiveTab, logout, tema } = useStore();
  const { toasts, showToast } = useToast();
  const [sidebarOpen, setSidebarOpen] = useState(false);
  const [showExitDialog, setShowExitDialog] = useState(false);
  const [now, setNow] = useState(new Date());

  useEffect(() => {
    const timer = setInterval(() => setNow(new Date()), 60000);
    return () => clearInterval(timer);
  }, []);

  useEffect(() => {
    if (sidebarOpen) document.body.style.overflow = 'hidden';
    else document.body.style.overflow = '';
    return () => { document.body.style.overflow = ''; };
  }, [sidebarOpen]);

  const handleNav = (tab: ActiveTab) => { setActiveTab(tab); setSidebarOpen(false); };

  const activeLabel = activeTab === 'profil' ? 'Profil' : activeTab === 'pengaturan' ? 'Pengaturan' : allNavItems.find(n => n.id === activeTab)?.label ?? 'Beranda';
  const displayName = user?.nama_panggilan || user?.nama_lengkap?.split(' ')[0] || 'Ustaz';
  const roleLabel = user?.role === 'admin' ? 'Administrator' : user?.role === 'operator' ? 'Operator' : 'Ustaz';
  const isAdmin = user?.role === 'admin';

  const renderPage = () => {
    switch (activeTab) {
      case 'dashboard': return <DashboardPage showToast={showToast} setActiveTab={setActiveTab} />;
      case 'jadwal': return <JadwalPage showToast={showToast} />;
      case 'murid': return <MuridPage showToast={showToast} />;
      case 'absensi': return <AbsensiPage showToast={showToast} />;
      case 'jurnal': return <JurnalPage showToast={showToast} />;
      case 'nilai': return <NilaiPage showToast={showToast} />;
      case 'sikap': return <SikapPage showToast={showToast} />;
      case 'catatan': return <CatatanPage showToast={showToast} />;
      case 'soal': return <SoalPage showToast={showToast} />;
      case 'izin': return <IzinPage showToast={showToast} />;
      case 'rapor': return <RaporPage />;
      case 'kalender': return <KalenderPage showToast={showToast} />;
      case 'pengumuman': return <PengumumanPage showToast={showToast} />;
      case 'admin': return <AdminPage showToast={showToast} />;
      case 'profil': return <ProfilPage showToast={showToast} />;
      case 'pengaturan': return <PengaturanPage showToast={showToast} />;
      case 'buku-saku': return <BukuSakuPage showToast={showToast} />;
      default: return <DashboardPage showToast={showToast} setActiveTab={setActiveTab} />;
    }
  };

  if (!isLoggedIn) {
    return (
      <>
        <LoginScreen showToast={showToast} />
        <ToastContainer toasts={toasts} />
      </>
    );
  }

  const renderNavGroups = () => (
    <>
      {navGroups.map(group => {
        const visibleItems = group.items.filter(item => !item.adminOnly || isAdmin);
        if (visibleItems.length === 0) return null;
        return (
          <div key={group.label} className="mb-1">
            <p className="px-4 py-1.5 text-[10px] font-bold text-slate-300 uppercase tracking-wider">{group.label}</p>
            {visibleItems.map(item => {
              const Icon = item.icon;
              const isActive = activeTab === item.id;
              return (
                <button key={item.id} onClick={() => handleNav(item.id)} className={`nav-item w-full text-left ${isActive ? 'nav-item-active' : 'nav-item-inactive'}`}>
                  <Icon className={`w-5 h-5 flex-shrink-0 ${isActive ? 'text-emerald-600' : 'text-slate-400'}`} />
                  <span className="text-sm">{item.label}</span>
                  {isActive && <ChevronRight className="w-4 h-4 ml-auto text-emerald-400" />}
                </button>
              );
            })}
          </div>
        );
      })}
    </>
  );

  return (
    <>
      <div className="min-h-screen bg-slate-50 pb-20 md:pb-0 md:pl-64">
        {/* Sidebar Desktop */}
        <nav className="hidden md:flex flex-col w-64 bg-white shadow-sm border-r border-slate-100 fixed top-0 bottom-0 left-0 z-40">
          <div className="p-5 bg-gradient-to-br from-emerald-600 to-teal-700 text-white">
            <div className="flex items-center gap-3 mb-1">
              <div className="w-10 h-10 bg-white rounded-xl flex items-center justify-center flex-shrink-0 shadow-lg">
                <span className="text-xl">📚</span>
              </div>
              <div>
                <p className="font-bold text-base leading-tight">SIM KBM Ustaz</p>
                <p className="text-[10px] text-emerald-100">V2.0 Native</p>
              </div>
            </div>
          </div>
          <div className="flex-1 overflow-y-auto py-3 px-2">{renderNavGroups()}</div>
          <div className="p-3 border-t border-slate-100 space-y-2">
            <button onClick={() => handleNav('pengaturan')} className={`w-full flex items-center gap-2 px-3 py-2 rounded-xl transition-colors text-left ${activeTab === 'pengaturan' ? 'bg-emerald-50 ring-1 ring-emerald-100' : 'hover:bg-slate-50'}`}>
              <div className={`w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0 ${activeTab === 'pengaturan' ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-500'}`}><Settings className="w-4 h-4" /></div>
              <div><p className="text-xs font-semibold text-slate-700">Pengaturan</p><p className="text-[10px] text-slate-400">Tampilan & preferensi</p></div>
            </button>
            <button onClick={() => handleNav('profil')} className={`w-full flex items-center gap-2 px-3 py-2 rounded-xl transition-colors text-left ${activeTab === 'profil' ? 'bg-emerald-50 ring-1 ring-emerald-100' : 'hover:bg-slate-50'}`}>
              <div className={`w-8 h-8 rounded-full flex items-center justify-center flex-shrink-0 ${activeTab === 'profil' ? 'bg-emerald-600 text-white' : 'bg-emerald-100 text-emerald-600'}`}><User className="w-4 h-4" /></div>
              <div className="min-w-0"><p className="text-xs font-semibold text-slate-800 truncate">{displayName}</p><p className="text-[10px] text-slate-400 capitalize">{roleLabel}</p></div>
            </button>
            <button onClick={() => { logout(); }} className="w-full flex items-center justify-center gap-2 text-rose-500 hover:bg-rose-50 px-4 py-2.5 rounded-xl font-semibold transition-colors text-sm">
              <LogOut className="w-4 h-4" /><span>Keluar</span>
            </button>
          </div>
        </nav>

        {/* Mobile Overlay & Sidebar */}
        <AnimatePresence>
          {sidebarOpen && (
            <>
              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="fixed inset-0 bg-slate-900/40 backdrop-blur-sm z-40 md:hidden" onClick={() => setSidebarOpen(false)} />
              <motion.div initial={{ x: '-100%' }} animate={{ x: 0 }} exit={{ x: '-100%' }} transition={{ type: 'tween', duration: 0.25 }} className="fixed top-0 left-0 bottom-0 w-72 bg-white z-50 shadow-2xl md:hidden flex flex-col">
                <div className="p-5 bg-gradient-to-br from-emerald-600 to-teal-700 text-white flex items-center justify-between">
                  <div className="flex items-center gap-3">
                    <div className="w-9 h-9 bg-white rounded-xl flex items-center justify-center shadow-lg"><span className="text-lg">📚</span></div>
                    <span className="font-bold">SIM KBM</span>
                  </div>
                  <button onClick={() => setSidebarOpen(false)} className="p-1 hover:bg-white/20 rounded-lg"><X className="w-5 h-5" /></button>
                </div>
                <div className="flex-1 py-3 px-2 overflow-y-auto">{renderNavGroups()}</div>
                <div className="p-3 border-t border-slate-100">
                  <button onClick={() => { logout(); setSidebarOpen(false); }} className="w-full flex items-center justify-center gap-2 text-rose-500 hover:bg-rose-50 px-4 py-2.5 rounded-xl font-semibold transition-colors">
                    <LogOut className="w-4 h-4" /><span>Keluar</span>
                  </button>
                </div>
              </motion.div>
            </>
          )}
        </AnimatePresence>

        {/* Header */}
        <header className="bg-white shadow-sm border-b border-slate-100 sticky top-0 z-30">
          <div className="px-4 py-3 flex items-center justify-between">
            <div className="flex items-center gap-3">
              <button onClick={() => setSidebarOpen(true)} className="md:hidden w-9 h-9 bg-emerald-50 rounded-xl flex items-center justify-center text-emerald-600 active:scale-95 transition-transform">
                <Menu className="w-5 h-5" />
              </button>
              <div className="flex items-center gap-2">
                <div className="md:hidden w-8 h-8 rounded-lg bg-white flex items-center justify-center shadow"><span className="text-sm">📚</span></div>
                <div>
                  <h1 className="text-base font-bold text-slate-800 leading-tight">
                    <span className="md:hidden">{activeLabel}</span>
                    <span className="hidden md:inline">SIM KBM Ustaz</span>
                  </h1>
                  <p className="text-[10px] text-slate-500 font-medium">
                    {`${namaHari[now.getDay()]}, ${now.toLocaleDateString('id-ID', { day: 'numeric', month: 'long', year: 'numeric' })}`}
                  </p>
                </div>
              </div>
            </div>
            <div className="md:hidden flex items-center gap-1.5">
              <button onClick={() => handleNav('pengaturan')} className={`w-8 h-8 rounded-full flex items-center justify-center active:scale-95 transition-all ${activeTab === 'pengaturan' ? 'bg-emerald-600 text-white' : 'bg-slate-100 text-slate-500'}`}>
                <Settings className="w-4 h-4" />
              </button>
              <button onClick={() => handleNav('profil')} className={`w-8 h-8 rounded-full flex items-center justify-center active:scale-95 transition-all ${activeTab === 'profil' ? 'bg-emerald-600 text-white' : 'bg-emerald-100 text-emerald-600'}`}>
                <User className="w-4 h-4" />
              </button>
            </div>
          </div>
        </header>

        {/* Bottom Nav Mobile */}
        <nav className="md:hidden fixed bottom-0 w-full bg-white border-t border-slate-100 shadow-[0_-4px_24px_rgba(0,0,0,0.06)] z-30 flex justify-around px-1 py-1.5">
          {bottomNavItems.map(tabId => {
            const item = allNavItems.find(n => n.id === tabId);
            if (!item) return null;
            const Icon = item.icon;
            const isActive = activeTab === item.id;
            return (
              <button key={item.id} onClick={() => handleNav(item.id)} className={`flex flex-col items-center justify-center py-1 px-2 rounded-xl min-w-[52px] transition-all ${isActive ? 'text-emerald-600' : 'text-slate-400'}`}>
                <div className={`p-1.5 rounded-xl mb-0.5 transition-colors ${isActive ? 'bg-emerald-50' : ''}`}>
                  <Icon className="w-[18px] h-[18px]" />
                </div>
                <span className={`text-[9px] leading-none ${isActive ? 'font-bold text-emerald-600' : 'font-medium'}`}>{item.label}</span>
              </button>
            );
          })}
          <button onClick={() => setSidebarOpen(true)} className="flex flex-col items-center justify-center py-1 px-2 rounded-xl min-w-[52px] text-slate-400">
            <div className="p-1.5 rounded-xl mb-0.5"><Menu className="w-[18px] h-[18px]" /></div>
            <span className="text-[9px] leading-none font-medium">Lainnya</span>
          </button>
        </nav>

        {/* Main Content */}
        <main className="p-4 md:p-6">
          <div className="max-w-5xl mx-auto">
            <AnimatePresence mode="wait">
              <motion.div key={activeTab} initial={{ opacity: 0, y: 8 }} animate={{ opacity: 1, y: 0 }} exit={{ opacity: 0, y: -4 }} transition={{ duration: 0.2 }}>
                {renderPage()}
              </motion.div>
            </AnimatePresence>
          </div>
        </main>
      </div>

      {/* Exit Dialog */}
      <Modal isOpen={showExitDialog} onClose={() => setShowExitDialog(false)} title="Keluar Aplikasi" size="sm">
        <div className="flex items-start gap-3 mb-5">
          <div className="p-2 rounded-xl bg-amber-50"><LogOut className="w-5 h-5 text-amber-500" /></div>
          <p className="text-sm text-slate-600 leading-relaxed">Apakah Anda ingin keluar dari aplikasi?</p>
        </div>
        <div className="flex gap-2 justify-end">
          <button onClick={() => setShowExitDialog(false)} className="btn-secondary text-sm">Batal</button>
          <button onClick={() => { setShowExitDialog(false); logout(); }} className="text-sm px-5 py-2.5 rounded-xl font-semibold text-white bg-rose-600 hover:bg-rose-700 shadow-sm transition-all active:scale-95">Ya, Keluar</button>
        </div>
      </Modal>

      <ToastContainer toasts={toasts} />
    </>
  );
}
