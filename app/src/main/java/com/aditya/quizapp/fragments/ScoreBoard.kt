package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.aditya.quizapp.databinding.FragmentScoreBoardBinding

class ScoreBoard : Fragment() {
    private lateinit var binding: FragmentScoreBoardBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentScoreBoardBinding.inflate(layoutInflater)

        binding.tbFinishedQuiz.tvToolbarTitle.text = "Score Board"
        val score = arguments?.getString("YourScore").toString()
        binding.scoreStudent.text = score
        return binding.root
    }

}