package com.example.quiz.presentation.ui.auth.signup

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
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
import com.example.quiz.presentation.util.Const.currentUser
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
                    it.response()?.let { res ->
                        if (res.isSuccessful) {
                            findUserAndLogin()
                        } else if (res.code() == 400) {
                            viewState.showSnackBar(R.string.email_exists)
                            viewState.hideProgressDialog()
                        }
                    }
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()
    }

    private fun findUserAndLogin() {
        userRepository
            .findUser()
            .compose(PresentationSingleTransformer())
            .subscribe({user ->
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
                it.response()?.let { res ->
                    if (res.isSuccessful) {
                        TOKEN = ORIGINAL_TOKEN + res.body()?.key
                        Log.d(TAG_LOG, "token = $TOKEN")
                        viewState.createCookie()
                        onProfileClick()
                    } else if (res.code() == 400) {
                        viewState.showError()
                    }
                }
            }, {
                viewState.showSnackBar(exceptionProcessor.processException(it))
            }).disposeWhenDestroy()
    }

    private fun validateForm(user: User): Boolean {
        return checkEmail(user.email)
                && checkPassword(user.password)
        && checkName(user.name)
        && checkLastName(user.lastname)

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

    private fun checkLastName(lastname: String): Boolean {
        return if (TextUtils.isEmpty(lastname)) {
            viewState.showLastNameError(true)
            false
        } else {
            viewState.showLastNameError(false)
            true
        }
    }

    private fun checkName(name: String): Boolean {
        return if (TextUtils.isEmpty(name)) {
            viewState.showNameError(true)
            false
        } else {
            viewState.showNameError(false)
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