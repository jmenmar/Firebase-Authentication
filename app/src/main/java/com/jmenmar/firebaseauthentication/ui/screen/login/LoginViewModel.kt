package com.jmenmar.firebaseauthentication.ui.screen.login

import android.content.Intent
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.firebase.auth.GoogleAuthProvider
import com.jmenmar.firebaseauthentication.domain.usecase.LoginUseCases
import com.jmenmar.firebaseauthentication.ui.util.PasswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCases: LoginUseCases
) : ViewModel() {
    var state by mutableStateOf(LoginState())
        private set

    fun onEvent(event: LoginEvent) {
        when(event) {
            is LoginEvent.EmailChange -> {
                state = state.copy(email = event.email)
            }
            is LoginEvent.PasswordChange -> {
                state = state.copy(password = event.password)
            }
            is LoginEvent.Login -> {
                login()
            }
        }
    }

    private fun login() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )
        if (!loginUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = "Invalid email"
            )
        }
        val passwordResult = loginUseCases.validatePasswordUseCase(state.password)
        state = state.copy(
            passwordError = PasswordErrorParser.parseError(passwordResult)
        )

        if (state.emailError == null && state.passwordError == null) {
            state = state.copy(isLoading = true)
            viewModelScope.launch(Dispatchers.IO) {
                loginUseCases.loginWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(isLoggedIn = true)
                }.onFailure {
                    state = state.copy(emailError = it.message)
                }
                state = state.copy(isLoading = false)
            }
        }
    }

    fun loginWithGoogleLauncher(launcher: ManagedActivityResultLauncher<Intent, ActivityResult>) {
        loginUseCases.loginWithGoogleLauncherUseCase(googleSignInLauncher = launcher)
    }

    fun loginWithGoogleResult(activityResult: ActivityResult) {
        state = state.copy(isLoading = true, googleError = null)
        loginUseCases.loginWithGoogleResultUseCase(task = GoogleSignIn.getSignedInAccountFromIntent(activityResult.data)).onSuccess {
            val credential = GoogleAuthProvider.getCredential(it.idToken, null)
            viewModelScope.launch {
                loginUseCases.loginWithGoogleCredential(credential = credential).onSuccess {
                    state = state.copy(isLoggedIn = true)
                }.onFailure { error ->
                    state = state.copy(googleError = error.message)
                }
            }
        }.onFailure {
            state = state.copy(googleError = it.message)
        }
        state = state.copy(isLoading = false)
    }
}