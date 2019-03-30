package com.example.quiz.presentation.ui

import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class MainBuilder {

    @ContributesAndroidInjector
    abstract fun buildSignInFragment(): SignInFragment

    @ContributesAndroidInjector
    abstract fun buildSignUpFragment(): SignUpFragment

    @ContributesAndroidInjector
    abstract fun buildProfileFragment(): ProfileFragment
}