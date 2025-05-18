package com.farukayata.t_vac_kotlin.di

import com.farukayata.t_vac_kotlin.domain.repository.AuthenticationRepository
import com.farukayata.t_vac_kotlin.domain.repository.AuthenticationRepositoryImpl
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthenticationModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}