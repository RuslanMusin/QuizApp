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

    override fun showSnackBar(message: String) {
        (activity as? BaseActivity)?.showSnackBar(message)
    }

    override fun showSnackBar(messageId: Int) {
        (activity as? BaseActivity)?.showSnackBar(messageId)
    }

    override fun setActionBar(toolbar: Toolbar) {
        (activity as? BaseActivity)?.setActionBar(toolbar)
    }

    override fun setToolbarTitle(id: Int) {
        (activity as? BaseActivity)?.setToolbarTitle(id)
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
        (activity as? BaseActivity)?.changeWindowsSoftInputMode(mode)
    }

    override fun showErrorDialog(errorText: String) {
        (activity as? BaseActivity)?.showErrorDialog(errorText)
    }

    override fun showErrorDialog(errorText: Int) {
        (activity as? BaseActivity)?.showErrorDialog(errorText)
    }

    override fun showProgressDialog() {
        (activity as? BaseActivity)?.showProgressDialog()
    }

    override fun hideProgressDialog() {
        (activity as? BaseActivity)?.hideProgressDialog()
    }
}