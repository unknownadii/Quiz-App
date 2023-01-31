package com.aditya.quizapp.api

import com.aditya.quizapp.models.StudentDashboardModel
import com.aditya.quizapp.models.getQuestion.StudentQuestion
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("/register/")
    suspend fun signUp(@Body userRequest: UserRegisterRequest): Response<AuthenticationResponseDataModel>

    @POST("/login/")
    suspend fun signIn(@Body loginRequest: RequestAuthenticationDataModel): Response<AuthenticationResponseDataModel>

    @GET("/studentDashboard/")
    suspend fun studentDashboard(@Header("Authorization") token: String): Response<StudentDashboardModel>

    @GET("/quiz/{subject}/{page}/")
    suspend fun quizQuestions(@Header("Authorization") token: String,@Path("subject") sub: String,@Query("page") page: Int): Response<StudentQuestion>
}