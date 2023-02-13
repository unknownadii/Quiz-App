package com.aditya.quizapp.models.addQuestionsToQuiz.request

data class Question(
    val correct_ans: String,
    val option: List<String>,
    val question: String
)