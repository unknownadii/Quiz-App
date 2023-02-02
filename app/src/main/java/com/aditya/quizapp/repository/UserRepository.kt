package com.example.quizapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel

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
            _responseTeacherDashBoard?.postValue(result.body())
        } else {
            _responseTeacherDashBoard.postValue(null)
        }
    }

}