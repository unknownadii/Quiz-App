package com.aditya.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.models.addQuestionsToQuiz.request.RequestAddQuizQuestion
import com.aditya.quizapp.models.addQuestionsToQuiz.response.ResponseAddQuizQuestion
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel
import com.aditya.quizapp.models.studentDashboardModel.StudentDashboardModel
import com.aditya.quizapp.models.subjectChoiceTeacher.ResponseSubjectChoice
import com.aditya.quizapp.models.subjectQuestion.ResponseSubjectQuestion
import com.aditya.quizapp.models.viewSubjectQuiz.ResponseViewSubjectQuiz

class UserRepository(private val userApi: UserApi) {

    private val _userRegisterResponseLiveData = MutableLiveData<AuthenticationResponseDataModel?>()
    val userRegisterResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = _userRegisterResponseLiveData

    private val _userLoginResponseLiveData = MutableLiveData<AuthenticationResponseDataModel?>()
    val userLoginResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = _userLoginResponseLiveData


    private val _userLogoutResponseLiveData = MutableLiveData<Any?>()
    val userLogoutResponseLiveData: LiveData<Any?>
        get() = _userLogoutResponseLiveData

    private val _responseTeacherDashBoard = MutableLiveData<ResponseTeacherDashboardDataModel?>()
    val responseTeacherDashBoard: LiveData<ResponseTeacherDashboardDataModel?>
        get() = _responseTeacherDashBoard

    private val _responseTeacherAddSubject = MutableLiveData<ResponseAddSubjectTeacherDataModel?>()
    val responseTeacherAddSubject: LiveData<ResponseAddSubjectTeacherDataModel?>
        get() = _responseTeacherAddSubject

    private val _responseTeacherSubjectChoice = MutableLiveData<ResponseSubjectChoice?>()
    val responseTeacherSubjectChoice: LiveData<ResponseSubjectChoice?>
        get() = _responseTeacherSubjectChoice

    private val _responseViewSubjectQuiz = MutableLiveData<ResponseViewSubjectQuiz?>()
    val responseViewSubjectQuiz: LiveData<ResponseViewSubjectQuiz?>
        get() = _responseViewSubjectQuiz

    private val _responseSubjectQuestion = MutableLiveData<ResponseSubjectQuestion?>()
    val responseSubjectQuestion: LiveData<ResponseSubjectQuestion?>
        get() = _responseSubjectQuestion

    private val _responseAddQuizQuestion = MutableLiveData<ResponseAddQuizQuestion?>()
    val responseAddQuizQuestion: LiveData<ResponseAddQuizQuestion?>
        get() = _responseAddQuizQuestion

    private val _responseStudentDashBoard = MutableLiveData<StudentDashboardModel?>()
    val responseStudentDashBoard: LiveData<StudentDashboardModel?>
        get() = _responseStudentDashBoard

    suspend fun userResister(userRequest: UserRegisterRequest) {
        val result = userApi.signUp(userRequest)
        val data = result.body()
        if (result.isSuccessful && result.body() != null) {
            _userRegisterResponseLiveData?.postValue(result.body())
        } else {
            _userRegisterResponseLiveData?.postValue(data)
        }
    }

    suspend fun userLogin(loginRequest: RequestAuthenticationDataModel) {
        val result = userApi.signIn(loginRequest)
        if (result.isSuccessful && result.body() != null) {
            _userLoginResponseLiveData.postValue(result.body())
        } else {
            _userLoginResponseLiveData?.postValue(null)
        }
    }

    suspend fun userLogout(accessTokens: String, refreshToken: String) {
        val result = userApi.logout(accessTokens,refreshToken)
        if (result.isSuccessful && result.body() != null) {
            _userLogoutResponseLiveData.postValue(result.body())
        } else {
            _userLogoutResponseLiveData?.postValue(null)
        }
    }

    suspend fun teacherDashboardData(accessTokens: String) {
        val result = userApi.teacherDashboard(accessTokens)
        if (result.isSuccessful && result.body() != null) {
            _responseTeacherDashBoard.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseTeacherDashBoard.postValue(result.body())
        } else {
            _responseTeacherDashBoard.postValue(null)
        }
    }

    suspend fun teacherAddSubjectData(
        accessTokens: String,
        subjectName: TeacherAddSubjectDataModel
    ) {
        val result = userApi.teacherAddSubject(accessTokens, subjectName)
        if (result.isSuccessful && result.body() != null) {
            _responseTeacherAddSubject.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseTeacherAddSubject.postValue(result.body())
        } else {
            _responseTeacherDashBoard.postValue(null)
        }
    }

    suspend fun teacherSubjectChoice() {
        val result = userApi.teacherSubjectChoice()
        if (result.isSuccessful && result.body() != null) {
            _responseTeacherSubjectChoice.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseTeacherSubjectChoice.postValue(result.body())
        } else {
            _responseTeacherSubjectChoice.postValue(null)
        }
    }

    suspend fun viewSubjectQuiz(accessTokens: String, subject: String) {
        val result = userApi.viewSubjectQuiz(accessTokens, subject)
        if (result.isSuccessful && result.body() != null) {
            _responseViewSubjectQuiz.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseViewSubjectQuiz.postValue(result.body())
        } else {
            _responseViewSubjectQuiz.postValue(null)
        }
    }

    suspend fun viewSubjectQuestion(accessTokens: String, subject: String, quizName: String) {
        val result = userApi.viewSubjectQuestion(accessTokens, subject, quizName)
        if (result.isSuccessful && result.body() != null) {
            _responseSubjectQuestion.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseSubjectQuestion.postValue(result.body())
        } else {
            _responseSubjectQuestion.postValue(null)
        }
    }

    suspend fun addSubjectQuestion(
        accessTokens: String,
        subjectName: String,
        question: RequestAddQuizQuestion
    ) {
        val result = userApi.addSubjectQuestion(accessTokens, subjectName, question)
        if (result.isSuccessful && result.body() != null) {
            _responseAddQuizQuestion.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseAddQuizQuestion.postValue(result.body())
        } else {
            _responseAddQuizQuestion.postValue(null)
        }
    }

    suspend fun studentDashboardData(accessTokens: String) {
        val result = userApi.studentDashboard(accessTokens)
        if (result.isSuccessful && result.body() != null) {
            _responseStudentDashBoard.postValue(result.body())
        } else if (!result.isSuccessful && result.body() != null) {
            _responseStudentDashBoard.postValue(result.body())
        } else {
            _responseStudentDashBoard.postValue(null)
        }
    }

}