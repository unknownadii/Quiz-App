package com.example.quizapplication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.example.quizapplication.repository.UserRepository
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.models.responseTeacherDashboard.ResponseTeacherDashboardDataModel
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userRegisterResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = userRepository.userRegisterResponseLiveData

    val userLoginResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = userRepository.userLoginResponseLiveData

    val responseTeacherDashboardLiveData: LiveData<ResponseTeacherDashboardDataModel?>
        get() = userRepository.responseTeacherDashBoard

    val responseTeacherSubjectNameLiveData: LiveData<ResponseAddSubjectTeacherDataModel?>
        get() = userRepository.responseTeacherAddSubject


    fun registerUser(userRequest: UserRegisterRequest) {
        viewModelScope.launch {
            userRepository.userResister(userRequest)
        }
    }

    fun loginUser(loginRequest: RequestAuthenticationDataModel) {
        viewModelScope.launch {
            userRepository.userLogin(loginRequest)
        }
    }

    fun getTeacherDashBoard(accessToken: String) {
        viewModelScope.launch {
            userRepository.teacherDashboardData(accessToken)
        }
    }
    fun getTeacherAddSubject(accessToken: String, subjectName: TeacherAddSubjectDataModel) {
        viewModelScope.launch {
            userRepository.teacherAddSubjectData(accessToken,subjectName)
        }
    }
}