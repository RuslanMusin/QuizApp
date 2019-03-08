package com.example.quiz.ui.base

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
import android.support.v7.app.AppCompatActivity
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.quiz.ui.base.interfaces.BasicFunctional


abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector, BasicFunctional {

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

}