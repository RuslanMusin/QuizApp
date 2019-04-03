package com.example.quiz.presentation.ui.bottom

import android.os.Bundle
import android.support.v4.app.Fragment
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.ashokvarma.bottomnavigation.BottomNavigationBar
import com.example.quiz.R
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.ui.common.BackButtonListener
import com.example.quiz.presentation.ui.common.RouterProvider
import com.example.quiz.presentation.ui.forward.BottomNavigationPresenter
import com.example.quiz.presentation.ui.forward.BottomNavigationView
import ru.terrakok.cicerone.Router
import javax.inject.Inject

class BottomNavigationActivity : MvpAppCompatActivity(), BottomNavigationView, RouterProvider {
    private var bottomNavigationBar: BottomNavigationBar? = null

    @Inject
    override lateinit var router: Router

    @InjectPresenter
    lateinit var presenter: BottomNavigationPresenter

    @ProvidePresenter
    fun createBottomNavigationPresenter(): BottomNavigationPresenter {
        return BottomNavigationPresenter(router)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
//        SampleApplication.INSTANCE.getAppComponent().inject(this)
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_bottom)
        bottomNavigationBar = findViewById(R.id.ab_bottom_navigation_bar) as BottomNavigationBar

        initViews()

        if (savedInstanceState == null) {
            bottomNavigationBar!!.selectTab(0, true)
        }
    }

    private fun initViews() {
        bottomNavigationBar!!.setTabSelectedListener(object : BottomNavigationBar.OnTabSelectedListener {
            override fun onTabSelected(position: Int) {
                when (position) {
                    0 -> selectTab("ANDROID")
                    1 -> selectTab("BUG")
                    2 -> selectTab("DOG")
                }
                bottomNavigationBar!!.selectTab(position, false)
            }

            override fun onTabUnselected(position: Int) {

            }

            override fun onTabReselected(position: Int) {
                onTabSelected(position)
            }
        })

    }

    private fun selectTab(tab: String) {
        val fm = supportFragmentManager
        var currentFragment: Fragment? = null
        val fragments = fm.fragments
        if (fragments != null) {
            for (f in fragments) {
                if (f.isVisible) {
                    currentFragment = f
                    break
                }
            }
        }
        val newFragment = fm.findFragmentByTag(tab)

        if (currentFragment != null && newFragment != null && currentFragment === newFragment) return

        val transaction = fm.beginTransaction()
        if (newFragment == null) {
            transaction.add(R.id.ab_container, Screens.TabScreen(tab).getFragment(), tab)
        }

        if (currentFragment != null) {
            transaction.hide(currentFragment)
        }

        if (newFragment != null) {
            transaction.show(newFragment)
        }
        transaction.commitNow()
    }

    override fun onBackPressed() {
        val fm = supportFragmentManager
        var fragment: Fragment? = null
        val fragments = fm.fragments
        if (fragments != null) {
            for (f in fragments) {
                if (f.isVisible) {
                    fragment = f
                    break
                }
            }
        }
        if (fragment != null
            && fragment is BackButtonListener
            && (fragment as BackButtonListener).onBackPressed()
        ) {
            return
        } else {
            presenter!!.onBackPressed()
        }
    }
}
