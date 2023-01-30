package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.databinding.FragmentLoginBinding
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.viewModels.AuthViewModelFactory
import com.example.quizapplication.api.UserApi
import com.aditya.quizapp.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.aditya.quizapp.viewModels.AuthViewModel
import com.google.android.material.snackbar.Snackbar


class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLoginBinding.inflate(layoutInflater)

        val text = arguments?.getString("PersonType")
        Log.d("AdityaClickedRegister", text.toString())
        binding.appLogo.text = "$text Login"

        // api calling for retrofit
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]

        binding.btnLogin.setOnClickListener {
            login()
            findNavController().navigate(R.id.action_loginFragment_to_addQuestionsFragment)
        }
        return binding.root
    }

    private fun login() {
        val result = viewModel.loginUser(RequestAuthenticationDataModel("aditya@gmail.com", "1234"))
        viewModel.userLoginResponseLiveData.observe(requireActivity()) {
            if (it != null) {
                Log.d("LoginResponse", it.tokens.toString())

                // Saving tokens to SharedPreferences
                val token = it.tokens
                val preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
                preferences.edit().putString("ACCESS_TOKEN", token.access).apply()
                preferences.edit().putString("REFRESH_TOKEN", token.refresh).apply()
            } else {
                Snackbar.make(
                    binding.root,
                    "Some Went Wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
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