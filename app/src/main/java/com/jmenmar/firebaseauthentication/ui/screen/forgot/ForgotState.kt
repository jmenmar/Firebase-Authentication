package com.jmenmar.firebaseauthentication.ui.screen.forgot

data class ForgotState(
    val email: String = "",
    val emailError: String? = null,
    val recover: Boolean = false,
    val logIn: Boolean = false,
    val isLoading: Boolean = false
)
