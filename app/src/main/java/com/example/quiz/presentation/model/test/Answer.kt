package com.example.quiz.presentation.model.test

import com.example.quiz.presentation.model.common.Identified
import com.google.gson.annotations.SerializedName

class Answer: Identified {

    override lateinit var id: String
    var content: String? = null
    @SerializedName("is_right")
    var isRight: Boolean = false

    var userClicked: Boolean = false


}
