package com.aditya.quizapp.models.addQuestionsToQuiz.request

data class RequestAddQuizQuestion(
    val level: String,
    val questions: List<Question>
)