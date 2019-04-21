package com.example.quiz.presentation.ui.auth.signin

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.util.Const.ORIGINAL_TOKEN
import com.example.quiz.presentation.util.Const.TOKEN
import com.example.quiz.presentation.util.Const.USER_DATA_PREFERENCES
import com.example.quiz.presentation.util.Const.USER_ITEM
import com.example.quiz.presentation.util.Const.currentUser
import com.example.quiz.presentation.util.Const.gson
import kotlinx.android.synthetic.main.fragment_sign_in.*
import javax.inject.Inject
import javax.inject.Provider

class SignInFragment : BaseFragment(), SignInView, BackButtonListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: SignInPresenter

    @Inject
    lateinit var presenterProvider: Provider<SignInPresenter>

    @ProvidePresenter
    fun providePresenter(): SignInPresenter = presenterProvider.get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_in, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        hideProgressDialog()
        initViews()
    }

    private fun initViews() {
        setActionBar(toolbar)
        setListeners()
        hideProgressDialog()
    }

    private fun signUp(v: View) {
        presenter.onSignUpClick()
    }

    override fun showEmailError(hasError: Boolean) {
        if (hasError) {
            ti_email.error = getString(R.string.enter_correct_name)
        } else {
            ti_email.error = null
        }

    }

    override fun showPasswordError(hasError: Boolean) {
        if (hasError) {
            ti_password.error = getString(R.string.enter_correct_password)
        } else {
            ti_password.error = null
        }

    }

    private fun setListeners() {
        btn_enter.setOnClickListener(this)
        btn_sign_up.setOnClickListener(this)
        iv_cover.setOnClickListener(this)
        tv_valera.setOnClickListener(this)
        tv_azat.setOnClickListener(this)
        tv_ivan.setOnClickListener(this)
        tv_damir.setOnClickListener(this)
        tv_aygul.setOnClickListener(this)
        tv_ruslan.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_enter -> {
                val email = et_email.getText().toString();
                val password = et_password.getText().toString();
                presenter.signIn(email, password)
            }

            R.id.btn_sign_up -> signUp(view)

            R.id.iv_cover -> {
                et_email.setText("ryst@ma.ru")
                et_password.setText("rystam")
            }

            R.id.tv_ruslan -> {
                et_email.setText("rast@ma.ru")
                et_password.setText("rastamka")

            }

            R.id.tv_valera -> {
                et_email.setText("valera@ma.ru")
                et_password.setText("valera")

            }

            R.id.tv_azat -> {
                et_email.setText("azat@ma.ru")
                et_password.setText("azat")

            }

            R.id.tv_damir -> {
                et_email.setText("damir@ma.ru")
                et_password.setText("damir")

            }

            R.id.tv_aygul -> {
                et_email.setText("aygul@ma.ru")
                et_password.setText("aygul")

            }

            R.id.tv_ivan -> {
                et_email.setText("ivan98@ma.ru")
                et_password.setText("ivanov")

            }


        }
    }

    override fun showError() {
        ti_email.error = getString(R.string.enter_correct_name)
        ti_password.error = getString(R.string.enter_correct_password)
    }

    override fun createCookie() {
        activity?.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            val editor = it.edit()
            editor.putString(ORIGINAL_TOKEN, TOKEN)
            editor.putString(USER_ITEM, gson.toJson(currentUser))
            editor.apply()
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackCommandClick()
        return true
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance() = SignInFragment()
    }
}