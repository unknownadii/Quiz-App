package com.example.quizapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quizapplication.retrofit.NetworkResult
import com.example.quizapplication.repository.UserRepository
import com.example.quizapplication.models.LoginRequest
import com.example.quizapplication.models.UserRequest
import com.example.quizapplication.models.UserResponse
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<NetworkResult<UserResponse>>
        get() = userRepository.userResponseLiveData

     fun registerUser(userRequest: UserRequest){
         viewModelScope.launch {
            userRepository.userResister(userRequest)
         }
     }

    fun loginUser(loginRequest: LoginRequest){
        viewModelScope.launch {
            userRepository.userLogin(loginRequest)
        }
    }


}