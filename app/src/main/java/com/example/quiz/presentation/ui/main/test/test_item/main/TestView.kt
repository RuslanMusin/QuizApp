package com.example.quiz.presentation.ui.main.test.test_item.main

import com.example.quiz.presentation.base.BaseView
import com.example.quiz.presentation.model.test.Test

interface TestView: BaseView {

    fun setData(test: Test)

    fun afterTestOpened()

    fun afterTestClosed()

}