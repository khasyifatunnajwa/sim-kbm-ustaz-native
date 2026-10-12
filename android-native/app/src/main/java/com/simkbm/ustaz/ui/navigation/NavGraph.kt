package com.simkbm.ustaz.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.simkbm.ustaz.ui.screens.auth.LoginScreen
import com.simkbm.ustaz.ui.screens.main.MainScreen

sealed class Screen(val route: String) {
    data object Login : Screen("login")
    data object Main : Screen("main")
    data object Dashboard : Screen("dashboard")
    data object Jadwal : Screen("jadwal")
    data object Murid : Screen("murid")
    data object Absensi : Screen("absensi")
    data object Jurnal : Screen("jurnal")
    data object Nilai : Screen("nilai")
    data object Sikap : Screen("sikap")
    data object Catatan : Screen("catatan")
    data object Soal : Screen("soal")
    data object Izin : Screen("izin")
    data object Rapor : Screen("rapor")
    data object Kalender : Screen("kalender")
    data object Pengumuman : Screen("pengumuman")
    data object Admin : Screen("admin")
    data object Profil : Screen("profil")
    data object Pengaturan : Screen("pengaturan")
}

@Composable
fun SimKbmNavHost() {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.Login.route) {
        composable(Screen.Login.route) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Screen.Main.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            )
        }
        composable(Screen.Main.route) {
            MainScreen(navController = navController)
        }
    }
}
