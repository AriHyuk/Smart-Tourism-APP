package com.ariawaludin.smarttourismapp.features.auth

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ariawaludin.smarttourismapp.data.AppDatabase
import com.ariawaludin.smarttourismapp.data.User
import kotlinx.coroutines.launch
import kotlin.random.Random

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController) {
    val context = LocalContext.current
    val db = AppDatabase.getInstance(context)
    val userDao = db.userDao()

    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var phone by remember { mutableStateOf("") }

    // OTP dialog state
    var showOtpDialog by remember { mutableStateOf(false) }
    var otpCode by remember { mutableStateOf("") }
    var inputOtp by remember { mutableStateOf("") }
    var otpError by remember { mutableStateOf("") }

    val scope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF8F9FA))
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title
            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold,
                        fontSize = 34.sp
                    )) { append("Smart") }
                    withStyle(style = SpanStyle(
                        color = Color.DarkGray,
                        fontWeight = FontWeight.Medium,
                        fontSize = 34.sp
                    )) { append("Tourism") }
                },
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Create Your Account",
                style = MaterialTheme.typography.headlineSmall.copy(
                    fontWeight = FontWeight.Bold
                ),
                color = Color.DarkGray
            )
            Text(
                text = "Start your premium tourism experience",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
            Spacer(modifier = Modifier.height(32.dp))

            // Form Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .shadow(elevation = 8.dp, shape = RoundedCornerShape(16.dp)),
                colors = CardDefaults.cardColors(containerColor = Color.White),
                shape = RoundedCornerShape(16.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // First Name
                    OutlinedTextField(
                        value = firstName,
                        onValueChange = { firstName = it },
                        label = { Text("First Name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Last Name
                    OutlinedTextField(
                        value = lastName,
                        onValueChange = { lastName = it },
                        label = { Text("Last Name") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Email
                    OutlinedTextField(
                        value = email,
                        onValueChange = { email = it },
                        label = { Text("Email") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                        leadingIcon = { Icon(Icons.Default.Email, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Username
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        modifier = Modifier.fillMaxWidth(),
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                        singleLine = true
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Password
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                                    contentDescription = "Toggle password visibility"
                                )
                            }
                        }
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    // Phone Number
                    OutlinedTextField(
                        value = phone,
                        onValueChange = { phone = it },
                        label = { Text("Phone Number") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        leadingIcon = { Icon(Icons.Default.Phone, contentDescription = null) },
                        singleLine = true
                    )
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Button Register
            Button(
                onClick = {
                    if (firstName.isBlank() || lastName.isBlank() || email.isBlank() ||
                        username.isBlank() || password.isBlank() || phone.isBlank()) {
                        Toast.makeText(context, "Please fill all fields!", Toast.LENGTH_SHORT).show()
                        return@Button
                    }
                    // Generate kode OTP random 6 digit
                    otpCode = (100000..999999).random().toString()
                    showOtpDialog = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .clip(RoundedCornerShape(28.dp))
                    .shadow(elevation = 4.dp, shape = RoundedCornerShape(28.dp)),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "CREATE ACCOUNT",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            TextButton(onClick = {
                navController.navigate("login")
            }) {
                Text(
                    "Already have an account? Log in",
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }

    // OTP Dialog
// Tambahkan parameter autofillOnOpen
    if (showOtpDialog) {
        // Auto-fill OTP saat dialog pertama kali muncul
        LaunchedEffect(showOtpDialog) {
            inputOtp = otpCode  // << autofill kode OTP!
        }

        AlertDialog(
            onDismissRequest = { showOtpDialog = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (inputOtp == otpCode) {
                            scope.launch {
                                val user = User(
                                    username = username,
                                    password = password,
                                    // extend model jika mau
                                )
                                userDao.insertUser(user)
                                Toast.makeText(context, "Register success!", Toast.LENGTH_SHORT).show()
                                showOtpDialog = false
                                navController.navigate("login")
                            }
                        } else {
                            otpError = "Kode OTP salah!"
                        }
                    }
                ) { Text("Verifikasi") }
            },
            dismissButton = {
                TextButton(onClick = { showOtpDialog = false }) { Text("Batal") }
            },
            title = { Text("Verifikasi OTP") },
            text = {
                Column {
                    Text("Kode OTP (simulasi SMS): $otpCode")
                    OutlinedTextField(
                        value = inputOtp,
                        onValueChange = { inputOtp = it },
                        label = { Text("Masukkan OTP") }
                    )
                    if (otpError.isNotEmpty()) Text(otpError, color = MaterialTheme.colorScheme.error)
                }
            }
        )
    }

}
