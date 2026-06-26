package com.tpc.nudj.di

import com.tpc.nudj.repository.auth.AuthRepository
import com.tpc.nudj.repository.auth.FirebaseAuthRepository
import com.tpc.nudj.repository.user.UserRepository
import com.tpc.nudj.repository.user.UserRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {

    @Provides
    @Singleton
    fun provideAuthRepository(): AuthRepository = FirebaseAuthRepository()

    @Provides
    @Singleton
    fun provideUserRepository(): UserRepository = UserRepositoryImpl()
}