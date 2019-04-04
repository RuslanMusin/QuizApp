package com.example.quiz.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import com.example.quiz.presentation.ui.test.add_test.main.AddMainTestFragment
import com.example.quiz.presentation.ui.test.add_test.question.AddQuestionTestFragment
import com.example.quiz.presentation.ui.test.test_list.TestListFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class Screens {

    class MainScreen : SupportAppScreen() {
        override fun getActivityIntent(context: Context?): Intent {
            return Intent(context, MainActivity::class.java)
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

    class TestListScreen() : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return TestListFragment.newInstance()
        }
    }

}
