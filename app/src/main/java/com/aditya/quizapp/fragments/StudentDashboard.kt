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
import com.aditya.quizapp.databinding.FragmentStudentDashboardBinding


class StudentDashboard : Fragment() {
    private lateinit var binding: FragmentStudentDashboardBinding

    private var adapter = StudentDashboardAdapter()
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentStudentDashboardBinding.inflate(layoutInflater)
//        layoutManager = GridLayoutManager(context, 2)
//        binding = FragmentStudentBinding.inflate(layoutInflater)
//        binding?.recyclerview?.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}