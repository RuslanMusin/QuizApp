package com.example.quiz.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.historyquiz.ui.tests.add_test.question.AddQuestionTestFragment
import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.bottom.BottomNavigationActivity
import com.example.quiz.presentation.ui.bottom.ForwardFragment
import com.example.quiz.presentation.ui.bottom.TabContainerFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import com.example.quiz.presentation.ui.sample.SampleFragment
import com.example.quiz.presentation.ui.test.add_test.main.AddMainTestFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class SampleScreen(private val number: Int) : SupportAppScreen() {

        init {
            this.screenKey = javaClass.simpleName + "_" + number
        }

        override fun getFragment(): Fragment {
            return SampleFragment.getNewInstance(number)
        }
    }

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
        }
    }

    class BottomNavigationScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent {
            return Intent(context, BottomNavigationActivity::class.java)
        }
    }

    class TabScreen(private val tabName: String) : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return TabContainerFragment.getNewInstance(tabName)
        }

    }

    class ForwardScreen(private val containerName: String, private val number: Int) : SupportAppScreen() {

        override fun getFragment(): Fragment {
            return ForwardFragment.getNewInstance(containerName, number)
        }
    }

    class GithubScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context): Intent {
            return Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/terrakok/Cicerone"))
        }
    }

    class SignInScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SignInFragment.newInstance()
        }
    }

    class SignUpScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return SignUpFragment.newInstance()
        }
    }

    class ProfileScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return ProfileFragment.newInstance()
        }
    }

    class AddTestScreen : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AddMainTestFragment.newInstance()
        }
    }

    class AddQuestionScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AddQuestionTestFragment.newInstance(args)
        }
    }

}
