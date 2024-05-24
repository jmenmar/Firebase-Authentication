package com.jmenmar.firebaseauthentication.ui.util

import com.jmenmar.firebaseauthentication.domain.usecase.PasswordResult

object PasswordErrorParser {
    fun parseError(error: PasswordResult): String? {
        return when (error) {
            PasswordResult.VALID -> null
            PasswordResult.INVALID_LOWERCASE -> "Password must have at least 1 lowercase character"
            PasswordResult.INVALID_UPPERCASE -> "Password must have at least 1 uppercase character"
            PasswordResult.INVALID_DIGITS -> "Password must have at least 1 digit"
            PasswordResult.INVALID_LENGTH -> "Password must have at least 8 characters"
        }
    }
}