package com.example.quiz.presentation.ui

import android.content.Context
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseActivity
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.base.navigation.ChainHolder
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.ui.auth.signin.SignInFragment
import com.example.quiz.presentation.util.Const.ORIGINAL_TOKEN
import com.example.quiz.presentation.util.Const.TOKEN
import com.example.quiz.presentation.util.Const.USER_DATA_PREFERENCES
import com.example.quiz.presentation.util.Const.USER_ITEM
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

open class MainActivity: BaseActivity(), MainView, ChainHolder {

    override val chain = ArrayList<WeakReference<Fragment>>()

    @InjectPresenter
    lateinit var presenter: MainPresenter

    @Inject
    lateinit var presenterProvider: Provider<MainPresenter>

    @ProvidePresenter
    fun providePresenter(): MainPresenter = presenterProvider.get()

    @Inject
    lateinit var navigatorHolder: NavigatorHolder

    private val navigator = object : SupportAppNavigator(this, R.id.main_container) {
        override fun applyCommands(commands: Array<Command>) {
            super.applyCommands(commands)
            supportFragmentManager.executePendingTransactions()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            if(checkUserSession()) {
                navigator.applyCommands(arrayOf(Replace(Screens.ProfileScreen())))
            } else {
                navigator.applyCommands(arrayOf(Replace(Screens.SignInScreen())))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.main_container)
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            super.onBackPressed()
        }
    }

    private fun checkUserSession(): Boolean {
        var flag = false
        getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            if (it.contains(ORIGINAL_TOKEN)) {
                flag = true
                TOKEN = it.getString(ORIGINAL_TOKEN, "")
                currentUser = gson.fromJson(it.getString(USER_ITEM, ""), User::class.java)
            }
        }
        return flag
    }


}