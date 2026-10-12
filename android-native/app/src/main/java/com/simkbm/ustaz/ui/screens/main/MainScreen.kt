package com.simkbm.ustaz.ui.screens.main

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.simkbm.ustaz.ui.screens.dashboard.DashboardScreen
import com.simkbm.ustaz.ui.screens.jadwal.JadwalScreen
import com.simkbm.ustaz.ui.screens.murid.MuridScreen
import com.simkbm.ustaz.ui.screens.absensi.AbsensiScreen
import com.simkbm.ustaz.ui.screens.jurnal.JurnalScreen
import com.simkbm.ustaz.ui.screens.nilai.NilaiScreen
import com.simkbm.ustaz.ui.screens.sikap.SikapScreen
import com.simkbm.ustaz.ui.screens.catatan.CatatanScreen
import com.simkbm.ustaz.ui.screens.soal.SoalScreen
import com.simkbm.ustaz.ui.screens.izin.IzinScreen
import com.simkbm.ustaz.ui.screens.rapor.RaporScreen
import com.simkbm.ustaz.ui.screens.kalender.KalenderScreen
import com.simkbm.ustaz.ui.screens.pengumuman.PengumumanScreen
import com.simkbm.ustaz.ui.screens.admin.AdminScreen
import com.simkbm.ustaz.ui.screens.profil.ProfilScreen
import com.simkbm.ustaz.ui.screens.pengaturan.PengaturanScreen
import com.simkbm.ustaz.ui.theme.Emerald50
import com.simkbm.ustaz.ui.theme.Emerald600
import com.simkbm.ustaz.ui.theme.Emerald700
import com.simkbm.ustaz.ui.theme.Slate50
import com.simkbm.ustaz.ui.theme.Slate100
import com.simkbm.ustaz.ui.theme.Slate400
import com.simkbm.ustaz.ui.theme.Slate500
import com.simkbm.ustaz.ui.theme.Slate600
import com.simkbm.ustaz.ui.theme.Slate700
import com.simkbm.ustaz.ui.theme.Slate800
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

// ==================== NAVIGATION ITEMS ====================

data class NavItem(
    val label: String,
    val icon: ImageVector,
    val route: String,
    val adminOnly: Boolean = false
)

data class NavGroup(
    val label: String,
    val items: List<NavItem>
)

val navGroups = listOf(
    NavGroup("Utama", listOf(
        NavItem("Beranda", Icons.Default.Home, "dashboard"),
        NavItem("Jadwal", Icons.Default.CalendarMonth, "jadwal"),
        NavItem("Kalender", Icons.Default.DateRange, "kalender"),
        NavItem("Santri", Icons.Default.People, "murid"),
    )),
    NavGroup("KBM", listOf(
        NavItem("Absensi", Icons.Default.CheckCircle, "absensi"),
        NavItem("Jurnal", Icons.Default.Description, "jurnal"),
        NavItem("Nilai", Icons.Default.BarChart, "nilai"),
        NavItem("Sikap", Icons.Default.Favorite, "sikap"),
    )),
    NavGroup("Lainnya", listOf(
        NavItem("Catatan", Icons.Default.Book, "catatan"),
        NavItem("Soal", Icons.Default.Help, "soal"),
        NavItem("Izin", Icons.Default.EventNote, "izin"),
        NavItem("Rapor", Icons.Default.School, "rapor"),
    )),
    NavGroup("Administrasi", listOf(
        NavItem("Panel Admin", Icons.Default.AdminPanelSettings, "admin", adminOnly = true),
        NavItem("Pengumuman", Icons.Default.Campaign, "pengumuman", adminOnly = true),
    )),
)

val allNavItems = navGroups.flatMap { it.items }
val bottomNavItems = listOf("dashboard", "jadwal", "absensi", "murid", "jurnal")

// ==================== MAIN SCREEN ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavController) {
    var selectedTab by remember { mutableStateOf("dashboard") }
    var drawerOpen by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    val drawerState = rememberDrawerState(DrawerValue.Closed)

    // Sync drawer state
    LaunchedEffect(drawerOpen) {
        if (drawerOpen) drawerState.open() else drawerState.close()
    }
    LaunchedEffect(drawerState.isOpen) {
        drawerOpen = drawerState.isOpen
    }

    // Back button handler
    var lastBackPress by remember { mutableStateOf(0L) }
    BackHandler {
        val now = System.currentTimeMillis()
        if (now - lastBackPress < 2000) {
            // Exit app
            android.app.Activity.finishAffinity(android.app.Activity())
        } else {
            lastBackPress = now
            // Show toast - will be handled by snackbar
        }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        gesturesEnabled = true,
        drawerContent = {
            DrawerContent(
                selectedTab = selectedTab,
                onTabSelected = { tab ->
                    selectedTab = tab
                    scope.launch { drawerState.close() }
                },
                onLogout = {
                    navController.popBackStack()
                }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopBar(
                    title = allNavItems.find { it.route == selectedTab }?.label ?: "Beranda",
                    onMenuClick = { scope.launch { drawerState.open() } },
                    onSettingsClick = { selectedTab = "pengaturan" },
                    onProfileClick = { selectedTab = "profil" },
                    currentTab = selectedTab
                )
            },
            bottomBar = {
                BottomNavBar(
                    selectedTab = selectedTab,
                    onTabSelected = { selectedTab = it }
                )
            }
        ) { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(Slate50)
            ) {
                when (selectedTab) {
                    "dashboard" -> DashboardScreen()
                    "jadwal" -> JadwalScreen()
                    "murid" -> MuridScreen()
                    "absensi" -> AbsensiScreen()
                    "jurnal" -> JurnalScreen()
                    "nilai" -> NilaiScreen()
                    "sikap" -> SikapScreen()
                    "catatan" -> CatatanScreen()
                    "soal" -> SoalScreen()
                    "izin" -> IzinScreen()
                    "rapor" -> RaporScreen()
                    "kalender" -> KalenderScreen()
                    "pengumuman" -> PengumumanScreen()
                    "admin" -> AdminScreen()
                    "profil" -> ProfilScreen()
                    "pengaturan" -> PengaturanScreen()
                }
            }
        }
    }
}

