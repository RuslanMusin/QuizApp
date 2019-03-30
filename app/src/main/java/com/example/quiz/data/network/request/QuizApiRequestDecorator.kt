package com.example.quiz.data.network.request

import com.example.quiz.data.network.exception.NoInternetConnectionException
import com.example.quiz.data.network.exception.TimeOutException
import com.example.quiz.data.network.exception.UnknownException
import com.example.quiz.data.network.exception.domain.DomainException
import com.example.quiz.presentation.model.auth.LoginResult
import com.example.quiz.presentation.model.user.Lector
import com.example.quiz.presentation.model.user.User
import io.reactivex.*
import retrofit2.adapter.rxjava2.Result
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class QuizApiRequestDecorator(val apiRequest: QuizApiRequest) : QuizApiRequest {

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
        return apiRequest
                .login(user)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun logout(): Completable {
        return apiRequest
                .logout()
                .compose(ApiRequestErrorCompletableTransformer())
    }

    override fun getCurator(id: String): Single<User> {
        return apiRequest
                .getCurator(id)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getAllCurators(): Single<List<User>> {
        return apiRequest
                .getAllCurators()
                .compose(ApiRequestErrorSingleTransformer())

    }

    override fun updateCurator(id: String, curator: User): Single<User> {
        return apiRequest
                .updateCurator(id, curator)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun signUp(user: User): Single<Lector> {
        return apiRequest
                .signUp(user)
                .compose(ApiRequestErrorSingleTransformer())
    }
}