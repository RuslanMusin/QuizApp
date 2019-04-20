package com.example.quiz.data.repository.test

import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.google.gson.JsonObject
import io.reactivex.Single

interface TestRepository {

    fun findById(id: Int): Single<Test>

    fun findAll(): Single<List<Test>>

    fun findByUser(userId: Int): Single<List<Test>>

    fun createTest(test: Test): Single<ElementId>

    fun openTest(id: Int): Single<JsonObject>

    fun closeTest(id: Int): Single<JsonObject>

    fun postTestResult(testId: Int, testResult: TestResult): Single<JsonObject>

    fun getTestResult(testId: Int, userId: Int): Single<JsonObject>

    fun getTestResults(id: Int): Single<JsonObject>

}