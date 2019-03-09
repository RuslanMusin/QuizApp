package com.example.quiz.di.modules

import com.example.quiz.api.interceptors.ApiKeyInterceptor
import com.example.quiz.api.interceptors.ErrorHandlingInterceptor
import dagger.Module
import okhttp3.logging.HttpLoggingInterceptor
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import javax.inject.Named
import javax.inject.Singleton


@Module
class OkHttpClientModule {

    @Provides
    @Singleton
    @Named("auth")
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
    }

    @Provides
    @Singleton
    @Named("main")
    fun provideMainHttpClient(@Named("auth") okHttpClient: OkHttpClient): OkHttpClient {
        return okHttpClient
            .newBuilder()
            .addInterceptor(ApiKeyInterceptor.create())
//            .addInterceptor(ErrorHandlingInterceptor.create())
            .build()
    }
}