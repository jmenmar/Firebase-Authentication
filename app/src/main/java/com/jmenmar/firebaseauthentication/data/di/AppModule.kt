package com.jmenmar.firebaseauthentication.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

//    @Singleton
//    @Provides
//    fun provideFirebaseService(reference: DatabaseReference) = FirebaseMarketService(reference)

    //AuthRepositoryImpl
    @Singleton
    @Provides
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    //AuthRepo
//    @Singleton
//    @Provides
//    fun provideFirebaseService(reference: DatabaseReference) = FirebaseMarketService(reference)

}