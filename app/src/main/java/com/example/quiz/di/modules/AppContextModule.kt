package com.example.quiz.di.modules

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.example.quiz.presentation.util.Const.TEST_PREFS
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppContextModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideSharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(TEST_PREFS, Context.MODE_PRIVATE)
    }
 }