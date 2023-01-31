package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aditya.quizapp.databinding.FragmentStudentBinding
import com.aditya.quizapp.adapters.QuestionAdapter


class StudentFragment : Fragment() {

    var binding: FragmentStudentBinding? = null
    var adapter = QuestionAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(inflater)
        val subject = arguments?.getString("subject")
        binding?.optionsRv?.adapter = adapter
        return binding?.root
    }

}