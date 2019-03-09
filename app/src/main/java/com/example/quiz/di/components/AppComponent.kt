package com.example.quiz.di.components

import android.content.Context
import dagger.BindsInstance
import com.example.quiz.di.modules.AppModule
import com.example.quiz.ui.base.App
import com.example.quiz.ui.login.fragments.login.LoginFragmentPresenter
import com.example.quiz.ui.login.fragments.signup.SignUpPresenter
import com.example.quiz.ui.profile.StudentFragment
import com.example.quiz.ui.profile.StudentPresenter
import dagger.Component
import javax.inject.Singleton


@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun context(context: Context): Builder

        fun build(): AppComponent
    }

    fun inject(app: App)

    fun inject(presenter: LoginFragmentPresenter)

    fun inject(presenter: SignUpPresenter)

    fun inject(presenter: StudentPresenter)

}