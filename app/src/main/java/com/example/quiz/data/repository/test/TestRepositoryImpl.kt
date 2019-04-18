package com.example.quiz.data.repository.test

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.test.Test
import io.reactivex.Single
import javax.inject.Inject
import javax.inject.Named

class TestRepositoryImpl @Inject constructor() : TestRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override fun findByName(name: String): Single<Test> {
        return apiRequest
            .getTest(name)
    }

    override fun findAll(): Single<List<Test>> {
        return apiRequest
            .getTests()
    }

    override fun findByUser(userId: String): Single<List<Test>> {
        return apiRequest
            .getTests()
    }

    override fun createTest(test: Test): Single<String> {
        return apiRequest
            .createTest(test)
    }

}