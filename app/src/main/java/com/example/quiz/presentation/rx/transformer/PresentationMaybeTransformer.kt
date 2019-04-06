package com.example.quiz.presentation.rx.transformer

import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class PresentationMaybeTransformer<T> : MaybeTransformer<T, T> {
    override fun apply(upstream: Maybe<T>): MaybeSource<T> {
        return upstream
                .doOnError(Throwable::printStackTrace)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }
}