package com.example.quiz.api

import com.example.quiz.utils.Const.BASE_URL
import com.example.quiz.utils.Const.TIME_FORMAT
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ApiFactory {

    companion object {

        /*private fun buildRetrofit(): Retrofit {
            val gson = GsonBuilder().setDateFormat(TIME_FORMAT).create()

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }

        private fun buildLoginRetrofit(): Retrofit {

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(OkHttpProvider.provideClient())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
        }*/
    }
}