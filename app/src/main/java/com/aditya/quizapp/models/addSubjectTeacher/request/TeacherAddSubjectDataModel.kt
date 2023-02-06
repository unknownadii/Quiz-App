package com.aditya.quizapp.models.addSubjectTeacher.request

import com.google.gson.annotations.SerializedName


data class TeacherAddSubjectDataModel(
    @SerializedName("subject")
    val subject: String
)