package com.jmenmar.firebaseauthentication.ui.screen.signup

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.data.network.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val authRepositoryImpl: AuthRepositoryImpl
): ViewModel() {

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _repeatPassword = MutableStateFlow("")
    val repeatPassword = _repeatPassword.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    fun setEmail(username: String) {
        _email.value = username
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun setRepeatPassword(pass: String) {
        _repeatPassword.value = pass
    }

    fun isValidForm(): Boolean {
        return email.value.isNotEmpty() &&
                password.value.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email.value).matches() &&
                passwordsMatch()
    }

    fun passwordsMatch(): Boolean {
        return password.value == repeatPassword.value
    }

    fun signUp(navigateToHome:() -> Unit) {
        viewModelScope.launch {
            _error.value = ""
            _loading.value = true

            try {
                val result = withContext(Dispatchers.IO) {
                    authRepositoryImpl.signUp(email.value, password.value)
                }

                if (result != null) {
                    navigateToHome()
                }

            } catch (e: Exception) {
                _error.value = e.message.orEmpty()
            }

            _loading.value = false
        }
    }
}