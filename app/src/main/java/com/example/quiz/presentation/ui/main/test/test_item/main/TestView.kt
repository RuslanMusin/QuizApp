package com.example.quiz.presentation.ui.main.test.test_item.main

import com.example.quiz.presentation.base.BaseView

interface TestView: BaseView {

    fun setData()

    fun afterTestOpened()

    fun afterTestClosed()

}