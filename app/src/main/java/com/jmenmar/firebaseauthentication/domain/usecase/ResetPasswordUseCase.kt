package com.jmenmar.firebaseauthentication.domain.usecase

import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository

class ResetPasswordUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(email: String): Result<Unit> {
        return repository.resetPassword(email = email)
    }
}