package com.example.quiz.utils

import com.example.quiz.model.common.APIError
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Response
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject
import javax.inject.Named


object ErrorUtils {

    @set:Inject
    @Named("main_retrofit")
    lateinit var retrofit: Retrofit

    val errorConverter: Converter<ResponseBody, APIError> = retrofit.responseBodyConverter(APIError::class.java, arrayOfNulls<Annotation>(0))

    fun parseError(response: Response<*>): APIError {

        var error = APIError()

        try {
            response.errorBody()?.let { error = errorConverter.convert(it) }
        } catch (e: IOException) {
            return APIError()
        }

        return error
    }
}