package com.example.quiz.ui.navigation

import android.os.Bundle
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.widget.Toolbar
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.quiz.R
import com.example.quiz.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_navigation.*

open class NavigationActivity: BaseActivity(), NavigationView {

    @InjectPresenter
    lateinit var navigationPresenter: NavigationPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_navigation)

//        setSupportActionBar(toolbar as Toolbar)
        val navFragment = host as NavHostFragment
        setupBottomNavMenu(navFragment.navController)

    }

    private fun setupBottomNavMenu(navController: NavController) {
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_nav_view)
        bottomNav?.setupWithNavController(navController)
    }



}