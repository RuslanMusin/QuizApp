package com.example.quiz.presentation.ui.auth.signup

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.example.quiz.R
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.util.Const
import com.example.quiz.presentation.util.Const.currentUser
import kotlinx.android.synthetic.main.fragment_sign_up.*
import javax.inject.Inject
import javax.inject.Provider

class SignUpFragment : BaseFragment(), SignUpView, BackButtonListener, View.OnClickListener {

    @InjectPresenter
    lateinit var presenter: SignUpPresenter

    @Inject
    lateinit var presenterProvider: Provider<SignUpPresenter>

    @ProvidePresenter
    fun providePresenter(): SignUpPresenter = presenterProvider.get()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViews()
    }

    private fun initViews() {
        setActionBar(toolbar)
        setListeners()
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_up -> {
                val user = buildUser()
                presenter.createAccount(user)
            }

            R.id.btn_login -> goToLogin()

            R.id.iv_cover -> setupText()
        }
    }

    private fun setupText() {
        et_email.setText("rast@ma.ru")
        et_password.setText("rastamka")
        et_lastname.setText("rastamka")
        et_name.setText("rastamka")
    }

    private fun buildUser(): User {
        val user = User()
        user.email = et_email.text.toString()
        user.password = et_password.text.toString()
        user.username = user.email
        user.name = et_name.text.toString()
        user.lastname = et_lastname.text.toString()
        return user
    }

    override fun showError() {
        ti_email.error = getString(R.string.enter_correct_name)
        ti_password.error = getString(R.string.enter_correct_password)
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

    private fun goToLogin() {
        presenter.onSignInCommandClick()
    }



    override fun onBackPressed(): Boolean {
        presenter.onBackCommandClick()
        return true
    }

    override fun createCookie() {
        activity?.getSharedPreferences(Const.USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            val editor = it.edit()
            editor.putString(Const.ORIGINAL_TOKEN, Const.TOKEN)
            editor.putString(Const.USER_ITEM, Const.gson.toJson(currentUser))
            editor.apply()
        }
    }

    companion object {

        fun newInstance(args: Bundle): Fragment {
            val fragment = SignUpFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): Fragment {
            val fragment = SignUpFragment()
            return fragment
        }
    }
}