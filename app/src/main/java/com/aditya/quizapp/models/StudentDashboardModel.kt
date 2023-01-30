package com.aditya.quizapp.models

data class StudentDashboardModel(
    val Message: String,
    val `data`: List<Data>,
    val sub: List<String>
)