package com.example.quiz.presentation.ui.main.test.test_item.before_feedback

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class BeforeFeedbackPresenter @Inject constructor() : BasePresenter<BeforeFeedbackView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor

    fun onTestClick(id: Int) {
        val args: Bundle = Bundle()
        args.putString(Const.TEST_ITEM, id.toString())
        router.newRootChain(Screens.TestScreen(args))
    }

    fun onFeedbackClick(args: Bundle) {
        router.newRootChain(Screens.FeedbackScreen(args))
    }


}