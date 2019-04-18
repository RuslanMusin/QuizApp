package com.example.quiz.presentation.model.test

import com.google.gson.annotations.SerializedName

class Answer {

    var content: String? = null
    @SerializedName("is_right")
    var isRight: Boolean = false

    var userClicked: Boolean = false


}
