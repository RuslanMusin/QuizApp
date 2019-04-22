package com.example.quiz.data.repository.user

import com.example.quiz.data.network.request.AuthApiRequest
import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class UserRepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest
    @Inject
    lateinit var authRequest: AuthApiRequest

    override fun findUser(): Single<User> {
        return apiRequest
            .findUser()
    }

    override fun createUser(user: User): Single<User> {
        return authRequest
                .signUp(user)
    }

}