package com.aditya.quizapp.utils

import android.app.Activity
import android.content.Context
import android.util.Patterns
import androidx.appcompat.widget.AppCompatEditText
import com.aditya.quizapp.R
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
    fun getToken(activity: Activity): String? {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)
        val accessToken = sharedPref?.getString(R.string.access_token.toString(), null)
        return accessToken
    }
}
