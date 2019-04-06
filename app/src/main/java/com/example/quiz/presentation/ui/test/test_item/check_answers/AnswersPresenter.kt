package com.example.quiz.presentation.ui.test.test_item.check_answers

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AnswersPresenter @Inject constructor() : BasePresenter<AnswersView>() {

    @Inject
    lateinit var router: Router

    fun onBeforeAnswerClick(args: Bundle) {
        router.replaceScreen(Screens.AnswersScreen(args))
    }

    fun onFinishClick(args: Bundle) {
        router.newRootChain(Screens.FinishScreen(args))
    }

    fun onNextAnswerClick(args: Bundle) {
        router.replaceScreen(Screens.AnswersScreen(args))
    }

}