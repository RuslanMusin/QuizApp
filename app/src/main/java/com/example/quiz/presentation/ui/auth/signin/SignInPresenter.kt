package com.example.quiz.presentation.ui.auth.signin

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.data.repository.user.UserRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.TAG_LOG
import com.example.quiz.presentation.util.Const.TOKEN
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class SignInPresenter @Inject constructor() : BasePresenter<SignInView>() {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var userRepository: UserRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var router: Router

    fun signIn(email: String, password: String) {
        if (!validateForm(email, password)) {
            return
        }
        val user = User(email, password)

        authRepository
                .login(user)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe { viewState.showProgressDialog() }
                .doAfterTerminate { viewState.hideProgressDialog() }
                .subscribe({
                    TOKEN = TOKEN + it.key
                    findUser()
                }, {
                    viewState.showErrorDialog(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()

    }

    private fun findUser() {
        userRepository
            .findUser()
            .compose(PresentationSingleTransformer())
            .doOnSubscribe { viewState.showProgressDialog() }
            .doAfterTerminate { viewState.hideProgressDialog() }
            .subscribe({user ->
                viewState.hideProgressDialog()
                Const.currentUser = user
                Log.d(TAG_LOG, "userId = ${currentUser.id}")
                onProfileClick()
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    private fun validateForm(email: String, password: String): Boolean {
        return checkEmail(email) && checkPassword(password)
    }

    private fun checkEmail(email: String): Boolean {
        return if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
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

    fun onSignUpClick() {
        router.navigateTo(Screens.SignUpScreen())
    }

    fun onProfileClick() {
        router.newRootScreen(Screens.getAuthorResultScreen())
    }

    fun onBackCommandClick() {
        router.exit()
    }

}