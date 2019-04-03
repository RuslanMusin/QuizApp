package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import java.util.ArrayList

class Question : Identified {

    override lateinit var id: String

    var description: String? = null

    var answers: MutableList<String> = ArrayList()

    var rightAnswers: MutableList<String> = ArrayList()

    var weight: Int = 0

}
