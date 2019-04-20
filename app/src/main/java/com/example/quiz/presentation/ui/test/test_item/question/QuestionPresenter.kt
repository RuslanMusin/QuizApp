package com.example.quiz.presentation.ui.test.test_item.question

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class QuestionPresenter @Inject constructor() : BasePresenter<QuestionView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun onTestClick(args: Bundle) {
        router.newRootChain(Screens.TestScreen(args))
    }

    fun onFinishClick(args: Bundle) {
        router.newRootChain(Screens.FinishScreen(args))
    }

    fun onNextQuestionClick(args: Bundle) {
        router.replaceScreen(Screens.QuestionScreen(args))
    }

    fun onBeforeFeedbackClick(args: Bundle) {
        router.replaceScreen(Screens.BeforeFeedbackScreen(args))
    }

}