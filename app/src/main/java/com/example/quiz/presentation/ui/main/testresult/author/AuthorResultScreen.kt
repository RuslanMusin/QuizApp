package com.example.quiz.presentation.ui.main.testresult.author

import android.os.Bundle
import android.support.v4.app.Fragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class AuthorResultScreen(val args: Bundle) : SupportAppScreen() {

    override fun getFragment(): Fragment {
        return AuthorResultFragment.getInstance(args)
    }

    override fun getScreenKey(): String {
        return AuthorResultFragment::class.java.name
    }
}