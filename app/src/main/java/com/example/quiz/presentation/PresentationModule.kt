package com.example.quiz.presentation

import android.content.Context
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessorImpl
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class PresentationModule {

    @Provides
    @Singleton
    fun provideExceptionProcessor(context: Context): ExceptionProcessor {
        return ExceptionProcessorImpl(context)
    }
}