package com.jmenmar.firebaseauthentication.domain.usecase

import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.tasks.Task
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository

class LoginWithGoogleResultUseCase(private val repository: AuthRepository) {
    operator fun invoke(task: Task<GoogleSignInAccount>): Result<GoogleSignInAccount> {
        return repository.signInWithGoogleResult(task = task)
    }
}