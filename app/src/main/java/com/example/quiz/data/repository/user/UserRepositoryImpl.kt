package com.example.quiz.data.repository.user

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override fun findById(id: String): Single<User> {
        return apiRequest
                .getUser(id)
    }

    override fun findAll(): Single<List<User>> {
        return apiRequest
                .getUsers()
    }

    override fun update(id: String, curator: User): Single<User> {
        return apiRequest
                .updateUser(id, curator)
    }

    override fun createUser(user: User): Single<User> {
        return apiRequest
                .signUp(user)
    }

}