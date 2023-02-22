package com.aditya.quizapp.models.submitQuizQuestion.Response

data class Data(
    val id: Int,
    val quiz_id: String,
    val scores: Int,
    val student_id: Int
)