package com.example.quiz.presentation.util.exceptionprocessor

interface ExceptionProcessor {

    fun processException(t: Throwable): String
}