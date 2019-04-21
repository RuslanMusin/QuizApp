package com.example.quiz.data.network.request

import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.result.AuthorResult
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.model.test.TestSubmit
import com.example.quiz.presentation.model.user.User
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*

interface QuizApiRequest {

    @POST("auth/logout/")
    fun logout(): Completable



    @GET("curators/{curator_id}")
    fun getUser(@Path("curator_id") id: Int): Single<User>

    @GET("curators")
    fun getUsers(): Single<List<User>>

    @PUT("curators/{curator_id}")
    fun updateUser(@Path("curator_id") id: Int, @Body curator: User): Single<User>

    @GET("test/{test_id}")
    fun getTest(@Path("test_id") id: Int): Single<Test>

    @GET("test")
    fun getTests(): Single<List<Test>>

    @GET("test/submission/{user_id}")
    fun getTestsByUser(@Path("user_id") id: Int): Single<List<Test>>

    @POST("test")
    fun createTest(@Body test: Test): Single<ElementId>

    @POST("test/{test_id}/open")
    fun openTest(@Path("test_id") id: Int): Single<JsonObject>

    @POST("test/{test_id}/close")
    fun closeTest(@Path("test_id") id: Int): Single<JsonObject>

    @POST("test/{test_id}/submit")
    fun postTestResult(
            @Path("test_id") id: Int,
            @Body testResult: TestSubmit
    ): Single<TestSubmit>

    @GET("test/{test_id}/result")
    fun getAuthorTestResults(@Path("test_id") id: Int): Single<AuthorResult>

    @GET("test/{test_id}/result/{user_id}")
    fun getParticipantTestResult(
            @Path("test_id") testId: Int,
            @Path("user_id") userId: Int
    ): Single<TestResult>

    @GET("auth/user")
    fun findUser(): Single<User>

}