package com.example.quiz.data.repository.auth

import com.example.quiz.data.network.request.AuthApiRequest
import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    @Inject
    lateinit var authApiRequest: AuthApiRequest

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override fun login(user: User): Single<LoginResult> {
        return authApiRequest
                .login(user)
    }

    override fun logout(): Completable {
        return apiRequest
                .logout()
    }
}
