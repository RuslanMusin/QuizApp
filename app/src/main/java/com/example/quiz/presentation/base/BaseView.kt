package com.example.quiz.presentation.base

import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.quiz.R

interface BaseView: MvpView {

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgressDialog(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showProgressDialog(messageId: Int = R.string.progress_message)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun hideProgressDialog()

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSnackBar(message: String)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun showSnackBar(messageId: Int)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setActionBar(toolbar: Toolbar)

    @StateStrategyType(OneExecutionStateStrategy::class)
    fun setToolbarTitle(id: Int)

    fun changeWindowsSoftInputMode(mode: Int)

}