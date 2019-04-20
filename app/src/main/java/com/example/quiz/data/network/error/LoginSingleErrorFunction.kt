package com.example.quiz.data.network.error

import com.example.quiz.data.network.exception.UnknownException
import com.example.quiz.data.network.exception.domain.InvalidEmailException
import com.example.quiz.presentation.model.auth.LoginResult
import com.google.gson.Gson
import com.google.gson.JsonObject
import io.reactivex.Completable
import io.reactivex.Single
import org.json.JSONObject
import retrofit2.HttpException

class LoginSingleErrorFunction : ErrorFunction<Single<LoginResult>> {
    override fun apply(t: Throwable): Single<LoginResult> {
        if (t is HttpException) {
            val error = t.response().errorBody()?.string()
            val errorText = JSONObject(error).getString("email")

            return when (errorText) {
                "Enter a valid email address." -> Single.error(InvalidEmailException())
                else -> Single.error(UnknownException())
            }
        }

        return Single.error(UnknownException())
    }
}