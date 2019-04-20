package com.example.quiz.data.repository.test

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.model.test.TestSubmit
import com.google.gson.JsonObject
import io.reactivex.Single
import javax.inject.Inject

class TestRepositoryImpl @Inject constructor() : TestRepository {

    @Inject
    lateinit var apiRequest: QuizApiRequest

    override fun findById(id: Int): Single<Test> {
        return apiRequest
            .getTest(id)
    }

    override fun findAll(): Single<List<Test>> {
        return apiRequest
            .getTests()
    }

    override fun findByUser(userId: Int): Single<List<Test>> {
        return apiRequest
            .getTestsByUser(userId)
    }

    override fun createTest(test: Test): Single<ElementId> {
        return apiRequest
            .createTest(test)
    }

    override fun openTest(id: Int): Single<JsonObject> {
        return apiRequest
            .openTest(id)
    }

    override fun closeTest(id: Int): Single<JsonObject> {
        return apiRequest
            .closeTest(id)
    }

    override fun postTestResult(testId: Int, testSubmit: TestSubmit): Single<JsonObject> {
        return apiRequest
            .postTestResult(testId, testSubmit)
    }

    override fun getTestResult(testId: Int, userId: Int): Single<TestResult> {
        return apiRequest
            .getTestResult(testId, userId)
    }

    override fun getTestResults(id: Int): Single<JsonObject> {
        return apiRequest
            .getTestResults(id)
    }

}