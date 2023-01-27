package com.aditya.quizapp.models.loginAndRegister.response

data class AuthenticationResponseDataModel(
    val Message: String,
    val `data`: String,
    val tokens: Tokens
)