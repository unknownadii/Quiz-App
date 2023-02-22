package com.aditya.quizapp.models.responseLeaderboardScore

data class Data(
    val id: Int,
    val quiz_id: String,
    val score: Int,
    val student_id: Int
)