package com.example.quizapplication.models

data class UserRequest(
    val contact: String,
    val dob: String,
    val email: String,
    val name: String,
    val password: String,
    val user: String
)