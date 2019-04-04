package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import java.util.ArrayList

class Test : Identified {

    override lateinit var id: String

    var name: String? = null

    var description: String? = null

    var questions: MutableList<Question> = ArrayList()

}
