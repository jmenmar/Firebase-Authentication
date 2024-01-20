package com.jmenmar.firebaseauthentication.domain.model

sealed class AuthResponse<out T> {
    data class Success<T>(val data: T): AuthResponse<T>()
    data class Error(val errorMessage: String): AuthResponse<Nothing>()
}