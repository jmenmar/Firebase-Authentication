package com.jmenmar.firebaseauthentication.data.di

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.jmenmar.firebaseauthentication.data.repository.AuthRepositoryImpl
import com.jmenmar.firebaseauthentication.data.repository.EmailMatcherImpl
import com.jmenmar.firebaseauthentication.domain.matcher.EmailMatcher
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository
import com.jmenmar.firebaseauthentication.domain.usecase.LoginUseCases
import com.jmenmar.firebaseauthentication.domain.usecase.LoginWithEmailUseCase
import com.jmenmar.firebaseauthentication.domain.usecase.LoginWithGoogleCredential
import com.jmenmar.firebaseauthentication.domain.usecase.LoginWithGoogleLauncherUseCase
import com.jmenmar.firebaseauthentication.domain.usecase.LoginWithGoogleResultUseCase
import com.jmenmar.firebaseauthentication.domain.usecase.ValidateEmailUseCase
import com.jmenmar.firebaseauthentication.domain.usecase.ValidatePasswordUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun authRepositoryImpl(@ApplicationContext context: Context): AuthRepository {
        return AuthRepositoryImpl(context = context)
    }

    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideLoginUseCases(
        repository: AuthRepository,
        emailMatcher: EmailMatcher
    ): LoginUseCases {
        return LoginUseCases(
            loginWithEmailUseCase = LoginWithEmailUseCase(repository),
            validateEmailUseCase = ValidateEmailUseCase(emailMatcher),
            validatePasswordUseCase = ValidatePasswordUseCase(),
            loginWithGoogleLauncherUseCase = LoginWithGoogleLauncherUseCase(repository),
            loginWithGoogleResultUseCase = LoginWithGoogleResultUseCase(repository),
            loginWithGoogleCredential = LoginWithGoogleCredential(repository)
        )
    }

    @Provides
    @Singleton
    fun provideEmailMatcher(): EmailMatcher {
        return EmailMatcherImpl()
    }
}