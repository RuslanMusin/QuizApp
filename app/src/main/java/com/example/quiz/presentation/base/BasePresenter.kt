package com.example.quiz.presentation.base

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BasePresenter<View : MvpView> : MvpPresenter<View>() {

    private val destroyDisposable = CompositeDisposable()

    override fun onDestroy() {
        super.onDestroy()
        if (!destroyDisposable.isDisposed) {
            destroyDisposable.dispose()
        }
    }

    protected fun Disposable.disposeWhenDestroy() {
        destroyDisposable.add(this)
    }

}