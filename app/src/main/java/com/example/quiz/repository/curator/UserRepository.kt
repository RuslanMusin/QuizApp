package com.example.quiz.repository.curator

import com.example.quiz.model.user.Lector
import com.example.quiz.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface UserRepository {

    var currentCurator: User

    //CRUD
    fun findById(id: String): Single<Result<User>>

    fun findAll(): Single<Result<List<User>>>

    fun update(id: String, curator: User): Single<Result<User>>

    fun createUser(user: User): Single<Result<Lector>>
}