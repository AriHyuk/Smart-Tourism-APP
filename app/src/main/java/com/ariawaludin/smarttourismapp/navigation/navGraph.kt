package com.ariawaludin.smarttourismapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ariawaludin.smarttourismapp.features.home.HomeScreen
import com.ariawaludin.smarttourismapp.features.listwisata.ListWisataScreen
import com.ariawaludin.smarttourismapp.features.splash.SplashScreen
import com.ariawaludin.smarttourismapp.features.auth.LoginScreen
import com.ariawaludin.smarttourismapp.features.auth.RegisterScreen
import com.ariawaludin.smarttourismapp.features.camera.CameraScreen
import com.ariawaludin.smarttourismapp.features.setting.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }
        composable("auth") {
            LoginScreen(navController = navController) // Ganti dengan login screen
        }
        composable("home") {
            HomeScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onMenuClick = { route -> navController.navigate(route) }
            )
        }
        composable("settings") {
            SettingsScreen(onBack = { navController.popBackStack() })
        }
        composable("camera") {
            CameraScreen(onBack = { navController.popBackStack() })
        }

        composable("register") {
            RegisterScreen(navController = navController)
        }

        composable("listwisata") {
            ListWisataScreen(navController = navController)
        }

        // Tambahkan composable lainnya di sini
        // composable("detail/{placeName}") { backStackEntry -> ... }
    }
}
