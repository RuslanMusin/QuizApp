package com.example.quiz.presentation.ui.auth.signin

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.presentation.model.user.Lector
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.curator.UserRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor() : BasePresenter<SignInView>() {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        val lector = Lector(email, password)

        authRepository
                .login(lector)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe { viewState.showProgressDialog() }
                .doAfterTerminate { viewState.hideProgressDialog() }
                .subscribe({
                    viewState.createCookie(lector.email, lector.password)
                    viewState.goToProfile(lector)
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()

    }

    private fun validateForm(email: String, password: String): Boolean {
        return checkEmail(email) && checkPassword(password)
    }

    private fun checkEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            viewState.showEmailError(true)
            false
        } else {
            viewState.showEmailError(false)
            true
        }
    }

    private fun checkPassword(password: String): Boolean {
        return if (TextUtils.isEmpty(password)) {
            viewState.showPasswordError(true)
            false
        } else {
            viewState.showPasswordError(false)
            true
        }
    }
}