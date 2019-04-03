package com.example.quiz.presentation.ui

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseActivity
import com.example.quiz.presentation.ui.common.BackButtonListener
import com.example.quiz.presentation.ui.sample.ChainHolder
import com.example.quiz.presentation.ui.sample.SampleFragment
import kotlinx.android.synthetic.main.activity_navigation.*
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command
import ru.terrakok.cicerone.commands.Replace
import java.lang.ref.WeakReference
import java.util.*
import javax.inject.Inject
import javax.inject.Provider

open class MainActivity: BaseActivity(), MainView, ChainHolder {

//    lateinit var screensSchemeTV: TextView
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
//            printScreensScheme()
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        screensSchemeTV = findViewById(R.id.screens_scheme) as TextView

        if (savedInstanceState == null) {
            navigator.applyCommands(arrayOf(Replace(Screens.SignInScreen())))
        } else {
            printScreensScheme()
        }
//        setSupportActionBar(toolbar as Toolbar)
    /*    val navFragment = host as NavHostFragment
        setupBottomNavMenu(navFragment.navController)*/

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        navigatorHolder.removeNavigator()
        super.onPause()
    }

    private fun printScreensScheme() {
        val fragments = ArrayList<SampleFragment>()
        for (fragmentReference in chain) {
            val fragment = fragmentReference.get()
            if (fragment != null && fragment is SampleFragment) {
                fragments.add(fragment as SampleFragment)
            }
        }
        Collections.sort<SampleFragment>(fragments) { f1, f2 ->
            val t = f1.creationTime - f2.creationTime
            if (t > 0)
                1
            else if (t < 0)
                -1
            else
                0
        }

        val keys = ArrayList<Int>()
        for (fragment in fragments) {
            keys.add(fragment.number)
        }
//        screensSchemeTV.setText("Chain: " + keys.toString() + "")
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


}