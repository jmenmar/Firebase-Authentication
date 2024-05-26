package com.jmenmar.firebaseauthentication.domain.usecase

data class ForgotUseCases(
    val resetPasswordUseCase: ResetPasswordUseCase,
    val validateEmailUseCase: ValidateEmailUseCase
)
