package com.aditya.quizapp.models.submitQuizQuestion.Response

data class ResponseSubmittedQuizQuestions(
    val Message: String,
    val `data`: Data,
    val given: Boolean
)