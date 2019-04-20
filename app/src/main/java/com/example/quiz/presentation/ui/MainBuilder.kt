package com.example.quiz.presentation.ui

import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import com.example.quiz.presentation.ui.test.add_test.main.AddMainTestFragment
import com.example.quiz.presentation.ui.test.add_test.question.AddQuestionTestFragment
import com.example.quiz.presentation.ui.test.test_item.before_feedback.BeforeFeedbackFragment
import com.example.quiz.presentation.ui.test.test_item.check_answers.AnswersFragment
import com.example.quiz.presentation.ui.test.test_item.feedback.FeedbackFragment
import com.example.quiz.presentation.ui.test.test_item.finish.FinishFragment
import com.example.quiz.presentation.ui.test.test_item.main.TestFragment
import com.example.quiz.presentation.ui.test.test_item.question.QuestionFragment
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

    @ContributesAndroidInjector
    abstract fun buildTestFragment(): TestFragment

    @ContributesAndroidInjector
    abstract fun buildQuestionFragment(): QuestionFragment

    @ContributesAndroidInjector
    abstract fun buildFinishFragment(): FinishFragment

    @ContributesAndroidInjector
    abstract fun buildAnswersFragment(): AnswersFragment

    @ContributesAndroidInjector
    abstract fun buildFeedbackFragment(): FeedbackFragment

    @ContributesAndroidInjector
    abstract fun buildBeforeFeedbackFragment(): BeforeFeedbackFragment

}