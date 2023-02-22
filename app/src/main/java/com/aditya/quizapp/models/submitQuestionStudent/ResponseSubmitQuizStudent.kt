package com.aditya.quizapp.models.submitQuestionStudent

data class ResponseSubmitQuizStudent(
    val Message: String,
    val data: List<Data>,
    val given: Boolean,
    val score: Int,
)