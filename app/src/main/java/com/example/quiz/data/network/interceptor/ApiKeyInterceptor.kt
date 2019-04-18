package com.example.quiz.data.network.interceptor

import com.example.quiz.presentation.util.Const.AUTHORIZATION
import com.example.quiz.presentation.util.Const.TOKEN
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(
            original.newBuilder()
                .addHeader(AUTHORIZATION, TOKEN)
                .build()
        )
    }
}