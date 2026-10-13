package com.simkbm.ustaz.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.simkbm.ustaz.data.remote.SupabaseClient
import com.simkbm.ustaz.data.session.SessionManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class AuthState(
    val isLoading: Boolean = false,
    val isLoggedIn: Boolean = false,
    val error: String? = null,
    val userId: String? = null,
    val userName: String? = null,
    val userRole: String? = null
)

class AuthViewModel(
    private val supabaseClient: SupabaseClient,
    private val sessionManager: SessionManager
) : ViewModel() {

    private val _state = MutableStateFlow(AuthState())
    val state: StateFlow<AuthState> = _state.asStateFlow()

    init {
        checkSession()
    }

    private fun checkSession() {
        viewModelScope.launch {
            sessionManager.isLoggedIn.collect { isLoggedIn ->
                _state.value = _state.value.copy(isLoggedIn = isLoggedIn)
            }
        }
    }

    fun login(idLogin: String, password: String) {
        if (idLogin.isBlank() || password.isBlank()) {
            _state.value = _state.value.copy(error = "Isi ID Login dan kata sandi")
            return
        }
        if (password.length < 6) {
            _state.value = _state.value.copy(error = "Kata sandi minimal 6 karakter")
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)

            try {
                // Determine email from ID login
                val email = if (idLogin.contains("@")) {
                    idLogin.trim().lowercase()
                } else {
                    // Try to lookup email from ID login
                    val lookupResult = supabaseClient.lookupEmail(idLogin.trim().lowercase())
                    lookupResult.getOrNull() ?: run {
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = "ID Login tidak ditemukan. Gunakan email untuk login."
                        )
                        return@launch
                    }
                }

                // Sign in with Supabase
                val result = supabaseClient.signInWithEmail(email, password)
                result.fold(
                    onSuccess = { response ->
                        response.user?.let { user ->
                            sessionManager.saveSession(
                                accessToken = response.access_token ?: "",
                                refreshToken = response.refresh_token,
                                userId = user.id ?: "",
                                email = user.email,
                                name = user.email?.split("@")?.firstOrNull(),
                                role = "ustaz"
                            )
                            _state.value = _state.value.copy(
                                isLoading = false,
                                isLoggedIn = true,
                                userId = user.id,
                                userName = user.email?.split("@")?.firstOrNull()
                            )
                        } ?: run {
                            _state.value = _state.value.copy(
                                isLoading = false,
                                error = "Login berhasil tapi data user tidak ditemukan"
                            )
                        }
                    },
                    onFailure = { error ->
                        val friendlyError = when {
                            error.message?.contains("Invalid login credentials") == true ->
                                "ID Login/Email atau kata sandi salah"
                            error.message?.contains("Email not confirmed") == true ->
                                "Email belum dikonfirmasi"
                            else -> error.message ?: "Terjadi kesalahan"
                        }
                        _state.value = _state.value.copy(
                            isLoading = false,
                            error = friendlyError
                        )
                    }
                )
            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    isLoading = false,
                    error = "Koneksi bermasalah. Periksa internet Anda."
                )
            }
        }
    }

    fun clearError() {
        _state.value = _state.value.copy(error = null)
    }
}
