package com.example.quiz.presentation.base

import android.app.ProgressDialog
import android.graphics.Color
import dagger.android.AndroidInjector
import dagger.android.AndroidInjection
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.support.HasSupportFragmentInjector
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import kotlinx.android.synthetic.main.activity_navigation.*


abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector,
    BaseView {

    var progressDialog: ProgressDialog? = null

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentInjector
    }

    override fun showProgressDialog(message: String) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(this)
            progressDialog?.let{
                it.setMessage(message)
                it.isIndeterminate = true
                it.setCancelable(false)
            }
        }
        progressDialog?.show()
    }

    override fun showProgressDialog(messageId: Int) {
        showProgressDialog(getString(messageId))
    }

    override fun hideProgressDialog() {
        progressDialog?.let {
            if (it.isShowing) {
                progressDialog!!.dismiss()
            }
        }
    }

    override fun showSnackBar(message: String) {
        val snackbar: Snackbar = Snackbar.make(findViewById(android.R.id.content),
            message, Snackbar.LENGTH_LONG)
        snackbar.getView().setBackgroundColor(Color.BLACK)
        val textView = snackbar.view.findViewById(android.support.design.R.id.snackbar_text) as TextView;
        textView.setTextColor(Color.WHITE);
        snackbar.show()
    }

    override fun showSnackBar(messageId: Int) {
        showSnackBar(getString(messageId))
    }

    override fun setActionBar(toolbar: Toolbar) {
        setSupportActionBar(toolbar)
    }

    override fun setToolbarTitle(id: Int) {
        supportActionBar?.title = getString(id)
    }

    override fun setBottomVisibility(flag: Boolean) {
        if(flag) {
            bottom_nav_view.visibility = View.VISIBLE
        } else {
            bottom_nav_view.visibility = View.GONE
        }
    }

}