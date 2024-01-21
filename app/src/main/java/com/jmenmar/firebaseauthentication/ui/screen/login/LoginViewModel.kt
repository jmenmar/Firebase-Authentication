package com.jmenmar.firebaseauthentication.ui.screen.login

import android.content.Context
import android.util.Patterns
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.domain.model.AuthResponse
import com.jmenmar.firebaseauthentication.domain.model.MessageBarState
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository
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
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _messageBarState: MutableState<MessageBarState> = mutableStateOf(MessageBarState())
    val messageBarState: State<MessageBarState> = _messageBarState

    private val _loading = MutableStateFlow(false)
    val loading = _loading.asStateFlow()

    private val _email = MutableStateFlow("")
    val email = _email.asStateFlow()

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    val instance = authRepository

    fun setMessage(messageBarState: MessageBarState) {
        _messageBarState.value = messageBarState
    }

    fun setEmail(email: String) {
        _email.value = email
    }

    fun setPassword(pass: String) {
        _password.value = pass
    }

    fun isValidForm(): Boolean {
        return email.value.isNotEmpty() &&
                password.value.isNotEmpty() &&
                Patterns.EMAIL_ADDRESS.matcher(email.value).matches()
    }

    fun login(navigateToHome: () -> Unit) {
        viewModelScope.launch {
            _loading.value = true

            val result = withContext(Dispatchers.IO) {
                authRepository.signInWithEmailAndPassword(email.value, password.value)
            }

            when (result) {
                is AuthResponse.Success -> {
                    navigateToHome()
                }

                is AuthResponse.Error -> {
                    _messageBarState.value = MessageBarState(error = result.errorMessage)
                }
            }

            _loading.value = false
        }
    }
}