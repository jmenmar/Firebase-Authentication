package com.jmenmar.firebaseauthentication.ui.screen.login

sealed interface LoginEvent {
    data class EmailChange(val email: String) : LoginEvent
    data class PasswordChange(val password: String) : LoginEvent
    data class Login(val google: Boolean? = false) : LoginEvent
}