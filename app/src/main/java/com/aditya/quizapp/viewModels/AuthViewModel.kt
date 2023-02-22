package com.aditya.quizapp.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.quizapp.models.addQuestionsToQuiz.request.RequestAddQuizQuestion
import com.aditya.quizapp.models.addQuestionsToQuiz.response.ResponseAddQuizQuestion
import com.aditya.quizapp.models.addSubjectTeacher.request.TeacherAddSubjectDataModel
import com.aditya.quizapp.models.addSubjectTeacher.response.ResponseAddSubjectTeacherDataModel
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.models.loginAndRegister.response.AuthenticationResponseDataModel
import com.aditya.quizapp.repository.UserRepository
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
import kotlinx.coroutines.launch

class AuthViewModel(private val userRepository: UserRepository) : ViewModel() {
    val userRegisterResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = userRepository.userRegisterResponseLiveData

    val userLoginResponseLiveData: LiveData<AuthenticationResponseDataModel?>
        get() = userRepository.userLoginResponseLiveData

    val userLogoutResponseLiveData: LiveData<ResponseLogoutDataModel?>
        get() = userRepository.userLogoutResponseLiveData

    val responseTeacherDashboardLiveData: LiveData<ResponseTeacherDashboardDataModel?>
        get() = userRepository.responseTeacherDashBoard

    val responseTeacherSubjectNameLiveData: LiveData<ResponseAddSubjectTeacherDataModel?>
        get() = userRepository.responseTeacherAddSubject

    val responseTeacherSubjectChoiceLiveData: LiveData<ResponseSubjectChoice?>
        get() = userRepository.responseTeacherSubjectChoice

    val responseViewSubjectQuizLiveData: LiveData<ResponseViewSubjectQuiz?>
        get() = userRepository.responseViewSubjectQuiz

    val responseViewSubjectQuestionLiveData: LiveData<ResponseSubjectQuestion?>
        get() = userRepository.responseSubjectQuestion

    val responseAddQuizQuestionLiveData: LiveData<ResponseAddQuizQuestion?>
        get() = userRepository.responseAddQuizQuestion

    val responseStudentDashboardLiveData: LiveData<StudentDashboardModel?>
        get() = userRepository.responseStudentDashBoard

    val responseStudentSubjectQuizLiveData: LiveData<StudentDashboardModel?>
        get() = userRepository.responseStudentSubjectQuiz

    val responseStudentQuizQuestionLiveData: LiveData<ResponseSubmitQuizStudent?>
        get() = userRepository.responseStudentQuizQuestion

    val responseSubmitQuizQuestionLiveData: LiveData<ResponseSubmittedQuizQuestions?>
        get() = userRepository.responseSubmitQuizQuestion

    val responseLeaderBoardLiveData: LiveData<StudentDashboardModel?>
        get() = userRepository.responseLeaderBoard

    val responseLeaderBoardQuizLiveData: LiveData<StudentDashboardModel?>
        get() = userRepository.responseLeaderBoardQuiz

    val responseLeaderBoardScoreLiveData: LiveData<ResponseLeaderBoardScore?>
        get() = userRepository.responseLeaderBoardScore

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

    fun logoutUser(accessToken: String, refreshToken: LogoutDataModel) {
        viewModelScope.launch {
            userRepository.userLogout(accessToken, refreshToken)
        }
    }

    fun getTeacherDashBoard(accessToken: String) {
        viewModelScope.launch {
            userRepository.teacherDashboardData(accessToken)
        }
    }

    fun getTeacherAddSubject(accessToken: String, subjectName: TeacherAddSubjectDataModel) {
        viewModelScope.launch {
            userRepository.teacherAddSubjectData(accessToken, subjectName)
        }
    }

    fun getTeacherSubjectChoice() {
        viewModelScope.launch {
            userRepository.teacherSubjectChoice()
        }
    }

    fun getSubjectQuiz(accessToken: String, subject: String) {
        viewModelScope.launch {
            userRepository.viewSubjectQuiz(accessToken, subject)
        }
    }

    fun getSubjectQuestion(accessToken: String, subject: String, quizName: String) {
        viewModelScope.launch {
            userRepository.viewSubjectQuestion(accessToken, subject, quizName)
        }
    }

    fun addQuizQuestion(
        accessToken: String,
        subjectName: String,
        question: RequestAddQuizQuestion
    ) {
        viewModelScope.launch {
            userRepository.addSubjectQuestion(accessToken, subjectName, question)
        }
    }


    fun getStudentDashBoard(accessToken: String) {
        viewModelScope.launch {
            userRepository.studentDashboardData(accessToken)
        }
    }

    fun getStudentSubjectQuiz(accessToken: String, subjectName: String) {
        viewModelScope.launch {
            userRepository.getSubjectQuizStudent(accessToken, subjectName)
        }
    }

    fun getStudentQuizQuestion(
        accessToken: String,
        subjectName: String,
        quizName: String
    ) {
        viewModelScope.launch {
            userRepository.getQuizQuestionStudent(accessToken, subjectName, quizName)
        }
    }

    fun submitStudentQuizQuestion(
        accessToken: String,
        subjectName: String,
        quizName: String,
        answersList: RequestSubmitQuizQuestion
    ) {
        viewModelScope.launch {
            userRepository.submitQuizQuestionStudent(
                accessToken,
                subjectName,
                quizName,
                answersList
            )
        }
    }

    fun getLeaderBoard(accessToken: String) {
        viewModelScope.launch {
            userRepository.getLeaderBoard(accessToken)
        }
    }

    fun getLeaderBoardQuiz(accessToken: String, subjectName: String) {
        viewModelScope.launch {
            userRepository.getLeaderBoardQuiz(accessToken, subjectName)
        }
    }

    fun getLeaderBoardScore(accessToken: String, subjectName: String, quizName: String) {
        viewModelScope.launch {
            userRepository.getLeaderBoardScore(accessToken, subjectName, quizName)
        }
    }
}