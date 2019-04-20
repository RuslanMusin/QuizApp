package com.example.quiz.presentation.util

import com.example.quiz.presentation.model.user.User
import com.google.gson.Gson

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
    var TOKEN = "Token "

    const val TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'"

}