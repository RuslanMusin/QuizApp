package com.example.quiz.data.repository.auth

import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.User
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface AuthRepository {

    fun login(user: User): Single<Result<LoginResult>>

    fun logout(): Single<JsonObject>
}