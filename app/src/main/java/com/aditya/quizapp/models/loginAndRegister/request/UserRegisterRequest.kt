package com.aditya.quizapp.models.loginAndRegister.request

data class UserRegisterRequest(
    val email: String,
    val subjects: List<String>,
    val name: String,
    val user: String,
    val password: String,
    val contact: String,
    val dob: String,
)