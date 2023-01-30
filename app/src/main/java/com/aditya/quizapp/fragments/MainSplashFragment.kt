package com.aditya.quizapp.fragments

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

        Handler().also {
            it.postDelayed({
                // Do something after 5s = 5000ms
                findNavController().navigate(R.id.action_mainSplashFragment_to_splashFragment)
            }, 3000)
        }

        return binding.root
    }

}