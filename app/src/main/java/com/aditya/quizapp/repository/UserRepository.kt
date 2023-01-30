package com.aditya.quizapp.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.aditya.quizapp.models.StudentDashboardModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.example.quizapplication.api.UserApi

class UserRepository(private val userApi: UserApi) {


   private val _userResponseLiveData = MutableLiveData<AuthenticationResponseDataModel>()
    val userResponseLiveData: LiveData<AuthenticationResponseDataModel>
        get() = _userResponseLiveData

   private val _userLoginResponseLiveData = MutableLiveData<AuthenticationResponseDataModel?>()
    val userLoginResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = _userLoginResponseLiveData

    private val _studentDashboardModelLiveData = MutableLiveData<StudentDashboardModel?>()
    val studentDashboardModelLiveData: LiveData<StudentDashboardModel?>
        get() = _studentDashboardModelLiveData




    suspend fun userResister(userRequest: UserRegisterRequest) {
        val response = userApi.signUp(userRequest)

        if (response.isSuccessful && response.body() != null) {
            _userResponseLiveData.postValue(response.body()!!)
        } else if (response.errorBody() != null) {
            _userResponseLiveData.postValue(error("something went wrong"))
        } else {
            _userResponseLiveData.postValue(error("something went wrong"))

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

    suspend fun studentDashboard(token: String){
        val result = userApi.studentDashboard(token)
        if (result.isSuccessful && result.body() != null) {
            _studentDashboardModelLiveData.postValue(result.body())
        } else {
            _studentDashboardModelLiveData?.postValue(null)
        }
    }

}