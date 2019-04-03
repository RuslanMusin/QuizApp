package com.example.quiz.di

import android.app.Application
import dagger.BindsInstance
import com.example.quiz.QuizApplication
import com.example.quiz.data.DataModule
import com.example.quiz.di.modules.AppContextModule
import com.example.quiz.di.modules.navigation.LocalNavigationModule
import com.example.quiz.di.modules.navigation.NavigationModule
import com.example.quiz.presentation.PresentationModule
import com.example.quiz.presentation.ui.RootBuilder
import com.example.quiz.presentation.ui.auth.signin.SignInPresenter
import com.example.quiz.presentation.ui.auth.signup.SignUpPresenter
import com.example.quiz.presentation.ui.main.profile.ProfilePresenter
import dagger.Component
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton


@Component(
    modules = [
        AppContextModule::class,
        DataModule::class,
        PresentationModule::class,
        RootBuilder::class,
        LocalNavigationModule::class,
        NavigationModule::class,
        AndroidSupportInjectionModule::class
    ]
)
@Singleton
interface AppComponent: AndroidInjector<QuizApplication> {

    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<QuizApplication>() {

        @BindsInstance
        abstract fun application(application: Application): Builder
    }
}