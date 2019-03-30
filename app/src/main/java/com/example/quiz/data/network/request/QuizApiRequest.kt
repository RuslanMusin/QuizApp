package com.example.quiz.data.network.request

import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.Lector
import com.example.quiz.presentation.model.user.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface QuizApiRequest {

    @POST("auth/login/")
    fun login(@Body user: User): Single<LoginResult>

    @POST("auth/logout/")
    fun logout(): Completable

    @POST("auth/register/")
    fun signUp(@Body user: User): Single<Lector>

    @GET("curators/{curator_id}")
    fun getCurator(@Path(value = "curator_id") id: String): Single<User>

    @GET("curators")
    fun getAllCurators(): Single<List<User>>

    @PUT("curators/{curator_id}")
    fun updateCurator(@Path(value = "curator_id") id: String, @Body curator: User): Single<User>
}