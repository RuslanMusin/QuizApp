package com.example.quiz.presentation.ui.main.test.test_item.finish

import android.content.SharedPreferences
import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.data.repository.test.TestRepository
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class FinishPresenter @Inject constructor() : BasePresenter<FinishView>() {

    @Inject
    lateinit var testRepository: TestRepository
    @Inject
    lateinit var router: Router

    fun onTestClick(args: Bundle) {
        router.newRootChain(Screens.TestScreen(args))
    }

    fun onAnswersClick(args: Bundle) {
        router.navigateTo(Screens.AnswersScreen(args))
    }

}