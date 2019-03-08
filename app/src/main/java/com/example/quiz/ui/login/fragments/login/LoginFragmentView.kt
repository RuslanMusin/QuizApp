package com.example.quiz.ui.login.fragments.login

import com.example.quiz.model.user.User
import com.example.quiz.ui.base.interfaces.BasicFunctional

interface LoginFragmentView: BasicFunctional {

    fun showEmailError(hasError: Boolean)

    fun showPasswordError(hasError: Boolean)

    fun showError()

    fun createCookie(email: String, password: String)

    fun goToProfile(curator: User)
}