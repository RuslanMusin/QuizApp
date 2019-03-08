package com.example.quiz.di.modules

import com.example.quiz.di.scopes.ActivityScope
import com.example.quiz.di.scopes.FragmentScope
import com.example.quiz.ui.base.BaseFragment
import com.example.quiz.ui.login.LoginActivity
import com.example.quiz.ui.login.fragments.login.LoginFragment
import com.example.quiz.ui.login.fragments.login.LoginFragmentPresenter
import com.example.quiz.ui.login.fragments.signup.SignUpFragment
import com.example.quiz.ui.navigation.NavigationActivity
import com.example.quiz.ui.profile.StudentFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module(includes = [AndroidSupportInjectionModule::class, RepositoryModule::class])
interface AppModule {

    @ActivityScope
    @ContributesAndroidInjector()
    fun loginActivityInjector(): LoginActivity

    @FragmentScope
    @ContributesAndroidInjector()
    fun loginFragmentInjector(): LoginFragment

    @FragmentScope
    @ContributesAndroidInjector()
    fun signUpFragmentInjector(): SignUpFragment

    @ActivityScope
    @ContributesAndroidInjector()
    fun navigationActivityInjector(): NavigationActivity

    @FragmentScope
    @ContributesAndroidInjector()
    fun profileFragmentInjector(): StudentFragment
}
