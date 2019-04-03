package com.example.quiz.presentation.ui

import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    @Inject
    lateinit var router: Router

    fun onMultiPressed() {
        router.navigateTo(Screens.BottomNavigationScreen())
    }

}