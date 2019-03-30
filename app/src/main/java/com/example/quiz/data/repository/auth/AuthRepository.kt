package com.example.quiz.data.repository.auth

import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.User
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface AuthRepository {

    fun login(user: User): Single<LoginResult>

    fun logout(): Completable
}