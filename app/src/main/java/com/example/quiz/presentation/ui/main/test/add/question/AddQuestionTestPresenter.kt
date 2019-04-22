package com.example.quiz.presentation.ui.main.test.add.question

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.gson
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
        val testStr = gson.toJson(test)
        Log.d(TAG_LOG, "test json = \n$testStr")
        testRepository
            .createTest(test)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                test.id = it.id
                Log.d(TAG_LOG, "id = ${test.id}")
                viewState.navigateToTest(test.id)
            }, {
                Log.d(TAG_LOG,"error process")
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun onTestListClick() {
        router.navigateTo(Screens.TestListScreen())
    }

    fun onBeforeQuestionClick(args: Bundle, toQuestion: Boolean) {
        if(toQuestion) {
            router.replaceScreen(Screens.AddQuestionScreen(args))
        } else {
            router.replaceScreen(Screens.AddTestScreen(args))
        }
    }

    fun onNextQuestionClick(args: Bundle) {
        router.replaceScreen(Screens.AddQuestionScreen(args))
    }

    fun onTestClick(args: Bundle) {
        router.navigateTo(Screens.TestScreen(args))
    }

}