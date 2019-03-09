package com.example.quiz.ui.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.model.user.User
import com.example.quiz.repository.auth.AuthRepository
import com.example.quiz.repository.curator.UserRepository
import com.example.quiz.ui.base.App
import com.example.quiz.ui.base.BasePresenter
import com.example.quiz.utils.Const
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

@InjectViewState
class StudentPresenter: BasePresenter<StudentView>() {

    init {
        App.sAppComponent.inject(this)
    }


    val compositeDisposable: CompositeDisposable = CompositeDisposable()

    @Inject
    lateinit var authRepository: AuthRepository

    internal fun logout() {
        Log.d(Const.TAG_LOG, "logout")

        viewState.showProgressDialog(R.string.progress_message)

        val disposable = authRepository.logout().subscribe { res ->
            val response = res?.response()
            if(response == null) {
                Log.d(Const.TAG_LOG, "login response is null")
                Log.d(Const.TAG_LOG, "error = ${res?.error()?.message}")
                viewState.showSnackBar(R.string.connection_reset)
                viewState.hideProgressDialog()
            }
            res?.response()?.let {
                Log.d(Const.TAG_LOG, res.response()?.body().toString())
                if(it.isSuccessful) {
                    val loginResult = it.body()
                    viewState.logout()
                    viewState.hideProgressDialog()

                } else {
//                    val error = ErrorUtils.parseError(it)
                    viewState.hideProgressDialog()
                    viewState.showSnackBar(R.string.error_authentication)
                    Log.d(Const.TAG_LOG, "err message =  ${it.message()}")
                }

            }
        }
        compositeDisposable.add(disposable)
    }

}