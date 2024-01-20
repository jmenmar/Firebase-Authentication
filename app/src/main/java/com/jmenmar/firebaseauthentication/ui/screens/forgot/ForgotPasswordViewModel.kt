package com.jmenmar.firebaseauthentication.ui.screens.forgot

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.data.network.AuthRepo
import com.jmenmar.firebaseauthentication.domain.model.AuthResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val auth: AuthRepo
) : ViewModel() {

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    fun setEmail(email: String) {
        _email.value = email
    }

    fun resetPassword(navigateToLogin: () -> Unit) {
        viewModelScope.launch {
            when(auth.resetPassword(email.value)) {
                is AuthResponse.Success -> {
                    Toast.makeText(context, "Correo enviado", Toast.LENGTH_SHORT).show()
                    navigateToLogin()
                }
                is AuthResponse.Error -> {
                    Toast.makeText(context, "Error al enviar el correo", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}