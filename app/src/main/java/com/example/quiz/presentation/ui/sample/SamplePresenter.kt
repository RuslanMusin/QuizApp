package com.example.quiz.presentation.ui.sample

import android.os.Handler
import android.os.Looper
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.example.quiz.presentation.ui.Screens
import ru.terrakok.cicerone.Router
import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.ScheduledFuture
import java.util.concurrent.TimeUnit

@InjectViewState
class SamplePresenter(private val router: Router, private val screenNumber: Int) : MvpPresenter<SampleView>() {
    private val executorService: ScheduledExecutorService
    private var future: ScheduledFuture<*>? = null

    init {
        executorService = Executors.newSingleThreadScheduledExecutor()

        viewState.setTitle("Screen $screenNumber")
    }

    fun onBackCommandClick() {
        router.exit()
    }

    fun onForwardCommandClick() {
        router.navigateTo(Screens.SampleScreen(screenNumber + 1))
    }

    fun onReplaceCommandClick() {
        router.replaceScreen(Screens.SampleScreen(screenNumber + 1))
    }

    fun onNewChainCommandClick() {
        router.newChain(
            Screens.SampleScreen(screenNumber + 1),
            Screens.SampleScreen(screenNumber + 2),
            Screens.SampleScreen(screenNumber + 3)
        )
    }

    fun onFinishChainCommandClick() {
        router.finishChain()
    }

    fun onNewRootCommandClick() {
        router.newRootScreen(Screens.SampleScreen(screenNumber + 1))
    }

    fun onForwardWithDelayCommandClick() {
        if (future != null) future!!.cancel(true)
        future = executorService.schedule({
            //WARNING! Navigation must be only in UI thread.
            Handler(Looper.getMainLooper()).post { router.navigateTo(Screens.SampleScreen(screenNumber + 1)) }
        }, 5, TimeUnit.SECONDS)
    }

    fun onBackToCommandClick() {
        router.backTo(Screens.SampleScreen(3))
    }
}
