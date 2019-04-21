package com.example.quiz.presentation.model.result

import com.google.gson.annotations.SerializedName

class AuthorAnswer {
    @field:SerializedName("id")
    val id: Int? = null
    @field:SerializedName("content")
    val content: String? = null
    @field:SerializedName("is_right")
    val isRight: Boolean? = null
    @field:SerializedName("choices_number")
    val choicesNumber: Int? = null
}