package com.example.quizapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.example.quizapplication.retrofit.NetworkResult
import com.example.quizapplication.repository.UserRepository
import com.example.quizapplication.models.LoginRequest
import com.example.quizapplication.models.UserRequest
import com.example.quizapplication.models.UserResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

    val userLoginResponseLiveData : LiveData<AuthenticationResponseDataModel?>
    get() = userRepository.userLoginResponseLiveData

     fun registerUser(userRequest: UserRequest){
         viewModelScope.launch {
            userRepository.userResister(userRequest)
         }
     }

    fun loginUser(loginRequest: RequestAuthenticationDataModel){
        viewModelScope.launch {
            userRepository.userLogin(loginRequest)
        }
    }


}