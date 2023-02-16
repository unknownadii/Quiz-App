package com.aditya.quizapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.databinding.FragmentSplashBinding
import com.google.android.material.snackbar.Snackbar

class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)

        val bundle = Bundle()
        binding.btnLogin.setOnClickListener {
            if (binding.autoCompleteTextView.text.isNullOrEmpty()) {
                Snackbar.make(
                    binding.autoCompleteTextView,
                    "Please Choose Person",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                bundle.apply {
                    putString("PersonType", binding.autoCompleteTextView.text.toString())
                }
                findNavController().navigate(R.id.action_splashFragment_to_loginFragment, bundle)
            }
        }

        binding.btnRegister.setOnClickListener {
            if (binding.autoCompleteTextView.text.isNullOrEmpty()) {
                Snackbar.make(
                    binding.autoCompleteTextView,
                    "Please Choose Person",
                    Snackbar.LENGTH_SHORT
                ).show()
            } else {
                bundle.apply {
                    Log.d("Aditya", binding.autoCompleteTextView.text.toString())
                    putString("PersonType", binding.autoCompleteTextView.text.toString())
                }
                findNavController().navigate(
                    R.id.action_splashFragment_to_registerFragment2,
                    bundle
                )
            }
        }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        Log.d("AdityaClicked", "Clicked")
        val languages = resources.getStringArray(R.array.programming_languages)
        val arrayAdapter = ArrayAdapter(binding.root.context, R.layout.dropdown_menu, languages)
        // get reference to the autocomplete text view
        val autocompleteTV = binding.autoCompleteTextView
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
    }
}