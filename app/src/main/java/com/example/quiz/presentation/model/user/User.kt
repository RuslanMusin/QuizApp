package com.example.quiz.presentation.model.user

import com.example.quiz.presentation.model.common.Identified
import com.google.gson.annotations.SerializedName

open class User {

    var id: Int = -1
    lateinit var email: String
    lateinit var password: String
    lateinit var username: String
    @SerializedName("first_name")
    lateinit var name: String
    @SerializedName("last_name")
    lateinit var lastname: String

    constructor() {}

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
        this.username = email
    }

    fun getFullName(): String {
        return "$lastname $name"
    }
}