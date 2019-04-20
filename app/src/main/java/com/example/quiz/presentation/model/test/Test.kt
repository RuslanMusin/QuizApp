package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.example.quiz.presentation.model.user.User
import com.google.gson.annotations.SerializedName
import java.util.*
import kotlin.collections.ArrayList

class Test : Identified {

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

    var questions: MutableList<Question> = ArrayList()
    @SerializedName("questions_feedback")
    var feedbackQuestions: MutableList<Question> = ArrayList()
    lateinit var rightQuestions: List<Question>

    lateinit var wrongQuestions: List<Question>

}
