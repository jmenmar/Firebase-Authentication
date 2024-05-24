package com.jmenmar.firebaseauthentication.domain.usecase

import com.jmenmar.firebaseauthentication.domain.matcher.EmailMatcher

class ValidateEmailUseCase(private val emailMatcher: EmailMatcher) {
    operator fun invoke(email: String): Boolean {
        return emailMatcher.isValid(email)
    }
}