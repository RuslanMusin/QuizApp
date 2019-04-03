package com.example.quiz.presentation.ui.forward

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.terrakok.cicerone.Router

@InjectViewState
class BottomNavigationPresenter(private val router: Router) : MvpPresenter<BottomNavigationView>() {

    fun onBackPressed() {
        router.exit()
    }
}
