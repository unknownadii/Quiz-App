package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.models.loginAndRegister.request.RequestAuthenticationDataModel
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentLoginBinding
import com.aditya.quizapp.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.aditya.quizapp.utils.Constants
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private lateinit var viewModel: AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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
            if (Constants.checkEmail(binding.etLoginEmail)) {
                if (!binding.etLoginPassword.text.isNullOrEmpty()) {
                    binding.btnLogin.isClickable = false
                    login()
                } else {
                    Snackbar.make(it, "Enter Your Password", Snackbar.LENGTH_SHORT).show()
                }
            }
        }
        setUpLoginObserver()
        return binding.root
    }

    private fun login() {
        binding.progressBarLogin.visibility = View.VISIBLE
        viewModel.loginUser(
            RequestAuthenticationDataModel(
                binding.etLoginEmail.text.toString(),
                binding.etLoginPassword.text.toString()
            )
        )
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

    private fun setUpLoginObserver() {
        viewModel.userLoginResponseLiveData.observe(requireActivity()) {
            if (it != null) {
                binding.progressBarLogin.visibility = View.GONE
                Log.d("Aditya", it.tokens.toString())
                //saving tokens in the preference
                saveData(it.data, it.tokens.access, it.tokens.refresh)
                //Toast.makeText(requireActivity(), it.tokens.access, Toast.LENGTH_SHORT).show()
                // findNavController().popBackStack()
                if (it.data == "student") {
                    findNavController().navigate(R.id.action_loginFragment_to_studentDashboard)
                } else if (it.data == "teacher") {
                    findNavController().navigate(R.id.action_loginFragment_to_teacherDashboardFragment)
                }
            } else {
                binding.progressBarLogin.visibility = View.GONE
                Snackbar.make(
                    binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT
                ).show()
                binding.btnLogin.isClickable = true
            }
        }
    }
    private fun saveData(personType: String, accessToken: String, refreshToken: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.access_token), accessToken)
            putString(getString(R.string.refresh_token), refreshToken)
            putString(getString(R.string.person_type), personType)
            apply()
            commit()
        }
    }
}
