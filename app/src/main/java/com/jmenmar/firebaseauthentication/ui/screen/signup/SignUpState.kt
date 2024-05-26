package com.jmenmar.firebaseauthentication.ui.screen.signup

data class SignUpState(
    val email: String = "",
    val emailError: String? = null,
    val password: String = "",
    val repeatPassword: String = "",
    val passwordError: String? = null,
    val isSignedIn: Boolean = false,
    val isLoading: Boolean = false,
    val logIn: Boolean = false
)
