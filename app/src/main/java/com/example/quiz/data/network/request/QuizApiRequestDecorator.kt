package com.example.quiz.data.network.request

import com.example.quiz.data.network.exception.NoInternetConnectionException
import com.example.quiz.data.network.exception.TimeOutException
import com.example.quiz.data.network.exception.UnknownException
import com.example.quiz.data.network.exception.domain.DomainException
import com.example.quiz.presentation.model.common.ElementId
import com.example.quiz.presentation.model.result.AuthorResult
import com.example.quiz.presentation.model.test.Test
import com.example.quiz.presentation.model.test.TestResult
import com.example.quiz.presentation.model.test.TestSubmit
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

    override fun logout(): Single<JsonObject> {
        return apiRequest
                .logout()
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

    override fun postTestResult(id: Int, testSubmit: TestSubmit): Single<TestSubmit> {
        return apiRequest
            .postTestResult(id, testSubmit)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getParticipantTestResult(testId: Int, userId: Int): Single<TestResult> {
        return apiRequest
            .getParticipantTestResult(testId, userId)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun getAuthorTestResults(id: Int): Single<AuthorResult> {
        return apiRequest
            .getAuthorTestResults(id)
            .compose(ApiRequestErrorSingleTransformer())
    }

    override fun findUser(): Single<User> {
        return apiRequest
            .findUser()
            .compose(ApiRequestErrorSingleTransformer())
    }
}