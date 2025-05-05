package com.ariawaludin.smarttourismapp.features.splash


import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ariawaludin.smarttourismapp.R
import com.ariawaludin.smarttourismapp.utils.SharedPrefHelper
import kotlinx.coroutines.delay
import androidx.compose.ui.platform.LocalContext
import com.ariawaludin.smarttourismapp.features.auth.LoginScreen
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay


@Composable
fun SplashScreen(navController: NavController) {
    val context = LocalContext.current
    val sharedPrefHelper = remember { SharedPrefHelper(context) }

    LaunchedEffect(true) {
        delay(2000)

        val isLoggedIn = sharedPrefHelper.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            navController.navigate("home") {
                popUpTo("splash") { inclusive = true }
            }
        } else {
            navController.navigate("auth") {
                popUpTo("splash") { inclusive = true }
            }
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.splashhh),
                contentDescription = "Splash Logo",
                modifier = Modifier.size(200.dp)
            )
        }
    }
}
