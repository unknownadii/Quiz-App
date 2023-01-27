package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.TeacherDashboardAdapter
import com.aditya.quizapp.databinding.FragmentTeachersDashboardBinding


class TeachersDashboard : Fragment() {
    private var binding : FragmentTeachersDashboardBinding? = null
    private val adapter = TeacherDashboardAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeachersDashboardBinding.inflate(layoutInflater)
        binding?.textView8?.setOnClickListener{
            //Navigation.findNavController(it).navigate(R.id.action_teachersDashboard_to_addSubFrag)
        }
        binding?.subjectslist?.adapter = adapter
        return binding?.root
    }


}