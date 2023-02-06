package com.aditya.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel
import com.aditya.quizapp.models.subjectChoiceTeacher.ResponseSubjectChoice

class UserRepository(private val userApi: UserApi) {

    private val _userRegisterResponseLiveData = MutableLiveData<AuthenticationResponseDataModel?>()
    val userRegisterResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = _userRegisterResponseLiveData

    private val _userLoginResponseLiveData = MutableLiveData<AuthenticationResponseDataModel?>()
    val userLoginResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = _userLoginResponseLiveData

    private val _responseTeacherDashBoard = MutableLiveData<ResponseTeacherDashboardDataModel?>()
    val responseTeacherDashBoard: LiveData<ResponseTeacherDashboardDataModel?>
        get() = _responseTeacherDashBoard

    private val _responseTeacherAddSubject = MutableLiveData<ResponseAddSubjectTeacherDataModel?>()
    val responseTeacherAddSubject: LiveData<ResponseAddSubjectTeacherDataModel?>
        get() = _responseTeacherAddSubject

    private val _responseTeacherSubjectChoice = MutableLiveData<ResponseSubjectChoice?>()
    val responseTeacherSubjectChoice: LiveData<ResponseSubjectChoice?>
        get() = _responseTeacherSubjectChoice

    suspend fun userResister(userRequest: UserRegisterRequest) {
        val result = userApi.signUp(userRequest)
        val data = result.body()
        val error = result.errorBody()
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

    suspend fun teacherAddSubjectData(accessTokens: String, subjectName: TeacherAddSubjectDataModel) {
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

}