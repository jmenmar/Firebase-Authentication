package com.jmenmar.firebaseauthentication.domain.usecase

data class LoginUseCases(
    val loginWithEmailUseCase: LoginWithEmailUseCase,
    val validatePasswordUseCase: ValidatePasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase,
    val loginWithGoogleResultUseCase: LoginWithGoogleResultUseCase,
    val loginWithGoogleLauncherUseCase: LoginWithGoogleLauncherUseCase,
    val loginWithGoogleCredential: LoginWithGoogleCredential
)
