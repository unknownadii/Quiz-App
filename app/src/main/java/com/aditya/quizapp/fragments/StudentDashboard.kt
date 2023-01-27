package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.databinding.FragmentStudentBinding


class StudentDashboard : Fragment() {
    private var binding : FragmentStudentBinding? = null

    private var adapter = StudentDashboardAdapter()
    private lateinit var layoutManager :  RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment

        layoutManager = GridLayoutManager(context, 2)
        binding = FragmentStudentBinding.inflate(layoutInflater)
        binding?.recyclerview?.adapter = adapter
        return binding?.root
    }


}