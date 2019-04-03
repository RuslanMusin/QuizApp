package com.example.quiz.presentation.ui.test.add_test.question

import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import javax.inject.Inject

@InjectViewState
class AddQuestionTestPresenter: BasePresenter<AddQuestionTestView>() {

    @Inject
    lateinit var testRepositoryImpl: TestRepository

    fun createTest(test: Test) {
        AppHelper.currentUser?.let {
            testRepositoryImpl
                .createTest(test, it)
                .subscribe { e -> viewState.navigateToTest() }
        }
    }

}