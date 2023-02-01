package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.databinding.FragmentMainSplashBinding

class MainSplashFragment : Fragment() {
    private lateinit var binding: FragmentMainSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMainSplashBinding.inflate(layoutInflater)
        binding.appLogoSplashMain.animation =
            AnimationUtils.loadAnimation(requireContext(), R.anim.slide_animation)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        val accessToken = sharedPref.getString(getString(R.string.access_token), null)
        val personType = sharedPref.getString(getString(R.string.person_type), null)
        Handler().also {
            it.postDelayed({
                if (accessToken != null && personType == "student") {
                    findNavController().navigate(R.id.action_mainSplashFragment_to_studentDashboard)
                } else if (accessToken != null && personType == "teacher") {
                    findNavController().navigate(R.id.action_mainSplashFragment_to_teacherDashboardFragment)
                } else {
                    findNavController().navigate(R.id.action_mainSplashFragment_to_splashFragment)
                }
            }, 3000)
        }

    }
}