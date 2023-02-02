package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.QuestionAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentStudentBinding
import com.aditya.quizapp.models.getQuestion.StudentQuestion
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.aditya.quizapp.viewModels.AuthViewModelFactory
import com.example.quizapplication.retrofit.RetrofitHelper
import com.google.android.material.snackbar.Snackbar


class StudentFragment : Fragment() {

    lateinit var binding: FragmentStudentBinding
    private lateinit var viewModel : AuthViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(inflater)

        val data = ArrayList<StudentQuestion>()
        val adapter = QuestionAdapter(data)

        val subject = arguments?.getString("subject")
        binding?.questionRv?.adapter = adapter

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        val preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val retrivedAccessToken = preferences.getString("ACCESS_TOKEN",null)

        val result = viewModel.getQuizQuestion(retrivedAccessToken!!,subject!!,1)

//        viewModel.studentDashboardModelLiveData.observe(requireActivity()) {
//            if (it != null) {
//
//                Log.d("StudentQuiz",result.toString())
//
//            } else {
//                Snackbar.make(
//                    binding.root,
//                    "Some Went Wrong",
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            }
//        }

        binding.btnSubmit.setOnClickListener {
            findNavController().navigate(R.id.action_studentFragment_to_scoreBoard)

        }


        return binding.root
    }

}