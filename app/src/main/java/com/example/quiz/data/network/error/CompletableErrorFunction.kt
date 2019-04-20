package com.example.quiz.data.network.error

import io.reactivex.Completable
import io.reactivex.functions.Function

interface CompletableErrorFunction: Function<Throwable, Completable> {
}