package com.example.quiz.data.network.request

import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthApiRequest {

    @POST("auth/login/")
    fun login(@Body user: User): Single<LoginResult>

    @POST("auth/register/")
    fun signUp(@Body user: User): Single<User>

}