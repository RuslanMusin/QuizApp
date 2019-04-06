package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.example.quiz.presentation.util.Const.TEST_ONE_TYPE
import java.util.ArrayList

class Question : Identified {

    override lateinit var id: String

    var description: String? = null

    var answers: MutableList<Answer> = ArrayList()

    var type: String = TEST_ONE_TYPE

    var userRight: Boolean = false

    lateinit var userAnswer: String

}
