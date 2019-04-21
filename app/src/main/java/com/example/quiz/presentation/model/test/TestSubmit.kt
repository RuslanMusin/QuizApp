package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.example.quiz.presentation.model.user.User
import com.google.gson.annotations.SerializedName
import java.util.*

class TestSubmit {

    var questions: MutableList<Question> = ArrayList()
    @SerializedName("questions_feedback")
    var feedbackQuestions: MutableList<Question> = ArrayList()
}