package com.example.quiz.presentation.rx.transformer

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class PresentationFlowableTransformer<T> : FlowableTransformer<T, T> {

    override fun apply(upstream: Flowable<T>): Flowable<T> {
        return upstream
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

}