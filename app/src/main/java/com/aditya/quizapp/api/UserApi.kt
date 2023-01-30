package com.aditya.quizapp.api

import com.aditya.quizapp.models.StudentDashboardModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface UserApi {
    @POST("/register/")
    suspend fun signUp(@Body userRequest: UserRegisterRequest): Response<AuthenticationResponseDataModel>

    @POST("/login/")
    suspend fun signIn(@Body loginRequest: RequestAuthenticationDataModel): Response<AuthenticationResponseDataModel>

    @GET("/studentDashboard/")
    suspend fun studentDashboard(@Header("Authorization") token: String): Response<StudentDashboardModel>
}