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
import com.example.quiz.presentation.util.Const.ORIGINAL_TOKEN
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TOKEN
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
                .subscribe({
                    Const.currentUser = user
                    login(user)
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()
    }

    private fun login(user: User) {
        authRepository
            .login(user)
            .compose(PresentationSingleTransformer())
            .subscribe({
                Const.TOKEN = ORIGINAL_TOKEN + it.key
                Log.d(TAG_LOG, "token = $TOKEN")
                viewState.createCookie()
                onProfileClick()
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
        router.newRootScreen(Screens.SignInScreen())
    }

    fun onProfileClick() {
        router.newRootScreen(Screens.ProfileScreen())
    }

    fun onBackCommandClick() {
        router.exit()
    }
}