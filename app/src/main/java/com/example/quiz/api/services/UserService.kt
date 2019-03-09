package com.example.quiz.api.services

import com.example.quiz.model.user.Lector
import com.example.quiz.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result
import retrofit2.http.*

interface UserService {

    @POST("auth/logout/")
    fun logout(): Single<Result<Void>>

    @GET("curators/{curator_id}")
    fun findById(
        @Path(value = "curator_id") id: String
    ): Single<Result<User>>

    @GET("curators")
    fun findAll(): Single<Result<List<User>>>

    @PUT("curators/{curator_id}")
    fun update(
        @Path(value = "curator_id") id: String,
        @Body curator: User
    ): Single<Result<User>>

    @POST("auth/register/")
    fun create(
        @Body user: User
    ): Single<Result<Lector>>

}