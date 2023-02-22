package com.aditya.quizapp.api

import com.aditya.quizapp.models.addQuestionsToQuiz.request.RequestAddQuizQuestion
import com.aditya.quizapp.models.addQuestionsToQuiz.response.ResponseAddQuizQuestion
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.logoutDataModel.LogoutDataModel
import com.aditya.quizapp.models.logoutDataModel.ResponseLogoutDataModel
import com.aditya.quizapp.models.responseLeaderboardScore.ResponseLeaderBoardScore
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel
import com.aditya.quizapp.models.studentDashboardModel.StudentDashboardModel
import com.aditya.quizapp.models.subjectChoiceTeacher.ResponseSubjectChoice
import com.aditya.quizapp.models.subjectQuestion.ResponseSubjectQuestion
import com.aditya.quizapp.models.submitQuestionStudent.ResponseSubmitQuizStudent
import com.aditya.quizapp.models.submitQuizQuestion.Request.RequestSubmitQuizQuestion
import com.aditya.quizapp.models.submitQuizQuestion.Response.ResponseSubmittedQuizQuestions
import com.aditya.quizapp.models.viewSubjectQuiz.ResponseViewSubjectQuiz
import retrofit2.Response
import retrofit2.http.*

interface UserApi {
    @POST("/register")
    suspend fun signUp(
        @Body userRequest: UserRegisterRequest
    ): Response<AuthenticationResponseDataModel>

    @POST("/login")
    suspend fun signIn(
        @Body loginRequest: RequestAuthenticationDataModel
    ): Response<AuthenticationResponseDataModel>

    @POST("/logout")
    suspend fun logout(
        @Header("Authorization") accessTokens: String,
        @Body refresh: LogoutDataModel
    ): Response<ResponseLogoutDataModel>

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

    @GET("/viewQuiz/{subject}/{quizName}")
    suspend fun viewSubjectQuestion(
        @Header("Authorization") accessTokens: String,
        @Path("subject") subject: String,
        @Path("quizName") quizName: String
    ): Response<ResponseSubjectQuestion>

    @POST("/addQuiz/{subjectName}")
    suspend fun addSubjectQuestion(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
        @Body questions: RequestAddQuizQuestion
    ): Response<ResponseAddQuizQuestion>

    @GET("/startQuiz/{subjectName}")
    suspend fun getSubjectQuizStudent(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
    ): Response<StudentDashboardModel>

    @GET("/startQuiz/{subjectName}/{quizName}")
    suspend fun getQuizQuestionStudent(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
        @Path("quizName") quizName: String,
    ): Response<ResponseSubmitQuizStudent>

    @POST("/startQuiz/{subjectName}/{quizName}/")
    suspend fun submitQuizQuestionStudent(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
        @Path("quizName") quizName: String,
        @Body answerList: RequestSubmitQuizQuestion
    ): Response<ResponseSubmittedQuizQuestions>

    @GET("/leaderboard")
    suspend fun getLeaderBoard(
        @Header("Authorization") accessTokens: String
    ): Response<StudentDashboardModel>

    @GET("/leaderboard/{subjectName}")
    suspend fun getLeaderBoardQuiz(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
    ): Response<StudentDashboardModel>

    @GET("/leaderboard/{subjectName}/{quizName}")
    suspend fun getLeaderBoardScore(
        @Header("Authorization") accessTokens: String,
        @Path("subjectName") subjectName: String,
        @Path("quizName") quizName: String,
    ): Response<ResponseLeaderBoardScore>
}