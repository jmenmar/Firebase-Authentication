package com.jmenmar.firebaseauthentication.ui.screen.forgot

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jmenmar.firebaseauthentication.R
import com.jmenmar.firebaseauthentication.domain.usecase.ForgotUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
    private val forgotUseCases: ForgotUseCases
) : ViewModel() {
    var state by mutableStateOf(ForgotState())
        private set

    fun onEvent(event: ForgotEvent) {
        when(event) {
            is ForgotEvent.EmailChange -> {
                state = state.copy(email = event.email)
            }
            ForgotEvent.Recover -> {
                resetPassword()
            }

            ForgotEvent.Login -> {
                state = state.copy(
                    logIn = true
                )
            }
        }
    }

    private fun resetPassword() {
        state = state.copy(emailError = null)

        if (!forgotUseCases.validateEmailUseCase(state.email)) {
            state = state.copy(
                emailError = context.getString(R.string.invalid_email)
            )
        }

        if (state.emailError == null) {
            state = state.copy(isLoading = true)
            viewModelScope.launch {
                forgotUseCases.resetPasswordUseCase(email = state.email).onSuccess {
                    state = state.copy(recover = true)
                }.onFailure {
                    state = state.copy(emailError = it.message)
                }
                state = state.copy(isLoading = false)
            }
        }
    }
}