package com.example.quiz.data.repository.curator

import com.example.quiz.presentation.model.user.Lector
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import retrofit2.adapter.rxjava2.Result

interface UserRepository {

    var currentCurator: User

    //CRUD
    fun findById(id: String): Single<User>

    fun findAll(): Single<List<User>>

    fun update(id: String, curator: User): Single<User>

    fun createUser(user: User): Single<Lector>
}