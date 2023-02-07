package com.aditya.quizapp.api

import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel
import com.aditya.quizapp.models.studentDashboardModel.StudentDashboardModel
import com.aditya.quizapp.models.subjectChoiceTeacher.ResponseSubjectChoice
import com.aditya.quizapp.models.viewSubjectQuiz.ResponseViewSubjectQuiz
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import javax.security.auth.Subject

interface UserApi {
    @POST("/register")
    suspend fun signUp(
        @Body userRequest: UserRegisterRequest
    ): Response<AuthenticationResponseDataModel>

    @POST("/login")
    suspend fun signIn(
        @Body loginRequest: RequestAuthenticationDataModel
    ): Response<AuthenticationResponseDataModel>

    @GET("/studentDashboard")
    suspend fun studentDashboard(
        @Header("Authorization") accessTokens: String
    ): Response<StudentDashboardModel>

    @GET("/teacherDashboard")
    suspend fun teacherDashboard(
        @Header("Authorization") accessTokens: String
    ): Response<ResponseTeacherDashboardDataModel>

    @POST("/addSubject")
    suspend fun teacherAddSubject(
        @Header("Authorization") accessTokens: String,
        @Body subjectName: TeacherAddSubjectDataModel
    ): Response<ResponseAddSubjectTeacherDataModel>

    @GET("/subjectNames")
    suspend fun teacherSubjectChoice(): Response<ResponseSubjectChoice>

    @GET("/viewQuizes/{subject}")
    suspend fun viewSubjectQuiz(
        @Header("Authorization") accessTokens: String,
        @Path("subject") subject: String
    ): Response<ResponseViewSubjectQuiz>
}