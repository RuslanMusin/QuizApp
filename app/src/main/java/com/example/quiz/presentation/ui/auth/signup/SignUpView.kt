package com.example.quiz.presentation.ui.auth.signup

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.base.BaseView

interface SignUpView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showEmailError(hasError: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPasswordError(hasError: Boolean)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showError()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun createCookie()

}