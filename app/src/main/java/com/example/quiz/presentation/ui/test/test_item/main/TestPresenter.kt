package com.example.quiz.presentation.ui.test.test_item.main

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TestPresenter @Inject constructor() : BasePresenter<TestView>() {

    @Inject
    lateinit var router: Router

    fun onTestListClick() {
        router.newRootChain(Screens.TestListScreen())
    }

    fun onQuestionClick(args: Bundle) {
        router.newChain(Screens.QuestionScreen(args))
    }

}