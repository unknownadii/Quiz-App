package com.aditya.quizapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.annotation.IdRes
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
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

        var callback: OnBackPressedCallback =
            object : OnBackPressedCallback(true /* enabled by default */) {
                override fun handleOnBackPressed() {
                    // Handle the back button event
                    findNavController().navigate(R.id.action_scoreBoard_to_studentDashboard)
                }
            }
        requireActivity().onBackPressedDispatcher.addCallback(requireActivity(), callback);
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tbFinishedQuiz.ivBackBtnToolbar.setOnClickListener {
            findNavController().navigate(R.id.action_scoreBoard_to_studentDashboard)
        }
    }
}