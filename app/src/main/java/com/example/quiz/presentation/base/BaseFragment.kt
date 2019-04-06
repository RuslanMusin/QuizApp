package com.example.quiz.presentation.base

import android.content.Context
import dagger.android.support.AndroidSupportInjection
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import com.arellomobile.mvp.MvpAppCompatFragment
import com.example.quiz.presentation.base.navigation.ChainHolder
import java.lang.ref.WeakReference


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

    override fun setActionBar(toolbar: Toolbar) {
        (activity as BaseView).setActionBar(toolbar)
    }

    override fun setToolbarTitle(id: Int) {
        (activity as BaseView).setToolbarTitle(id)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        val activity = activity
        if (activity is ChainHolder) {
            (activity as ChainHolder).chain.add(WeakReference<Fragment>(this))
        }
    }

    override fun onDetach() {
        val activity = activity
        if (activity is ChainHolder) {
            val chain = (activity as ChainHolder).chain
            val it = chain.iterator()
            while (it.hasNext()) {
                val fragmentReference = it.next()
                val fragment = fragmentReference.get()
                if (fragment != null && fragment === this) {
                    it.remove()
                    break
                }
            }
        }
        super.onDetach()
    }

    override fun changeWindowsSoftInputMode(mode: Int) {
        (activity as BaseView).changeWindowsSoftInputMode(mode)
    }
}