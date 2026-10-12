package com.simkbm.ustaz.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.simkbm.ustaz.ui.theme.Emerald600
import com.simkbm.ustaz.ui.theme.Emerald700
import com.simkbm.ustaz.ui.theme.Slate50
import com.simkbm.ustaz.ui.theme.Slate500
import com.simkbm.ustaz.ui.theme.Slate800

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: () -> Unit) {
    var idLogin by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    // Demo login - in production, use AuthViewModel with Supabase
    val handleLogin = {
        if (idLogin.isBlank() || password.isBlank()) {
            errorMessage = "Isi ID Login dan kata sandi"
            return@handleLogin
        }
        if (password.length < 6) {
            errorMessage = "Kata sandi minimal 6 karakter"
            return@handleLogin
        }
        isLoading = true
        errorMessage = null
        // Simulate login delay
        kotlinx.coroutines.MainScope().launch {
            kotlinx.coroutines.delay(800)
            isLoading = false
            onLoginSuccess()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Slate50)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Logo
            Surface(
                modifier = Modifier.size(80.dp),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 8.dp
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text("📚", fontSize = 36.sp)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "SIM KBM Ustaz",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Slate800
            )

            Text(
                text = "Manajemen Kelas & Santri",
                fontSize = 14.sp,
                color = Slate500
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Login Card
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                color = MaterialTheme.colorScheme.surface,
                shadowElevation = 4.dp
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    // Error message
                    errorMessage?.let { error ->
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.errorContainer
                        ) {
                            Text(
                                text = error,
                                modifier = Modifier.padding(12.dp),
                                color = MaterialTheme.colorScheme.onErrorContainer,
                                fontSize = 13.sp
                            )
                        }
                        Spacer(modifier = Modifier.height(16.dp))
                    }

                    // ID Login field
                    Text(
                        text = "ID Login / Email",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate800
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = idLogin,
                        onValueChange = { idLogin = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("ID Login atau alamat email") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp)
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Password field
                    Text(
                        text = "Kata Sandi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium,
                        color = Slate800
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        modifier = Modifier.fillMaxWidth(),
                        placeholder = { Text("Minimal 6 karakter") },
                        singleLine = true,
                        shape = RoundedCornerShape(12.dp),
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        trailingIcon = {
                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(
                                    imageVector = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                    contentDescription = if (passwordVisible) "Sembunyikan" else "Tampilkan"
                                )
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    // Login button
                    Button(
                        onClick = { handleLogin() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(48.dp),
                        enabled = !isLoading,
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Emerald600,
                            contentColor = androidx.compose.ui.graphics.Color.White
                        )
                    ) {
                        if (isLoading) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(20.dp),
                                color = androidx.compose.ui.graphics.Color.White,
                                strokeWidth = 2.dp
                            )
                        } else {
                            Text("Masuk", fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Sistem Informasi Manajemen KBM Ustaz V2.0",
                fontSize = 12.sp,
                color = Slate500,
                textAlign = TextAlign.Center
            )
        }
    }
}
