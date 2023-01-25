package com.aditya.quizapp.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        val text = arguments?.getString("PersonType")
        Log.d("AdityaClickedRegister",text.toString())
        binding.appLogo.text = "$text Login"

        binding.btnLogin.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_addQuestionsFragment)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackArrow.setOnClickListener {
            /*
            Using navigation to move from one fragment to another fragment and adding popUpTo = "Fragment where you want" to give
            direction of movement and by adding app:popUpToInclusive="true" we can clear all fragments in stack with those fragments
            but if we app:popUpToInclusive="false" then it will not clear stack
            */
            findNavController().navigate(R.id.action_loginFragment_to_splashFragment)
        }
    }

}