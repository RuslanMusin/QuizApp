package com.example.quiz.presentation.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.ui.auth.signup.SignUpFragment
import com.example.quiz.presentation.ui.main.profile.ProfileFragment
import com.example.quiz.presentation.ui.main.test.add.main.AddMainTestFragment
import com.example.quiz.presentation.ui.main.test.add.question.AddQuestionTestFragment
import com.example.quiz.presentation.ui.main.test.test_item.before_feedback.BeforeFeedbackFragment
import com.example.quiz.presentation.ui.main.test.test_item.check_answers.AnswersFragment
import com.example.quiz.presentation.ui.main.test.test_item.feedback.FeedbackFragment
import com.example.quiz.presentation.ui.main.test.test_item.finish.FinishFragment
import com.example.quiz.presentation.ui.main.test.test_item.main.TestFragment
import com.example.quiz.presentation.ui.main.test.test_item.question.QuestionFragment
import com.example.quiz.presentation.ui.main.test.test_list.TestListFragment
import com.example.quiz.presentation.ui.main.testresult.author.AuthorResultScreen
import ru.terrakok.cicerone.android.support.SupportAppScreen

object Screens {

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

    class AddTestScreen(val args: Bundle?) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AddMainTestFragment.newInstance(args)
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

    class TestScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return TestFragment.newInstance(args)
        }
    }

    class QuestionScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return QuestionFragment.newInstance(args)
        }
    }

    class AnswersScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return AnswersFragment.newInstance(args)
        }
    }

    class FinishScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FinishFragment.newInstance(args)
        }
    }

    class BeforeFeedbackScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return BeforeFeedbackFragment.newInstance(args)
        }
    }

    class FeedbackScreen(val args: Bundle) : SupportAppScreen() {
        override fun getFragment(): Fragment {
            return FeedbackFragment.newInstance(args)
        }
    }

    fun getAuthorResultScreen(args: Bundle): AuthorResultScreen = AuthorResultScreen(args)
}
