package com.example.quiz.data.repository

import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.auth.AuthRepositoryImpl
import com.example.quiz.data.repository.curator.UserRepository
import com.example.quiz.data.repository.curator.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class RepositoryBinder {

    @Binds
    @Singleton
    abstract fun userRepository(repositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    @Singleton
    abstract fun authRepository(repositoryImpl: AuthRepositoryImpl): AuthRepository
}