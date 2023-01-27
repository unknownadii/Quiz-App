package com.example.quizapplication.api

import com.example.quizapplication.models.LoginRequest
import com.example.quizapplication.models.StudentDashboard
import com.example.quizapplication.models.UserRequest
import com.example.quizapplication.models.UserResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserApi {
    @POST("/register/")
    suspend fun signup(@Body userRequest: UserRequest):Response<UserResponse>

    @POST("/login/")
    suspend fun signin(@Body loginRequest: LoginRequest):Response<UserResponse>

    @GET("/studentDashboard/")
    suspend fun studentDashboard():Response<StudentDashboard>
}