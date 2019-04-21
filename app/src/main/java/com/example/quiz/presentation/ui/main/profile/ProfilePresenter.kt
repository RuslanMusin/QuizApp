package com.example.quiz.presentation.ui.main.profile

import android.util.Log
import com.arellomobile.mvp.InjectViewState
import com.example.quiz.R
import com.example.quiz.data.repository.auth.AuthRepository
import com.example.quiz.QuizApplication
import com.example.quiz.presentation.base.BasePresenter
import com.example.quiz.presentation.rx.transformer.PresentationCompletableTransformer
import com.example.quiz.presentation.rx.transformer.PresentationSingleTransformer
import com.example.quiz.presentation.ui.Screens
import com.example.quiz.presentation.util.Const.ORIGINAL_TOKEN
import com.example.quiz.presentation.util.Const.TOKEN
import com.example.quiz.presentation.util.exceptionprocessor.ExceptionProcessor
import io.reactivex.disposables.CompositeDisposable
import ru.terrakok.cicerone.Router
import javax.inject.Inject

@InjectViewState
class ProfilePresenter @Inject constructor() : BasePresenter<ProfileView>() {
    @Inject
    lateinit var authRepository: AuthRepository
    @Inject
    lateinit var exceptionProcessor: ExceptionProcessor
    @Inject
    lateinit var router: Router

    fun logout() {
        authRepository
                .logout()
                .compose(PresentationSingleTransformer())
                .doOnSubscribe { viewState.showProgressDialog() }
                .subscribe({
                    TOKEN = ORIGINAL_TOKEN
                    viewState.removeCookie()
                    onLogoutClick()
                }, {
                    viewState.showSnackBar(exceptionProcessor.processException(it))
                }).disposeWhenDestroy()
    }

    fun onBackClick() {
        router.exit()
    }

    fun onTestListClick() {
        router.replaceScreen(Screens.TestListScreen())
    }

    fun onLogoutClick() {
        router.replaceScreen(Screens.SignInScreen())
    }

}