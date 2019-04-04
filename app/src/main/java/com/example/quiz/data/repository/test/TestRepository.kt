package com.example.quiz.data.repository.test

import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.user.User
import io.reactivex.Single

interface TestRepository {

    fun findByName(name: String): Single<Test>

    fun findAll(): Single<List<Test>>

    fun findByUser(userId: String): Single<List<Test>>

    fun createTest(test: Test): Single<Test>
}