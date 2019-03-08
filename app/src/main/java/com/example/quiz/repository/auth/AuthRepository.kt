package com.example.quiz.repository.auth

import com.example.quiz.model.auth.LoginResult
import com.example.quiz.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface AuthRepository {

    fun login(user: User): Single<Result<LoginResult>>
    fun logout(): Single<Result<Void>>
}