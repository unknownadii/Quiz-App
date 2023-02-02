package com.aditya.quizapp.models.getQuestion

data class Data(
    val id: Int,
    val options: List<String>,
    val question: String
)