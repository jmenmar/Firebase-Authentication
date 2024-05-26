package com.jmenmar.firebaseauthentication.ui.screen.signup

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.domain.usecase.SignupUseCases
import com.jmenmar.firebaseauthentication.ui.util.PasswordErrorParser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signupUseCases: SignupUseCases
): ViewModel() {
    var state by mutableStateOf(SignUpState())
        private set

    fun onEvent(event: SignUpEvent) {
        when (event) {
            is SignUpEvent.EmailChange -> {
                state = state.copy(
                    email = event.email
                )
            }
            is SignUpEvent.PasswordChange -> {
                state = state.copy(
                    password = event.password
                )
            }
            is SignUpEvent.RepeatPasswordChange -> {
                state = state.copy(
                    repeatPassword = event.repeatPassword
                )
            }
            SignUpEvent.LogIn -> {
                state = state.copy(
                    logIn = true
                )
            }
            SignUpEvent.SignUp -> {
                signUp()
            }
        }
    }

    private fun signUp() {
        state = state.copy(
            emailError = null,
            passwordError = null
        )
        if (!signupUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = "Invalid email"
            )
        }

        val passwordResult = signupUseCases.validatePasswordUseCase(state.password)
        state = state.copy(
            passwordError = PasswordErrorParser.parseError(passwordResult)
        )
        if (state.password != state.repeatPassword) {
            state = state.copy(
                passwordError = "Passwords do not match"
            )
        }

        if (state.emailError == null && state.passwordError == null) {
            state = state.copy(
                isLoading = true
            )
            viewModelScope.launch {
                signupUseCases.signupWithEmailUseCase(state.email, state.password).onSuccess {
                    state = state.copy(
                        isSignedIn = true
                    )
                }.onFailure {
                    state = state.copy(
                        emailError = it.message
                    )
                }
                state = state.copy(
                    isLoading = false
                )
            }
        }
    }
}
