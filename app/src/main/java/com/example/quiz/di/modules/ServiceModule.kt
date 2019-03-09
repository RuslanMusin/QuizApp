package com.example.quiz.di.modules

import com.example.quiz.api.services.AuthService
import com.example.quiz.api.services.UserService
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named
import javax.inject.Singleton

@Module(includes = [RetrofitModule::class])
public class ServiceModule {

    @Provides
    @Singleton
    fun provideAuthService(
        @Named("auth") retrofit: Retrofit
    ): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideCuratorService(
        @Named("auth") retrofit: Retrofit
    ): UserService {
        return retrofit.create(UserService::class.java)
    }

}
