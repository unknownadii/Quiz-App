package com.example.quizapplication.models

data class UserResponse(
    val Message: String,
    val `data`: String,
    val tokens: Tokens
)