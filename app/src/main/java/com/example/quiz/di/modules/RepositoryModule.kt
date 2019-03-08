package com.example.quiz.di.modules

import com.example.quiz.repository.auth.AuthRepository
import com.example.quiz.repository.auth.AuthRepositoryImpl
import com.example.quiz.repository.curator.UserRepository
import com.example.quiz.repository.curator.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module(includes = [ServiceModule::class])
interface RepositoryModule {

    @Singleton
    @Binds
    fun curatorRepository(repository: UserRepositoryImpl): UserRepository

    @Singleton
    @Binds
    fun authRepository(repository: AuthRepositoryImpl): AuthRepository

}