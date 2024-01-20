package com.jmenmar.firebaseauthentication.ui.screens.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.data.network.AuthRepo
import com.jmenmar.firebaseauthentication.data.network.AuthRepositoryImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val authRepositoryImpl: AuthRepositoryImpl
) : ViewModel() {
    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _error = MutableStateFlow("")
    val error = _error.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun isUserLogged(): Boolean {
        return authRepositoryImpl.isUserAuthenticated
    }

    fun login(navigateToHome: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            try {
                _error.value = ""
                val result = withContext(Dispatchers.IO) {
                    authRepositoryImpl.login(email.value, password.value)
                }

                if (result != null) {
                    navigateToHome()
                }
            } catch (e: Exception) {
                _error.value = context.getString(R.string.incorrect_email_password)
            }

            _loading.value = false
        }
    }
}