package com.example.quiz.presentation.ui.main.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.QuizApplication
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationCompletableTransformer
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class ProfilePresenter : BasePresenter<ProfileView>() {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun logout() {
        authRepository
                .logout()
                .compose(PresentationCompletableTransformer())
                .doOnSubscribe { viewState.showProgressDialog() }
                .doAfterTerminate { viewState.hideProgressDialog() }
                .subscribe({
                    viewState.logout()
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()
    }

}