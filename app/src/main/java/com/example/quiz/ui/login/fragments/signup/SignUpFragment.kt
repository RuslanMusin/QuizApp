package com.example.quiz.ui.login.fragments.signup

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.arellomobile.mvp.MvpAppCompatFragment
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.quiz.R
import com.example.quiz.model.user.Lector
import com.example.quiz.model.user.User
import com.example.quiz.ui.base.BaseFragment
import com.example.quiz.ui.login.fragments.login.LoginFragment.Companion.KEY
import com.example.quiz.ui.login.fragments.login.LoginFragmentPresenter
import com.example.quiz.ui.login.fragments.login.LoginFragmentView
import kotlinx.android.synthetic.main.fragment_sign_up.*

class SignUpFragment: BaseFragment(), SignUpView, View.OnClickListener {

    @InjectPresenter
    lateinit var signUpPresenter: SignUpPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_sign_up, container, false)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setBottomVisibility(false)
        initViews()
    }

    private fun initViews() {
        setListeners()
    }

    private fun setListeners() {
        btn_sign_up.setOnClickListener(this)
        btn_login.setOnClickListener(this)
        iv_cover.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_sign_up -> {
                val user = buildUser()
                signUpPresenter.createAccount(user)
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

    private fun buildUser(): Lector {
        val user = Lector()
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
        if(hasError) {
            ti_email.error = getString(R.string.enter_correct_name)
        } else {
            ti_email.error = null
        }

    }

    override fun showPasswordError(hasError: Boolean) {
        if(hasError) {
            ti_password.error = getString(R.string.enter_correct_password)
        } else {
            ti_password.error = null
        }

    }

    private fun goToLogin() {
        Navigation.findNavController(btn_login).navigate(R.id.action_signUpFragment_to_loginFragment2)
    }

    override fun goToProfile(user: User) {
        val args = Bundle()
        args.putString(KEY, "Button")
        Navigation.findNavController(btn_sign_up).navigate(R.id.action_signUpFragment_to_studentFragment2, args)
    }


    companion object {

        private val GALLERY_PHOTO = 0

        private val STANDART_PHOTO = 1
    }
}