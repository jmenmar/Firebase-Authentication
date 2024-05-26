package com.jmenmar.firebaseauthentication.ui.screen.forgot

sealed interface ForgotEvent {
    data class EmailChange(val email: String) : ForgotEvent
    object Recover: ForgotEvent
    object Login: ForgotEvent
}