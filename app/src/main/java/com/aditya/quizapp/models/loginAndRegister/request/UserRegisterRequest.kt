package com.aditya.quizapp.models.loginAndRegister.request

data class UserRegisterRequest(
    val contact: String,
    val dob: String,
    val email: String,
    val name: String,
    val password: String,
    val user: String
)