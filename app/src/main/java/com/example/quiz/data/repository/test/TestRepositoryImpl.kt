package com.example.quiz.data.repository.test

import com.example.quiz.data.network.request.QuizApiRequest
import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.result.AuthorResult
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.google.gson.Gson
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

    override fun getParticipantTestResult(testId: Int, userId: Int): Single<JsonObject> {
        return apiRequest
                .getParticipantTestResult(testId, userId)
    }

    override fun getAuthorTestResults(id: Int): Single<AuthorResult> {
        /*return apiRequest
            .getAuthorTestResults(id)*/

        val response = "{\n" +
                "  \"id\": 6,\n" +
                "  \"name\": \"test\",\n" +
                "  \"description\": \"test\",\n" +
                "  \"date_creation\": \"2019-04-17T09:18:59.615830Z\",\n" +
                "  \"date_open\": \"2019-04-17T09:19:49.159022Z\",\n" +
                "  \"date_close\": \"2019-04-17T09:50:37.604599Z\",\n" +
                "  \"owner\": {\n" +
                "    \"id\": 1,\n" +
                "    \"first_name\": \"test\",\n" +
                "    \"last_name\": \"test\",\n" +
                "    \"email\": \"test@test.com\"\n" +
                "  },\n" +
                "  \"questions\": [\n" +
                "    {\n" +
                "      \"id\": 15,\n" +
                "      \"description\": \"q3\",\n" +
                "      \"type\": \"text\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 35,\n" +
                "          \"content\": \"q3-a1\",\n" +
                "          \"is_right\": true,\n" +
                "          \"choices_number\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1,\n" +
                "      \"right_answers_number\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 14,\n" +
                "      \"description\": \"q2\",\n" +
                "      \"type\": \"many\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 32,\n" +
                "          \"content\": \"q2-a1\",\n" +
                "          \"is_right\": true,\n" +
                "          \"choices_number\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 33,\n" +
                "          \"content\": \"q2-a2\",\n" +
                "          \"is_right\": true,\n" +
                "          \"choices_number\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 34,\n" +
                "          \"content\": \"q2-a3\",\n" +
                "          \"is_right\": false,\n" +
                "          \"choices_number\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1,\n" +
                "      \"right_answers_number\": 0\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 13,\n" +
                "      \"description\": \"q1\",\n" +
                "      \"type\": \"one\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 29,\n" +
                "          \"content\": \"q1-a1\",\n" +
                "          \"is_right\": true,\n" +
                "          \"choices_number\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 30,\n" +
                "          \"content\": \"q1-a2\",\n" +
                "          \"is_right\": false,\n" +
                "          \"choices_number\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 31,\n" +
                "          \"content\": \"q1-a3\",\n" +
                "          \"is_right\": false,\n" +
                "          \"choices_number\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1,\n" +
                "      \"right_answers_number\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"questions_feedback\": [\n" +
                "    {\n" +
                "      \"id\": 4,\n" +
                "      \"description\": \"Do you like it?\",\n" +
                "      \"type\": \"one\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 6,\n" +
                "          \"content\": \"Yes, I do.\",\n" +
                "          \"choices_number\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 7,\n" +
                "          \"content\": \"No, I don't.\",\n" +
                "          \"choices_number\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 5,\n" +
                "      \"description\": \"Select good points about action.\",\n" +
                "      \"type\": \"many\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 8,\n" +
                "          \"content\": \"Humor\",\n" +
                "          \"choices_number\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 9,\n" +
                "          \"content\": \"Competence\",\n" +
                "          \"choices_number\": 1\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 10,\n" +
                "          \"content\": \"Oratory\",\n" +
                "          \"choices_number\": 0\n" +
                "        },\n" +
                "        {\n" +
                "          \"id\": 11,\n" +
                "          \"content\": \"Interaction with audience\",\n" +
                "          \"choices_number\": 0\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1\n" +
                "    },\n" +
                "    {\n" +
                "      \"id\": 6,\n" +
                "      \"description\": \"Describe your impression about all in one word.\",\n" +
                "      \"type\": \"text\",\n" +
                "      \"answers\": [\n" +
                "        {\n" +
                "          \"id\": 12,\n" +
                "          \"content\": \"hello, world\",\n" +
                "          \"choices_number\": 1\n" +
                "        }\n" +
                "      ],\n" +
                "      \"answers_number\": 1\n" +
                "    }\n" +
                "  ],\n" +
                "  \"questions_number\": 3,\n" +
                "  \"participants_number\": 1\n" +
                "}"

        val gson = Gson().fromJson(response, AuthorResult::class.java)
        return Single.just(gson)
    }

}