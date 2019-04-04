package com.example.quiz.presentation.ui.test.test_list.tab_fragment.ended_test_list

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.ui.auth.signin.SignInView
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class EndedTestsPresenter @Inject constructor() : BasePresenter<EndedTestsView>() {
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var router: Router

    fun loadTests(userId: String) {
        testRepository
            .findByUser(userId)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                viewState.changeDataSet(it)
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    fun onTestClick(args: Bundle) {
        router.navigateTo(Screens.TestListScreen())
    }

    fun onAddTestClick() {
        router.navigateTo(Screens.AddTestScreen())
    }

}