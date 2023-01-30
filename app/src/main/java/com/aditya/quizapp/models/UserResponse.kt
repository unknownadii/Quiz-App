package com.aditya.quizapp.models

data class UserResponse(
    val Message: String,
    val `data`: String,
    val tokens: Tokens
)