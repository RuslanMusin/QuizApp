package com.example.quiz.presentation.rx.transformer

import io.reactivex.Single
import io.reactivex.SingleSource
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PresentationSingleTransformer<T> : SingleTransformer<T, T> {
    override fun apply(upstream: Single<T>): SingleSource<T> {
        return upstream
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}