package com.example.quiz.presentation.model.common

import com.google.gson.annotations.Expose


class APIError {

//    private val statusCode: Int = 0
    @Expose
    lateinit var error: String

}