package com.aditya.quizapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.aditya.quizapp.databinding.FragmentStudentBinding
import com.example.quizapplication.QuestionAdapter
import com.example.quizapplication.models.Quetions


class StudentFragment : Fragment() {

    var binding : FragmentStudentBinding? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(inflater)
        val data = ArrayList<Quetions>()
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))
        data.add(Quetions("Martha ,Mary, May, Made Marvelous Milk. In that sentence who made the milk? This is an easy and dumb question!","Martha","Martha","Mary","Martha, Mary, May","May"))


        val adapter = QuestionAdapter(data,requireContext())
        binding?.recyclerview?.adapter = adapter
        return binding?.root
    }




}