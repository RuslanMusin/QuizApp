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
import com.example.quiz.presentation.model.user.User
import com.example.quiz.presentation.base.BaseFragment
import com.example.quiz.presentation.base.navigation.BackButtonListener
import com.example.quiz.presentation.util.Const
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
        initViews()
    }

    private fun initViews() {
        setListeners()
    }

    private fun signUp(v: View) {
        presenter.onForwardCommandClick()
    }

    private fun checkUserSession() {
        activity?.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            if (it.contains(USER_USERNAME)) {
                val email: String = it.getString(USER_USERNAME, "")
                val password: String = it.getString(USER_PASSWORD, "")
                presenter.signIn(email, password)
            }
        }
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
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_enter -> {
                val email = et_email.getText().toString();
                val password = et_password.getText().toString();
                presenter.signIn(email, password)
            }

            R.id.iv_cover -> {
                et_email.setText("rast@ma.ru")
                et_password.setText("rastamka")
            }

            R.id.btn_sign_up -> signUp(view)
        }
    }

    override fun goToProfile(user: User) {
        Const.currentUser = user
        presenter.onNewRootCommandClick()
    }

    override fun showError() {
        ti_email.error = getString(R.string.enter_correct_name)
        ti_password.error = getString(R.string.enter_correct_password)
    }

    override fun createCookie(email: String, password: String) {
        activity?.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            if (!it.contains(USER_USERNAME)) {
                val editor = it.edit()
                editor.putString(USER_USERNAME, email)
                editor.putString(USER_PASSWORD, password)
                editor.apply()
            }
        }
    }

    override fun onBackPressed(): Boolean {
        presenter.onBackCommandClick()
        return true
    }

    companion object {

        private const val USER_DATA_PREFERENCES = "user_data"
        private const val USER_USERNAME = "user_username"
        private const val USER_PASSWORD = "user_password"

        fun newInstance(args: Bundle): Fragment {
            val fragment = SignInFragment()
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): Fragment {
            val fragment = SignInFragment()
            return fragment
        }
    }
}