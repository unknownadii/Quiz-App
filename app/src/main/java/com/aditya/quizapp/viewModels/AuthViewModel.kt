package com.aditya.quizapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.quizapp.models.StudentDashboardModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.repository.UserRepository
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository): ViewModel() {

    val userResponseLiveData : LiveData<AuthenticationResponseDataModel>
        get() = userRepository.userResponseLiveData

    val userLoginResponseLiveData : LiveData<AuthenticationResponseDataModel?>
    get() = userRepository.userLoginResponseLiveData

    val studentDashboardModelLiveData : LiveData<StudentDashboardModel?>
        get() = userRepository.studentDashboardModelLiveData


    fun registerUser(userRequest: UserRegisterRequest){
         viewModelScope.launch {
            userRepository.userResister(userRequest)
         }
     }

    fun loginUser(loginRequest: RequestAuthenticationDataModel){
        viewModelScope.launch {
            userRepository.userLogin(loginRequest)
        }
    }

    fun studentDashboard(token: String){
        viewModelScope.launch {
            userRepository.studentDashboard(token)
        }
    }


}