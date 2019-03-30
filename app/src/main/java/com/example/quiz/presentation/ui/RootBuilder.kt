package com.example.quiz.presentation.ui

import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class RootBuilder {

    @ContributesAndroidInjector(modules = [MainModule::class])
    abstract fun buildMainActivity(): MainActivity
}