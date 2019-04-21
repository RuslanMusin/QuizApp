package com.example.quiz.presentation.model.result

import com.google.gson.annotations.SerializedName

class AuthorQuestion {
    @field:SerializedName("id")
    val id: Int? = null
    @field:SerializedName("description")
    val description: String? = null
    @field:SerializedName("type")
    val type: String? = null
    @field:SerializedName("answers")
    val answers: List<AuthorAnswer>? = null
    @field:SerializedName("answers_number")
    val tryAnswerNumber: Int? = null
    @field:SerializedName("right_answers_number")
    val rightAnswerNumber: Int? = null
}