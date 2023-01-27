package com.example.quizapplication.api

import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.example.quizapplication.models.StudentDashboard
import com.example.quizapplication.models.UserRequest
import com.example.quizapplication.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("/register/")
    suspend fun signUp(@Body userRequest: UserRequest): Response<UserResponse>

    @POST("/login/")
    suspend fun signIn(@Body loginRequest: RequestAuthenticationDataModel): Response<AuthenticationResponseDataModel>

    @GET("/studentDashboard/")
    suspend fun studentDashboard(): Response<StudentDashboard>
}