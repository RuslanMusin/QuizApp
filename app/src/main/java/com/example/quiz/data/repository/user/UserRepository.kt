package com.example.quiz.data.repository.user

import com.example.quiz.presentation.model.user.User
import io.reactivex.Single

interface UserRepository {

    fun findUser(): Single<User>

    fun findById(id: Int): Single<User>

    fun findAll(): Single<List<User>>

    fun update(id: Int, curator: User): Single<User>

    fun createUser(user: User): Single<User>
}