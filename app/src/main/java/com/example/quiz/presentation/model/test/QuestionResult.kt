package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.example.quiz.presentation.util.Const
import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class QuestionResult : Identified {

    override var id: Int = -1

    var description: String? = null

    var answers: MutableList<Answer> = ArrayList()
    @SerializedName("participant_answers")
    var participantAnswers: MutableList<Answer> = ArrayList()

    var type: String = Const.TEST_ONE_TYPE

    var userRight: Boolean = false

    lateinit var userAnswer: String

}