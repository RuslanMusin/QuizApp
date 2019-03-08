package com.example.quiz.model.user

import com.example.quiz.model.common.Identified
import com.google.gson.annotations.SerializedName

abstract class User: Identified {

    override lateinit var id: String
    @SerializedName("username")
    lateinit var email: String
    lateinit var password: String
    lateinit var name: String
    @SerializedName("last_name")
    lateinit var lastname: String
    lateinit var patronymic: String

    constructor() {}

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }

    fun getFullName(): String {
        return "$lastname $name $patronymic"
    }
}