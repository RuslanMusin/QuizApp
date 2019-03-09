package com.example.quiz.ui.base

import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.quiz.ui.base.interfaces.BasicFunctional


abstract class BaseFragment : MvpAppCompatFragment(), BasicFunctional {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun showProgressDialog(message: String) {
        (activity as BasicFunctional).showProgressDialog(message)
    }

    override fun showProgressDialog(messageId: Int) {
        (activity as BasicFunctional).showProgressDialog(messageId)
    }

    override fun hideProgressDialog() {
        (activity as BasicFunctional).hideProgressDialog()
    }

    override fun showSnackBar(message: String) {
        (activity as BasicFunctional).showSnackBar(message)
    }

    override fun showSnackBar(messageId: Int) {
        (activity as BasicFunctional).showSnackBar(messageId)
    }

    override fun setBottomVisibility(flag: Boolean) {
        (activity as BasicFunctional).setBottomVisibility(flag)
    }

    override fun setActionBar(toolbar: Toolbar) {
        (activity as BasicFunctional).setActionBar(toolbar)
    }

    override fun setToolbarTitle(id: Int) {
        (activity as BasicFunctional).setToolbarTitle(id)
    }
}