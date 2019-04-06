package com.example.quiz.presentation.ui.test.add_test.main

import android.os.Bundle
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router
import javax.inject.Inject


@InjectViewState
class AddMainTestPresenter @Inject constructor() : BasePresenter<AddMainTestView>() {

    @Inject
    lateinit var router: Router

    fun onCreateQuestionCommandClick(args: Bundle) {
        router.navigateTo(Screens.AddQuestionScreen(args))
    }

    fun onBackCommandClick() {
        router.exit()
    }
}