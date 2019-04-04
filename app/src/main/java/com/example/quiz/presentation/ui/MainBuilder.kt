package com.example.quiz.presentation.ui

import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import com.example.quiz.presentation.ui.test.add_test.main.AddMainTestFragment
import com.example.quiz.presentation.ui.test.add_test.question.AddQuestionTestFragment
import com.example.quiz.presentation.ui.test.test_list.TestListFragment
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.all_test_list.AllTestsFragment
import com.example.quiz.presentation.ui.test.test_list.tab_fragment.ended_test_list.EndedTestsFragment
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

    @ContributesAndroidInjector
    abstract fun buildTestListFragment(): TestListFragment

    @ContributesAndroidInjector
    abstract fun buildAllTestsFragment(): AllTestsFragment

    @ContributesAndroidInjector
    abstract fun buildEndedTestsFragment(): EndedTestsFragment

    @ContributesAndroidInjector
    abstract fun buildAddMainTestFragment(): AddMainTestFragment

    @ContributesAndroidInjector
    abstract fun buildAddQuestionTestFragment(): AddQuestionTestFragment

}