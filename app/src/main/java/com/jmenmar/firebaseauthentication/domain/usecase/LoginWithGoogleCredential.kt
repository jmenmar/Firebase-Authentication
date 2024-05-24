package com.jmenmar.firebaseauthentication.domain.usecase

import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseUser
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository

class LoginWithGoogleCredential(private val repository: AuthRepository) {
    suspend operator fun invoke(credential: AuthCredential): Result<FirebaseUser> {
        return repository.signInWithGoogleCredential(credential = credential)
    }
}