// ==================== TOP BAR ====================

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    title: String,
    onMenuClick: () -> Unit,
    onSettingsClick: () -> Unit,
    onProfileClick: () -> Unit,
    currentTab: String
) {
    val dateFormat = SimpleDateFormat("EEEE, d MMMM yyyy", Locale("id", "ID"))
    val today = dateFormat.format(Date())

    TopAppBar(
        title = {
            Column {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Text(
                    text = today,
                    fontSize = 11.sp,
                    color = Slate500
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Default.Menu, contentDescription = "Menu")
            }
        },
        actions = {
            IconButton(onClick = onSettingsClick) {
                Icon(
                    Icons.Default.Settings,
                    contentDescription = "Pengaturan",
                    tint = if (currentTab == "pengaturan") Emerald600 else Slate500
                )
            }
            IconButton(onClick = onProfileClick) {
                Icon(
                    Icons.Default.Person,
                    contentDescription = "Profil",
                    tint = if (currentTab == "profil") Emerald600 else Slate500
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    )
}

// ==================== BOTTOM NAV BAR ====================

@Composable
fun BottomNavBar(
    selectedTab: String,
    onTabSelected: (String) -> Unit
) {
    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 4.dp
    ) {
        bottomNavItems.forEach { route ->
            val item = allNavItems.find { it.route == route } ?: return@forEach
            val isSelected = selectedTab == route

            NavigationBarItem(
                selected = isSelected,
                onClick = { onTabSelected(route) },
                icon = {
                    Icon(
                        item.icon,
                        contentDescription = item.label,
                        modifier = Modifier.size(20.dp)
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Emerald600,
                    selectedTextColor = Emerald600,
                    indicatorColor = Emerald50
                )
            )
        }
    }
}

// ==================== DRAWER ====================

@Composable
fun DrawerContent(
    selectedTab: String,
    onTabSelected: (String) -> Unit,
    onLogout: () -> Unit
) {
    ModalDrawerSheet(
        modifier = Modifier.width(280.dp)
    ) {
        // Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = androidx.compose.ui.graphics.Brush.linearGradient(
                        colors = listOf(Emerald600, com.simkbm.ustaz.ui.theme.Teal700)
                    )
                )
                .padding(20.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    modifier = Modifier.size(40.dp),
                    shape = RoundedCornerShape(12.dp),
                    color = Color.White
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("📚", fontSize = 20.sp)
                    }
                }
                Column {
                    Text(
                        text = "SIM KBM Ustaz",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Text(
                        text = "V2.0 Native",
                        color = Color.White.copy(alpha = 0.7f),
                        fontSize = 11.sp
                    )
                }
            }
        }

        // Navigation Groups
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(vertical = 8.dp)
        ) {
            navGroups.forEach { group ->
                Text(
                    text = group.label,
                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Slate400
                )
                group.items.forEach { item ->
                    val isSelected = selectedTab == item.route
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 2.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(if (isSelected) Emerald50 else Color.Transparent)
                            .clickable { onTabSelected(item.route) }
                            .padding(horizontal = 12.dp, vertical = 10.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Icon(
                            item.icon,
                            contentDescription = item.label,
                            modifier = Modifier.size(20.dp),
                            tint = if (isSelected) Emerald600 else Slate400
                        )
                        Text(
                            text = item.label,
                            fontSize = 14.sp,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            color = if (isSelected) Emerald700 else Slate600,
                            modifier = Modifier.weight(1f)
                        )
                        if (isSelected) {
                            Icon(
                                Icons.Default.ChevronRight,
                                contentDescription = null,
                                modifier = Modifier.size(16.dp),
                                tint = Emerald600
                            )
                        }
                    }
                }
            }
        }

        // Footer
        Divider(color = Slate100)
        Column(modifier = Modifier.padding(12.dp)) {
            // Settings
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onTabSelected("pengaturan") }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Slate100),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Settings, contentDescription = null, modifier = Modifier.size(16.dp), tint = Slate500)
                }
                Column {
                    Text("Pengaturan", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate700)
                    Text("Tampilan & preferensi", fontSize = 10.sp, color = Slate400)
                }
            }

            // Profile
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .clickable { onTabSelected("profil") }
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(32.dp)
                        .clip(CircleShape)
                        .background(Emerald50),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp), tint = Emerald600)
                }
                Column {
                    Text("Ustaz", fontSize = 12.sp, fontWeight = FontWeight.SemiBold, color = Slate800)
                    Text("Administrator", fontSize = 10.sp, color = Slate400)
                }
            }

            // Logout
            Button(
                onClick = onLogout,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent,
                    contentColor = com.simkbm.ustaz.ui.theme.Rose600
                ),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.Logout, contentDescription = null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text("Keluar", fontWeight = FontWeight.SemiBold)
            }
        }
    }
}
