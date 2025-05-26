package com.ariawaludin.smarttourismapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ariawaludin.smarttourismapp.features.auth.LoginScreen
import com.ariawaludin.smarttourismapp.features.auth.RegisterScreen
import com.ariawaludin.smarttourismapp.features.camera.CameraScreen
import com.ariawaludin.smarttourismapp.features.explore.ExploreScreen
import com.ariawaludin.smarttourismapp.features.home.HomeScreen
import com.ariawaludin.smarttourismapp.features.listwisata.ListWisataScreen
import com.ariawaludin.smarttourismapp.features.maps.MapsScreen
import com.ariawaludin.smarttourismapp.features.profile.ProfileScreen
import com.ariawaludin.smarttourismapp.features.setting.SettingsScreen
import com.ariawaludin.smarttourismapp.features.splash.SplashScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = "SplashScreen"
    ) {

        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }

        composable("auth") {
            LoginScreen(navController = navController)
        }

        composable("register") {
            RegisterScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(
                navController = navController,

                onMenuClick = { route ->
                    when (route) {
                        "explore" -> navController.navigate("explore")
                        "camera" -> navController.navigate("camera")
                        "maps" -> navController.navigate("maps")
                        "profile" -> navController.navigate("profile")
                        "settings" -> navController.navigate("settings")
                    }
                },
                onNavigateToSettings = {
                    navController.navigate("settings")
                }
            )
        }

        composable("explore") {
            ExploreScreen(navController = navController)
        }

        composable("maps") {
            MapsScreen(navController = navController)
        }

        composable("camera") {
            CameraScreen(onBack = { navController.popBackStack() })
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToEditProfile = {
                    navController.navigate("edit_profile")
                }
            )
        }

        composable("profile") {
            ProfileScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToEditProfile = { navController.navigate("edit_profile") },
                onNavigateToTrips = { navController.navigate("my_trips") },
                onNavigateToFavorites = { navController.navigate("favorites") },
                onNavigateToReviews = { navController.navigate("reviews") },
                onNavigateToHelp = { navController.navigate("help") },
                onLogout = {
                    navController.navigate("auth") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
        }

        composable("listwisata") {
            ListWisataScreen(navController = navController)
        }

        // Tambahkan lainnya sesuai fitur
    }
}
