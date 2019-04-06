package com.example.quiz

import com.example.quiz.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class QuizApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).create(this)
    }

    override fun onCreate() {
        super.onCreate()
    }
}