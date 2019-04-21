package com.example.quiz.presentation.ui.test.test_list

import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.ui.main.profile.ProfileView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class TestListPresenter @Inject constructor() : BasePresenter<TestListView>() {

    @Inject
    lateinit var router: Router

    fun onBackClick() {
        router.newRootScreen(Screens.ProfileScreen())
    }

}