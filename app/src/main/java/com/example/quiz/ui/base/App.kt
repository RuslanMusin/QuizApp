package com.example.quiz.ui.base

import android.app.Activity
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.HasActivityInjector
import android.app.Application
import com.example.quiz.di.components.AppComponent
import com.example.quiz.di.components.DaggerAppComponent

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()

        sAppComponent = DaggerAppComponent
            .builder()
            .context(this)
            .build()

        sAppComponent.inject(this)

    }

    override fun activityInjector(): AndroidInjector<Activity>? {
        return dispatchingAndroidInjector
    }

    companion object {
        lateinit var sAppComponent: AppComponent
    }
}