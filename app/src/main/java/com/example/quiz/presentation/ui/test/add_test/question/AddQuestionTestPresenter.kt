package com.example.quiz.presentation.ui.test.add_test.question

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class AddQuestionTestPresenter @Inject constructor() : BasePresenter<AddQuestionTestView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun createTest(test: Test) {
        testRepository
            .createTest(test)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.navigateToTest()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun onTestListClick() {
        router.navigateTo(Screens.TestListScreen())
    }

    fun onBeforeQuestionClick(args: Bundle) {
        router.navigateTo(Screens.AddQuestionScreen(args))
    }

    fun onNextQuestionClick(args: Bundle) {
        router.navigateTo(Screens.AddQuestionScreen(args))
    }

    fun onTestClick(args: Bundle) {
        router.navigateTo(Screens.TestListScreen())
    }

}