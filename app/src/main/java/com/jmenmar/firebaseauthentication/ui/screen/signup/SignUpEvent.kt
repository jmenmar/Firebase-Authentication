package com.jmenmar.firebaseauthentication.ui.screen.signup

sealed interface SignUpEvent {
    data class EmailChange(val email: String) : SignUpEvent
    data class PasswordChange(val password: String) : SignUpEvent
    data class RepeatPasswordChange(val repeatPassword: String) : SignUpEvent
    object LogIn : SignUpEvent
    object SignUp : SignUpEvent
}