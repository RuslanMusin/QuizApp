package com.example.quiz.api.interceptors

import com.example.quiz.utils.Const.ACCEPT
import com.example.quiz.utils.Const.APP_JSON
import com.example.quiz.utils.Const.AUTHORIZATION
import com.example.quiz.utils.Const.AUTH_VALUE
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor private constructor() : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val original = chain.request()
        return chain.proceed(original.newBuilder()
                .addHeader(ACCEPT, APP_JSON)
//                .addHeader(AUTHORIZATION, AUTH_VALUE)
                .build())
    }

    companion object {

        fun create(): Interceptor {
            return ApiKeyInterceptor()
        }
    }
}