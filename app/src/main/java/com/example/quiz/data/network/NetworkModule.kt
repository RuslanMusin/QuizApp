package com.example.quiz.data.network

import com.example.quiz.data.network.interceptor.ApiKeyInterceptor
import com.example.quiz.data.network.request.AuthApiRequest
import com.example.quiz.data.network.request.AuthRequestDecorator
import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.data.network.request.QuizApiRequestDecorator
import com.example.quiz.presentation.util.Const.TIME_FORMAT
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
class NetworkModule {

    companion object {
        private const val BASE_URL = "https://quiez-api.herokuapp.com/api/"
    }

    @Singleton
    @Provides
    fun httpLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Singleton
    @Provides
    fun apiInterceptor(): ApiKeyInterceptor {
        return ApiKeyInterceptor()
    }

    @Singleton
    @Provides
    @Named("AuthClient")
    fun provideAuthHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .writeTimeout(15, TimeUnit.SECONDS)
            .readTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    @Named("MainClient")
    fun provideMainHttpClient(@Named("AuthClient") okHttpClient: OkHttpClient,
                          apiKeyInterceptor: ApiKeyInterceptor): OkHttpClient {
        return okHttpClient.newBuilder()
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    @Named("AuthRetrofit")
    fun provideAuthRetrofit(@Named("AuthClient") okHttpClient: OkHttpClient): Retrofit {
        val gson = GsonBuilder().setDateFormat(TIME_FORMAT).create()
        return Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    @Named("MainRetrofit")
    fun provideMainRetrofit(@Named("AuthRetrofit") retrofit: Retrofit,
                        @Named("MainClient") okHttpClient: OkHttpClient): Retrofit {
        return retrofit.newBuilder()
            .client(okHttpClient)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthApi(@Named("AuthRetrofit") retrofit: Retrofit): AuthApiRequest {
        val service = retrofit.create(AuthApiRequest::class.java)
        return AuthRequestDecorator(service)
    }

    @Singleton
    @Provides
    fun provideQuizApi(@Named("MainRetrofit") retrofit: Retrofit): QuizApiRequest {
        val service = retrofit.create(QuizApiRequest::class.java)
        return QuizApiRequestDecorator(service)
    }
}