package com.example.quiz.presentation.ui.test.test_item.feedback

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.ui.test.test_item.question.QuestionView
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class FeedbackPresenter @Inject constructor() : BasePresenter<FeedbackView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun onTestClick(args: Bundle) {
        router.newRootChain(Screens.TestScreen(args))
    }

    fun onFinishClick() {
//        router.newRootChain(Screens.FinishScreen(args))
        router.newRootChain(Screens.TestListScreen())
    }

    fun onNextQuestionClick(args: Bundle) {
        router.replaceScreen(Screens.QuestionScreen(args))
    }

    fun postTestResult(testId: Int, testResult: TestResult) {
        testRepository
            .postTestResult(testId, testResult)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.showSnackBar(R.string.wait_test_result)
                onFinishClick()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }


}