package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import java.util.ArrayList

class Grade : Identified {

    override lateinit var id: String

    var minValue: Int = 3

    var maxValue: Int = 5

    var gradesMap: Map<Int, String> = HashMap()
}