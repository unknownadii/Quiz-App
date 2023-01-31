package com.example.quizapplication.utils

import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.google.android.material.snackbar.Snackbar

object Constants {
    const val TAG = "QUIZCODE"
    const val BASE_URl: String = "https://48d3-49-249-44-114.in.ngrok.io"
    fun checkEmail(view: AppCompatEditText): Boolean {
        // Getting the user input
        if (view.text.isNullOrEmpty()) // check if user have not entered then ask for enter
        {
            Snackbar.make(view, "Enter Your Email", Snackbar.LENGTH_SHORT).show()
            return false
        } else if (!Patterns.EMAIL_ADDRESS.matcher(view.text).matches()) {
            // using EMAIL_ADDRESS matcher
            Snackbar.make(view, "Enter Valid Email", Snackbar.LENGTH_SHORT).show()
            return false
        }
        return true
    }
}
