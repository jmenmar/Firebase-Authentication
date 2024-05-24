package com.jmenmar.firebaseauthentication.ui.screen.login

data class LoginState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val googleError: String? = null,
    val isLoggedIn: Boolean = false,
    val isLoading: Boolean = false
)
