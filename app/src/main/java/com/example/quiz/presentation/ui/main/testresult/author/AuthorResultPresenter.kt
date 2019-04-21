package com.example.quiz.presentation.ui.main.testresult.author

import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import javax.inject.Inject

@InjectViewState
class AuthorResultPresenter @Inject constructor() : BasePresenter<AuthorResultView>() {

    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    var id: Int = 22

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
//        update()
    }

    fun onRetry() {
      update()
    }

    fun update() {
        testRepository.getAuthorTestResults(id)
                .compose(PresentationSingleTransformer())
                .doOnSubscribe {
                    viewState.showProgress()
                    viewState.hideRetry()
                }
                .doAfterTerminate { viewState.hideProgress() }
                .subscribe({ result ->
                    viewState.setTestInfo(result)
                    result.questions?.let { viewState.showBarChartAnswers(it) }
                    result.feedbacks?.get(0)?.let { viewState.showPieChartFeedbackOne(it) }
                    result.feedbacks?.get(1)?.let { viewState.showPieChartFeedbackTwo(it) }
                    result.feedbacks?.get(2)?.let { viewState.showPieChartFeedbackThree(it) }
                }, {
                    viewState.showRetry(exceptionProcessor.processException(it))
                })
                .disposeWhenDestroy()
    }

}