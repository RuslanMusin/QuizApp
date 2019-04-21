package com.example.quiz.presentation.base

import android.graphics.Color
import dagger.android.AndroidInjector
import dagger.android.AndroidInjection
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.support.HasSupportFragmentInjector
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.example.quiz.presentation.dialog.ErrorDialog
import com.example.quiz.presentation.dialog.WaitDialog


abstract class BaseActivity : MvpAppCompatActivity(), HasSupportFragmentInjector, BaseView {

    companion object {
        private const val TAG_WAIT_DIALOG = "TAG_WAIT_DIALOG"
        private const val TAG_ERROR_DIALOG = "TAG_ERROR_DIALOG"
    }

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return fragmentInjector
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

    override fun changeWindowsSoftInputMode(mode: Int) {
        this.window.setSoftInputMode(mode);
    }

    override fun showErrorDialog(errorText: Int) {
        showErrorDialog(getString(errorText))
    }

    override fun showErrorDialog(errorText: String) {
        ErrorDialog.getInstance(errorText).show(supportFragmentManager, TAG_ERROR_DIALOG)
    }

    override fun showProgressDialog() {
        WaitDialog.getInstance().show(supportFragmentManager, TAG_WAIT_DIALOG)
    }

    override fun hideProgressDialog() {
        (supportFragmentManager.findFragmentByTag(TAG_WAIT_DIALOG) as? DialogFragment)?.dismiss()
    }
}