package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.AnswerAdapter
import com.aditya.quizapp.adapters.ViewQuestionAdapter
import com.aditya.quizapp.databinding.FragmentTeacherBinding


class TeacherFragment : Fragment() {

    private var binding : FragmentTeacherBinding? = null
    val adapter = AnswerAdapter()
    private val questionAdapter = ViewQuestionAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherBinding.inflate(layoutInflater)
        binding?.questionrv?.adapter = questionAdapter
        binding?.questionrv?.findViewById<RecyclerView>(R.id.answers)?.adapter = adapter
        return binding?.root

    }


}