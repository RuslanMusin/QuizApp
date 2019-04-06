package com.example.quiz.data.repository

import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.auth.AuthRepositoryImpl
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.data.repository.test.TestRepositoryImpl
import com.example.quiz.data.repository.user.UserRepository
import com.example.quiz.data.repository.user.UserRepositoryImpl
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

    @Binds
    @Singleton
    abstract fun testRepository(repositoryImpl: TestRepositoryImpl): TestRepository
}