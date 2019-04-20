package com.example.quiz.presentation.ui.auth.signup

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.model.user.User
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.user.UserRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SignUpPresenter @Inject constructor() : BasePresenter<SignUpView>() {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var router: Router

    fun createAccount(user: User) {
        if (!validateForm(user)) {
            return
        }

        userRepository.createUser(user)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe { viewState.showProgressDialog() }
                .doAfterTerminate { viewState.hideProgressDialog() }
                .subscribe({
                    Const.currentUser = user
                    login(user)
                    onTestListClick()
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()
    }

    private fun login(user: User) {
        authRepository
            .login(user)
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({
                Const.TOKEN = Const.TOKEN + it.key
                onTestListClick()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    private fun validateForm(user: User): Boolean {
        return checkEmail(user.email) && checkPassword(user.password)
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

    fun onSignInCommandClick() {
        router.exit()
    }

    fun onTestListClick() {
        router.newRootScreen(Screens.TestListScreen())
    }

    fun onBackCommandClick() {
        router.finishChain()
    }

}