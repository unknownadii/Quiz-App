package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aditya.quizapp.databinding.FragmentTeacherDashboardBinding


class TeacherDashboardFragment : Fragment() {

    private lateinit var binding: FragmentTeacherDashboardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeacherDashboardBinding.inflate(layoutInflater)

        return binding.root
    }
}