package com.example.quiz.presentation.ui.forward

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router

@InjectViewState
class ForwardPresenter(private val container: String, private val router: Router, private val number: Int) :
    MvpPresenter<ForwardView>() {

    init {

        viewState.setChainText(createChain(number))
    }

    private fun createChain(number: Int): String {
        var chain = "[0]"

        for (i in 0 until number) {
            chain += "➔" + (i + 1)
        }

        return chain
    }

    fun onForwardPressed() {
        router.navigateTo(Screens.ForwardScreen(container, number + 1))
    }

    fun onGithubPressed() {
        router.navigateTo(Screens.GithubScreen())
    }

    fun onBackPressed() {
        router.exit()
    }
}