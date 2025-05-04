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
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
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
        Text(text = "Login - Smart Tourism App", style = MaterialTheme.typography.headlineMedium)

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
                    // Memanggil fungsi suspend dalam coroutine scope
                    scope.launch {
                        val user = userDao.getUserByUsernameAndPassword(username, password)
                        if (user != null) {
                            Toast.makeText(context, "Selamat datang kembali, $username!", Toast.LENGTH_SHORT).show()
                            navController.navigate("home")
                        } else {
                            Toast.makeText(context, "Username atau password salah.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Username dan password tidak boleh kosong!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Masuk")
        }

        Spacer(modifier = Modifier.height(16.dp))

        TextButton(onClick = {
            // Navigasi ke halaman RegisterScreen
            navController.navigate("register")
        }) {
            Text("Belum punya akun? Daftar di sini")
        }
    }
}
