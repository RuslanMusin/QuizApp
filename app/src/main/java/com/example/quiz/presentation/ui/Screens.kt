package com.example.quiz.presentation.ui

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.support.v4.app.Fragment
import com.example.quiz.presentation.ui.bottom.BottomNavigationActivity
import com.example.quiz.presentation.ui.bottom.ForwardFragment
import com.example.quiz.presentation.ui.bottom.TabContainerFragment
import com.example.quiz.presentation.ui.sample.SampleFragment
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
}
