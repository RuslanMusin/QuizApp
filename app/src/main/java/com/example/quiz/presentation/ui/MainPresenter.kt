package com.example.quiz.presentation.ui

import android.content.SharedPreferences
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.util.Const.TESTS
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class MainPresenter @Inject constructor(): BasePresenter<MainView>() {

    @Inject
    lateinit var router: Router
    @Inject
    lateinit var prefs: SharedPreferences

    fun removeTests() {
        prefs.edit().putString(TESTS, "").apply()
    }

}