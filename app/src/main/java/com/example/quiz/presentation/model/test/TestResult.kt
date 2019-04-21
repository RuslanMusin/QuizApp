package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.example.quiz.presentation.model.user.User
import com.google.gson.annotations.SerializedName
import java.util.*

class TestResult : Identified {

    override var id: Int = -1

    var name: String? = null

    var description: String? = null
    @SerializedName("date_creation")
    var dateCreation: Date? = null
    @SerializedName("date_open")
    var dateOpen: Date? = null
    @SerializedName("date_close")
    var dateClose: Date? = null

    var owner: User? = null
    var participant: User? = null

    var questions: MutableList<QuestionResult> = ArrayList()
    @SerializedName("questions_feedback")
    var feedbackQuestions: MutableList<QuestionResult> = ArrayList()

    @SerializedName("questions_number")
    var questionsNumber: Int = 0
    @SerializedName("right_answers_number")
    var rightAnswersNumber: Int = 0

    lateinit var rightQuestions: List<QuestionResult>
    lateinit var wrongQuestions: List<QuestionResult>

}
