package com.example.quizapplication.retrofit

import com.aditya.quizapp.utils.Constants.BASE_URl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    //logger
    private val logger = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create Okhttp Client
    private val okHttp = OkHttpClient.Builder().addInterceptor(logger)

    fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URl)
            .client(okHttp.build())
            .build()
    }

}