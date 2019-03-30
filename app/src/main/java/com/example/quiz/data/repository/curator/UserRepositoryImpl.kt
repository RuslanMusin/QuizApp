package com.example.quiz.data.repository.curator

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.user.Lector
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor() : UserRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override lateinit var currentCurator: User

    override fun findById(id: String): Single<User> {
        return apiRequest
                .getCurator(id)
    }

    override fun findAll(): Single<List<User>> {
        return apiRequest
                .getAllCurators()
    }

    override fun update(id: String, curator: User): Single<User> {
        return apiRequest
                .updateCurator(id, curator)
    }

    override fun createUser(user: User): Single<Lector> {
        return apiRequest
                .signUp(user)
    }


}