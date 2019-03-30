package com.example.quiz.presentation.base

import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatFragment


abstract class BaseFragment : MvpAppCompatFragment(), BaseView {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun showProgressDialog(message: String) {
        (activity as BaseView).showProgressDialog(message)
    }

    override fun showProgressDialog(messageId: Int) {
        (activity as BaseView).showProgressDialog(messageId)
    }

    override fun hideProgressDialog() {
        (activity as BaseView).hideProgressDialog()
    }

    override fun showSnackBar(message: String) {
        (activity as BaseView).showSnackBar(message)
    }

    override fun showSnackBar(messageId: Int) {
        (activity as BaseView).showSnackBar(messageId)
    }

    override fun setBottomVisibility(flag: Boolean) {
        (activity as BaseView).setBottomVisibility(flag)
    }

    override fun setActionBar(toolbar: Toolbar) {
        (activity as BaseView).setActionBar(toolbar)
    }

    override fun setToolbarTitle(id: Int) {
        (activity as BaseView).setToolbarTitle(id)
    }
}