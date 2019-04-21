package com.example.quiz.presentation.ui.main.testresult.author

import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import com.example.quiz.presentation.base.BaseView
import com.example.quiz.presentation.model.result.AuthorQuestion
import com.example.quiz.presentation.model.result.AuthorResult

interface AuthorResultView: BaseView {

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showBarChartAnswers(questions: List<AuthorQuestion>)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPieChartFeedbackOne(feedback: AuthorQuestion)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPieChartFeedbackTwo(feedback: AuthorQuestion)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showPieChartFeedbackThree(feedback: AuthorQuestion)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideProgress()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showRetry(errorText: String)

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun hideRetry()

    @StateStrategyType(AddToEndSingleStrategy::class)
    fun setTestInfo(item: AuthorResult)
}