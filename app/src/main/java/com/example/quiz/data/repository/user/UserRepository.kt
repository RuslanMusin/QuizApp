package com.example.quiz.data.repository.user

import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface UserRepository {

    fun findUser(): Single<User>

    fun createUser(user: User): Single<Result<User>>
}