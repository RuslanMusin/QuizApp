package com.example.quiz.data.network.request

import com.example.quiz.data.network.exception.NoInternetConnectionException
import com.example.quiz.data.network.exception.TimeOutException
import com.example.quiz.data.network.exception.UnknownException
import com.example.quiz.data.network.exception.domain.DomainException
import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.model.user.User
import com.google.gson.JsonObject
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

    override fun logout(): Completable {
        return apiRequest
                .logout()
                .compose(ApiRequestErrorCompletableTransformer())
    }

    override fun getUser(id: Int): Single<User> {
        return apiRequest
                .getUser(id)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getUsers(): Single<List<User>> {
        return apiRequest
                .getUsers()
                .compose(ApiRequestErrorSingleTransformer())

    }

    override fun updateUser(id: Int, curator: User): Single<User> {
        return apiRequest
                .updateUser(id, curator)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun signUp(user: User): Single<User> {
        return apiRequest
                .signUp(user)
                .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getTest(id: Int): Single<Test> {
        return apiRequest
            .getTest(id)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getTests(): Single<List<Test>> {
        return apiRequest
            .getTests()
            .compose(ApiRequestErrorSingleTransformer())

    }

    override fun getTestsByUser(id: Int): Single<List<Test>> {
        return apiRequest
            .getTestsByUser(id)
            .compose(ApiRequestErrorSingleTransformer())

    }

    override fun createTest(test: Test): Single<ElementId> {
        return apiRequest
            .createTest(test)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun openTest(id: Int): Single<JsonObject> {
        return apiRequest
            .openTest(id)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun closeTest(id: Int): Single<JsonObject> {
        return apiRequest
            .closeTest(id)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun postTestResult(id: Int, testResult: TestResult): Single<JsonObject> {
        return apiRequest
            .postTestResult(id, testResult)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getTestResult(testId: Int, userId: Int): Single<JsonObject> {
        return apiRequest
            .getTestResult(testId, userId)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getTestResults(id: Int): Single<JsonObject> {
        return apiRequest
            .getTestResults(id)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun findUser(): Single<User> {
        return apiRequest
            .findUser()
            .compose(ApiRequestErrorSingleTransformer())
    }
}