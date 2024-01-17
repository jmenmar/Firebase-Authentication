package com.jmenmar.firebaseauthentication.ui.screens.signup

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.data.network.AuthService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authService: AuthService
): ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun setEmail(username: String) {
        _email.value = username
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun signUp(navigateToHome:() -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    authService.signUp(email.value, password.value)
                }

                if (result != null) {
                    navigateToHome()
                }

            } catch (e: Exception) {

            }

            _loading.value = false
        }
    }
}