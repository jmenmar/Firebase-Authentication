package com.jmenmar.firebaseauthentication.data.di

import com.jmenmar.firebaseauthentication.data.repository.AuthRepositoryImpl
import com.jmenmar.firebaseauthentication.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {

    @Singleton
    @Binds
    abstract fun authRepositoryImpl(authRepositoryImpl: AuthRepositoryImpl): AuthRepository
}