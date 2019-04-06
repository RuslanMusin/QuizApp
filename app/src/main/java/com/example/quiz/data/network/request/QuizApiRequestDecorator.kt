package com.example.quiz.data.network.request

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

    override fun getUser(id: String): Single<User> {
        return apiRequest
                .getUser(id)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getUsers(): Single<List<User>> {
        return apiRequest
                .getUsers()
                .compose(ApiRequestErrorSingleTransformer())

    }

    override fun updateUser(id: String, curator: User): Single<User> {
        return apiRequest
                .updateUser(id, curator)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun signUp(user: User): Single<User> {
        return apiRequest
                .signUp(user)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getTest(id: String): Single<Test> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun getTests(): Single<List<Test>> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createTest(test: Test): Single<Test> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}