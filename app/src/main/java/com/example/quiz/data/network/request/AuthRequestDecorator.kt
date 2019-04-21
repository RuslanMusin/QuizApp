package com.example.quiz.data.network.request

import com.example.quiz.data.network.error.LoginSingleErrorFunction
import com.example.quiz.data.network.exception.NoInternetConnectionException
import com.example.quiz.data.network.exception.TimeOutException
import com.example.quiz.data.network.exception.UnknownException
import com.example.quiz.data.network.exception.domain.DomainException
import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.user.User
import io.reactivex.*
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class AuthRequestDecorator(val authApiRequest: AuthApiRequest) : AuthApiRequest {

    companion object {
        fun processApiThrowable(t: Throwable): Throwable {
            return when(t) {
                is DomainException -> t
                is UnknownHostException -> NoInternetConnectionException()
                is SocketTimeoutException -> TimeOutException()
                else -> UnknownException()
            }
        }
    }

    private class ApiRequestErrorSingleTransformer<T> : SingleTransformer<T, T> {
        override fun apply(upstream: Single<T>): SingleSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Single.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorObservableTransformer<T> : ObservableTransformer<T, T> {
        override fun apply(upstream: Observable<T>): ObservableSource<T> {
            return upstream.onErrorResumeNext { t: Throwable -> Observable.error(processApiThrowable(t)) }
        }
    }

    private class ApiRequestErrorCompletableTransformer : CompletableTransformer {
        override fun apply(upstream: Completable): CompletableSource {
            return upstream.onErrorResumeNext { t: Throwable -> Completable.error(processApiThrowable(t)) }
        }
    }

    override fun login(user: User): Single<LoginResult> {
        return authApiRequest
            .login(user)
            .onErrorResumeNext(LoginSingleErrorFunction())
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun signUp(user: User): Single<User> {
        return authApiRequest
            .signUp(user)
            .compose(ApiRequestErrorSingleTransformer())
    }
}