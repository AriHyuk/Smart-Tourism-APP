package com.ariawaludin.smarttourismapp.navigation

import EditProfileScreen
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ariawaludin.smarttourismapp.features.auth.LoginScreen
import com.ariawaludin.smarttourismapp.features.auth.RegisterScreen
import com.ariawaludin.smarttourismapp.features.camera.CameraScreen
import com.ariawaludin.smarttourismapp.features.explore.ExploreScreen
import com.ariawaludin.smarttourismapp.features.home.AllMenuScreen
import com.ariawaludin.smarttourismapp.features.home.HomeScreen
import com.ariawaludin.smarttourismapp.features.listwisata.ListWisataScreen
import com.ariawaludin.smarttourismapp.features.maps.MapsScreen
import com.ariawaludin.smarttourismapp.features.profile.ProfileScreen
import com.ariawaludin.smarttourismapp.features.setting.SettingsScreen
import com.ariawaludin.smarttourismapp.features.splash.SplashScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavGraph(
    navController: NavHostController,
    onThemeChanged: (Boolean) -> Unit
) {

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
                        "home" -> navController.navigate("home")
                        "maps" -> navController.navigate("maps")
                        "camera" -> navController.navigate("camera")
                        "profile" -> navController.navigate("profile")
                        "settings" -> navController.navigate("settings")
                        "explore" -> navController.navigate("explore")
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
                onNavigateToEditProfile = { navController.navigate("edit_profile") },
                onThemeChanged = onThemeChanged
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
        composable("edit_profile") {
             EditProfileScreen(
                onBack = { navController.popBackStack() },
                onSave = { navController.popBackStack() }
            )
        }
        composable("allmenu") {
            AllMenuScreen(navController = navController)
        }



        // Tambahkan lainnya sesuai fitur
    }
}
