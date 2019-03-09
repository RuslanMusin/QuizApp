package com.example.quiz.ui.login.fragments.login

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.Navigation.findNavController
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.quiz.R
import com.example.quiz.model.user.User
import com.example.quiz.ui.base.BaseFragment
import com.example.quiz.ui.navigation.NavigationView
import com.example.quiz.utils.Const.TAG
import com.example.quiz.utils.Const.TAG_LOG
import com.example.quiz.utils.Const.USER_DATA_PREFERENCES
import com.example.quiz.utils.Const.USER_PASSWORD
import com.example.quiz.utils.Const.USER_USERNAME
import kotlinx.android.synthetic.main.fragment_login.*
import javax.inject.Inject

class LoginFragment : BaseFragment(), LoginFragmentView, View.OnClickListener {

    @InjectPresenter
    lateinit var loginFragmentPresenter: LoginFragmentPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_login, container, false)
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

    private fun signUp(v: View) {
     /*   val options = NavOptions.Builder().apply {
            setEnterAnim(R.anim.slide_in_right)
            setExitAnim(R.anim.slide_out_left)
            setPopEnterAnim(R.anim.slide_in_left)
            setPopExitAnim(R.anim.slide_out_right)
        }.build()*/

        val args = Bundle()
        args.putString(KEY, "Button")
        findNavController(v).navigate(R.id.signUpAciton)
    }

    private fun checkUserSession() {
        activity?.getSharedPreferences(USER_DATA_PREFERENCES, Context.MODE_PRIVATE)?.let {
            if (it.contains(USER_USERNAME)) {
                val email: String = it.getString(USER_USERNAME, "")
                val password: String = it.getString(USER_PASSWORD, "")
                loginFragmentPresenter.signIn(email, password)
            }
        }
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

    private fun setListeners() {
        btn_enter.setOnClickListener(this)
        btn_sign_up.setOnClickListener(this)
        iv_cover.setOnClickListener(this)
        tv_name.setOnClickListener(this)
    }

    override fun onClick(view: View) {

        when (view.id) {

            R.id.btn_enter -> {
                val email = et_email.getText().toString();
                val password = et_password.getText().toString();
                loginFragmentPresenter.signIn(email, password)
            }

            R.id.tv_name -> {
                et_email.setText("rast@ma.ru")
                et_password.setText("rastamka")

            }

            R.id.iv_cover -> {
                et_email.setText("Прохор.Куклев.Брониславович")
                et_password.setText("Прохор.Куклев.Брониславович")
            }

            R.id.btn_sign_up -> signUp(view)
        }
    }

    override fun goToProfile(curator: User) {
        Log.d(TAG,"login")
        val args = Bundle()
        args.putString(KEY, "Button")
        findNavController(btn_enter).navigate(R.id.action_loginFragment_to_studentFragment2)
        Log.d(TAG,"loginAfter")
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

    companion object {

        const val KEY = "TITLE"
    }
}