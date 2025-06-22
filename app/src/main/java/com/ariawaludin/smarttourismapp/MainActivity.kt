package com.ariawaludin.smarttourismapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.ariawaludin.smarttourismapp.navigation.NavGraph
import com.ariawaludin.smarttourismapp.ui.theme.SmartTourismAppTheme
import com.ariawaludin.smarttourismapp.utils.SharedPrefHelper

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Biar dark mode berubah real-time saat toggle
            val context = LocalContext.current
            val pref = remember { SharedPrefHelper(context) }
            // Pakai State di sini agar otomatis recomposition
            var darkMode by remember { mutableStateOf(pref.getBoolean("dark_mode", false)) }

            // Buat NavController sekali
            val navController = rememberNavController()

            // SmartTourismAppTheme: pass darkMode
            SmartTourismAppTheme(darkTheme = darkMode) {
                Surface(color = MaterialTheme.colorScheme.background) {
                    // NavGraph passing lambda supaya SettingsScreen bisa update dark mode
                    NavGraph(
                        navController = navController,
                        onThemeChanged = { newValue ->
                            darkMode = newValue
                        }
                    )
                }
            }
        }
    }
}
