package com.ariawaludin.smarttourismapp.features.threadasync

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ThreadDanAsyncScreen() {
    var isLoading by remember { mutableStateOf(false) }
    var result by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope()

    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick = {
                isLoading = true
                result = ""
                // Simulasi kerja background (contoh: fetch dari API)
                scope.launch {
                    delay(2000) // Simulasi loading 2 detik
                    result = "Data berhasil di-load pada ${System.currentTimeMillis()}"
                    isLoading = false
                }
            }
        ) {
            Text("Simulasi AsyncTask/Coroutine")
        }
        Spacer(Modifier.height(16.dp))
        if (isLoading) {
            CircularProgressIndicator()
            Text("Loading data...")
        }
        if (result.isNotEmpty()) {
            Text(result, color = Color.Green)
        }
    }
}
