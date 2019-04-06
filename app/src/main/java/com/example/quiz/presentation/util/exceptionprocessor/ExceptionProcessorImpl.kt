package com.example.quiz.presentation.util.exceptionprocessor

import android.content.Context
import com.example.quiz.R
import com.example.quiz.data.network.exception.NoInternetConnectionException
import com.example.quiz.data.network.exception.TimeOutException

class ExceptionProcessorImpl(val context: Context): ExceptionProcessor {

    override fun processException(t: Throwable): String {
        return when (t) {
            is NoInternetConnectionException -> getString(R.string.exception_no_connection)
            is TimeOutException -> getString(R.string.exception_time_out)
            else -> getString(R.string.exception_unknown)
        }
    }

    private fun getString(text: Int): String {
        return context.getString(text)
    }
}