package com.ariawaludin.smarttourismapp.navigation


import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.ariawaludin.smarttourismapp.features.home.HomeScreen
import com.ariawaludin.smarttourismapp.features.listwisata.ListWisataScreen
import com.ariawaludin.smarttourismapp.features.splash.SplashScreen
import com.ariawaludin.smarttourismapp.features.auth.LoginScreen
import com.ariawaludin.smarttourismapp.features.auth.RegisterScreen
import com.ariawaludin.smarttourismapp.features.camera.CameraScreen
import com.ariawaludin.smarttourismapp.features.explore.ExploreScreen
import com.ariawaludin.smarttourismapp.features.maps.MapsScreen
import com.ariawaludin.smarttourismapp.features.profile.ProfileHeader
import com.ariawaludin.smarttourismapp.features.profile.ProfileScreen
import com.ariawaludin.smarttourismapp.features.setting.SettingsScreen

@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "SplashScreen") {
        composable("SplashScreen") {
            SplashScreen(navController = navController)
        }

        composable("auth") {
            LoginScreen(navController = navController)
        }

        composable("home") {
            HomeScreen(
                navController = navController,
                onMenuClick = { navController.navigate("explore") },
                onNavigateToSettings = { navController.navigate("settings") }
            )
        }

        composable("settings") {
            SettingsScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToEditProfile = { navController.navigate("edit_profile") }
            )
        }

        composable("explore") {
            MapsScreen(navController)
        }

//        composable("dining") {
//            DiningScreen(navController)
//        }
//
//        composable("hotels") {
//            HotelScreen(navController)
//        }

        composable("profile") {
            ProfileScreen(
                onNavigateToSettings = { navController.navigate("settings") },
                onNavigateToEditProfile = { navController.navigate("edit_profile") },
                onNavigateToTrips = { navController.navigate("my_trips") },
                onNavigateToFavorites = { navController.navigate("favorites") },
                onNavigateToReviews = { navController.navigate("reviews") },
                onNavigateToHelp = { navController.navigate("help") },
                onLogout = {
                    navController.navigate("login") {
                        popUpTo("home") { inclusive = true }
                    }
                }
            )
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

        // Tambahkan rute lainnya sesuai kebutuhan
    }
}
