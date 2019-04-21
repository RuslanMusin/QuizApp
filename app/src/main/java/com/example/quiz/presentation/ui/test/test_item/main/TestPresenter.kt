package com.example.quiz.presentation.ui.test.test_item.main

import android.os.Bundle
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Question
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TEST_ITEM
import com.example.quiz.presentation.util.Const.gson
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TestPresenter @Inject constructor() : BasePresenter<TestView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun onTestListClick() {
        router.newRootChain(Screens.TestListScreen())
    }

    fun onQuestionClick(args: Bundle) {
        router.newChain(Screens.QuestionScreen(args))
    }

    fun onResultClick(args: Bundle) {
        router.newRootChain(Screens.FinishScreen(args))
    }

    fun findTest(testId: Int) {
        testRepository
            .findById(testId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .subscribe({
                it.questions.sortBy { it.id }
                viewState.setData(it)
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun showResult(testId: Int, userId: Int) {
        testRepository
            .getTestResult(testId, userId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .subscribe({
                Log.d(TAG_LOG, "test result = \n${gson.toJson(it)}")
                it.questions.sortBy { it.id }
                val args = Bundle()
                args.putString(TEST_ITEM, gson.toJson(it))
                onResultClick(args)
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun showResultOverview(testId: Int) {
        testRepository
            .getTestResults(testId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                Log.d(TAG_LOG, "result overview\n$it")
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun openTest(id: Int) {
        testRepository
            .openTest(id)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.afterTestOpened()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun closeTest(id: Int) {
        testRepository
            .closeTest(id)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.afterTestClosed()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()    }

    fun onAnswersClick(args: Bundle) {
        router.navigateTo(Screens.AnswersScreen(args))
    }

}