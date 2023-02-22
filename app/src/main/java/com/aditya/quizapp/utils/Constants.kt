package com.aditya.quizapp.utils

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Patterns
import androidx.annotation.IdRes
import androidx.appcompat.widget.AppCompatEditText
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import com.aditya.quizapp.R
import com.aditya.quizapp.models.responseLeaderboardScore.Data
import com.google.android.material.snackbar.Snackbar

object Constants {
    const val TAG = "QUIZCODE"
    const val BASE_URl: String = "https://27a2-182-69-183-144.in.ngrok.io"
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


    // Some times nav graph does not naviagation correctly so use this to navigate correctly
    fun NavController.navigateSafe(@IdRes resId: Int, args: Bundle? = null) {
        val destinationId: Int? = currentDestination?.getAction(resId)?.destinationId
        currentDestination?.let { node ->
            val currentNode = when (node) {
                is NavGraph -> node
                else -> node.parent
            }
            if (destinationId != 0) {
                if (destinationId != null) {
                    currentNode?.findNode(destinationId)?.let { navigate(resId, args) }
                }
            }
        }
    }
}
