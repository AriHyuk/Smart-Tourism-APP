package com.ariawaludin.smarttourismapp.features.auth

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.ariawaludin.smarttourismapp.data.AppDatabase
import com.ariawaludin.smarttourismapp.data.User
import kotlinx.coroutines.launch

@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val userDao = db.userDao()

    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    val scope = rememberCoroutineScope() // CoroutineScope untuk mengeksekusi fungsi suspend

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Register - Smart Tourism App", style = MaterialTheme.typography.headlineMedium)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Masukkan Username") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Masukkan Password") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (username.isNotBlank() && password.isNotBlank()) {
                    val newUser = User(username = username, password = password)

                    // Menyimpan user ke database di dalam coroutine
                    scope.launch {
                        userDao.insertUser(newUser)
                        // Navigasi ke halaman login setelah berhasil register
                        Toast.makeText(context, "Akun berhasil dibuat, $username!", Toast.LENGTH_SHORT).show()
                        navController.navigate("login")
                    }
                } else {
                    Toast.makeText(context, "Semua field harus diisi!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Daftar")
        }
    }
}
