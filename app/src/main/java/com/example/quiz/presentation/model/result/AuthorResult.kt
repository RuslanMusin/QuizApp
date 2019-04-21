package com.example.quiz.presentation.model.result

import com.google.gson.annotations.SerializedName

class AuthorResult {
    @field:SerializedName("id")
    val id: Int? = null
    @field:SerializedName("name")
    val name: String? = null
    @field:SerializedName("description")
    val description: String? = null
    @field:SerializedName("date_creation")
    val createDate: String? = null
    @field:SerializedName("date_close")
    val closeDate: String? = null
    @field:SerializedName("questions")
    val questions: List<AuthorQuestion>? = null
    @field:SerializedName("questions_feedback")
    val feedbacks: List<AuthorQuestion>? = null
    @field:SerializedName("questions_number")
    val questionsNumber: Int? = null
    @field:SerializedName("participants_number")
    val participantsNumber: Int? = null
}