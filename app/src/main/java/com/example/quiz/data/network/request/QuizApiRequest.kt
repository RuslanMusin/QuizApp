package com.example.quiz.data.network.request

import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.user.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface QuizApiRequest {

    @POST("auth/logout/")
    fun logout(): Completable

    @POST("auth/register/")
    fun signUp(@Body user: User): Single<User>

    @GET("curators/{curator_id}")
    fun getUser(@Path(value = "curator_id") id: String): Single<User>

    @GET("curators")
    fun getUsers(): Single<List<User>>

    @PUT("curators/{curator_id}")
    fun updateUser(@Path(value = "curator_id") id: String, @Body curator: User): Single<User>

    @GET("test/{test_id}")
    fun getTest(@Path(value = "test_id") id: String): Single<Test>

    @GET("curators")
    fun getTests(): Single<List<Test>>

    @POST("test")
    fun createTest(@Body test: Test): Single<String>

}