package com.example.quiz.presentation.ui.auth.signin

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.base.BaseView

interface SignInView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmailError(hasError: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPasswordError(hasError: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun createCookie(email: String, password: String)
}