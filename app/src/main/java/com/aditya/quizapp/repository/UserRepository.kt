package com.example.quizapplication.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.quizapplication.api.UserApi
import com.example.quizapplication.models.LoginRequest
import com.example.quizapplication.models.UserRequest
import com.example.quizapplication.models.UserResponse
import com.example.quizapplication.retrofit.NetworkResult

class UserRepository(private val userApi: UserApi) {


    val _userResponseLiveData = MutableLiveData<NetworkResult<UserResponse>>()
    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = _userResponseLiveData


    suspend fun userResister(userRequest: UserRequest){
        val response = userApi.signup(userRequest)

        if (response.isSuccessful && response.body() != null){
            _userResponseLiveData.postValue(NetworkResult.Success(response.body()!!))
        }
        else if (response.errorBody() != null){
            _userResponseLiveData.postValue(NetworkResult.Error("something went wrong"))
        }else{
            _userResponseLiveData.postValue(NetworkResult.Error("something went wrong"))

        }


    }
    suspend fun userLogin(loginRequest: LoginRequest){
        val response = userApi.signin(loginRequest)

    }

}