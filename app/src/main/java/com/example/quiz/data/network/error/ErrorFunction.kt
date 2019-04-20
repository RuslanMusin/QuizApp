package com.example.quiz.data.network.error

import io.reactivex.functions.Function

interface ErrorFunction<T>: Function<Throwable, T> {
}