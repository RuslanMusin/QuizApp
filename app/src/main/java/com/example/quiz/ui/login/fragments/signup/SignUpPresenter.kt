package com.example.quiz.ui.login.fragments.signup

import android.text.TextUtils
import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.model.user.User
import com.example.quiz.repository.auth.AuthRepository
import com.example.quiz.repository.curator.UserRepository
import com.example.quiz.ui.base.App
import com.example.quiz.ui.base.BasePresenter
import com.example.quiz.utils.Const
import com.example.quiz.utils.Const.TAG_LOG
import com.example.quiz.utils.ErrorUtils
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class SignUpPresenter: BasePresenter<SignUpView>() {

    init {
        App.sAppComponent.inject(this)
    }


    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var authRepository: AuthRepository

    @Inject
    lateinit var userRepository: UserRepository

    internal fun createAccount(user: User) {
        Log.d(TAG_LOG, "createAccount")
        if (!validateForm(user)) {
            return
        }

        viewState.showProgressDialog(R.string.progress_message)

        val disposable = userRepository.createUser(user).subscribe { res ->
            val response = res?.response()
            if(response == null) {
                Log.d(TAG_LOG, "login response is null")
                Log.d(TAG_LOG, "error = ${res?.error()?.message}")
                viewState.showSnackBar(R.string.connection_reset)
                viewState.hideProgressDialog()
            }
            res?.response()?.let {
                Log.d(TAG_LOG, res.response()?.body().toString())
                if(it.isSuccessful) {
                    val loginResult = it.body()
                    Log.d(TAG_LOG, "signInWithEmail:success")
                    loginResult?.let {result ->
                        updateUI(result)
                        viewState.hideProgressDialog()
                    }
                } else {
//                    val error = ErrorUtils.parseError(it)
                    viewState.hideProgressDialog()
                    viewState.showSnackBar(R.string.error_authentication)
                    viewState.showError()
                    Log.d(TAG_LOG, "err message =  ${it.message()}")
                }

            }
        }
        compositeDisposable.add(disposable)
    }

    private fun validateForm(user: User): Boolean {
        return checkEmail(user.email) && checkPassword(user.password)
    }

    private fun checkEmail(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            viewState.showEmailError(true)
            false
        } else {
            viewState.showEmailError(false)
            true
        }
    }

    private fun checkPassword(password: String): Boolean {
        return if (TextUtils.isEmpty(password)) {
            viewState.showPasswordError(true)
            false
        } else {
            viewState.showPasswordError(false)
            true
        }
    }


    private fun updateUI(user: User) {
        viewState.hideProgressDialog()
        viewState.goToProfile(user)
    }

}