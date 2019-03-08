package com.example.quiz.ui.login.fragments.signup

import com.arellomobile.mvp.MvpView
import com.example.quiz.model.user.User
import com.example.quiz.ui.base.interfaces.BasicFunctional

interface SignUpView: BasicFunctional {

    fun showEmailError(hasError: Boolean)

    fun showPasswordError(hasError: Boolean)

    fun showError()

    fun goToProfile(user: User)
}