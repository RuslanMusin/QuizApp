package com.example.quiz.presentation.util

import com.example.quiz.presentation.model.user.User
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

object Const {

    const val TAG_LOG = "TAG_LOG"
    const val TEST_PREFS = "TESTS_PREFS"
    const val TESTS = "tests"
    const val TEST_USER = "test_user"

    //items
    const val USER_ITEM = "USER_ITEM"
    const val TEST_ITEM = "TEST_ITEM"

    //args
    const val QUESTION_NUMBER = "queston_number"
    const val RIGHT_ANSWERS = "right_answers"
    const val WRONG_ANSWERS = "wrong_answers"
    const val ANSWERS_TYPE = "type_answers"

    //TestType
    const val TEST_ONE_TYPE = "one"
    const val TEST_MANY_TYPE = "many"
    const val TEST_TEXT_TYPE = "text"

    val gson = Gson()

    lateinit var currentUser: User

    //Http request
    const val AUTHORIZATION = "Authorization"
    const val ORIGINAL_TOKEN = "Token "
    var TOKEN = ORIGINAL_TOKEN
    const val TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"
    const val SIMPLE_TIME_FORMAT = "dd.MM.yyyy"

    const val MAX_LENGTH = 50
    const val MORE_TEXT = "..."

    fun cutLongDescription(description: String, maxLength: Int): String {
        return if (description.length < maxLength) {
            description
        } else {
            description.substring(0, maxLength - MORE_TEXT.length) + MORE_TEXT
        }
    }

    fun getStringFromDate(date: Date): String {
        val cbDateFormat = SimpleDateFormat(SIMPLE_TIME_FORMAT)
        return cbDateFormat.format(date)
    }

}