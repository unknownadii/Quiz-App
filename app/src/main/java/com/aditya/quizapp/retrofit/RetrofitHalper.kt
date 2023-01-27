package com.example.quizapplication.retrofit

import com.example.quizapplication.utils.Constants.BASE_URl
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitHalper {

    fun getInstance(): Retrofit{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URl)
            .build()
    }

}