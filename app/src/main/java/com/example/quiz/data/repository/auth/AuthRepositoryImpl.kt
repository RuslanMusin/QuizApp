package com.example.quiz.data.repository.auth

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.User
import io.reactivex.Completable
import io.reactivex.Single
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor() : AuthRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override fun login(user: User): Single<LoginResult> {
        return apiRequest
                .login(user)
    }

    override fun logout(): Completable {
        return apiRequest
                .logout()
    }
}
