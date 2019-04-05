package com.example.quiz.presentation.ui.test.test_item.question

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class QuestionPresenter @Inject constructor() : BasePresenter<QuestionView>() {

    @Inject
    lateinit var router: Router

    fun onTestClick(args: Bundle) {
        router.newRootChain(Screens.TestScreen(args))
    }

    fun onFinishClick(args: Bundle) {
        router.newRootChain(Screens.FinishScreen(args))
    }

    fun onNextQuestionClick(args: Bundle) {
        router.replaceScreen(Screens.QuestionScreen(args))
    }


}