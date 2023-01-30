package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.databinding.FragmentStudentDashboardBinding
import com.aditya.quizapp.models.StudentDashboardModel
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.aditya.quizapp.viewModels.AuthViewModelFactory
import com.example.quizapplication.api.UserApi
import com.example.quizapplication.retrofit.RetrofitHelper
import com.google.android.material.snackbar.Snackbar


class StudentDashboard : Fragment(),StudentDashboardAdapter.ClickEvent {
    private lateinit var binding : FragmentStudentDashboardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var layoutManager :  RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        layoutManager = GridLayoutManager(context, 2)
        binding = FragmentStudentDashboardBinding.inflate(layoutInflater)

        val data = ArrayList<StudentDashboardModel>()

        val adapter = StudentDashboardAdapter(this,data)

        binding.subjectslist.adapter = adapter

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        getData()

        return binding.root
    }

    private fun getData() {


        val preferences = requireActivity().getSharedPreferences("MY_APP", Context.MODE_PRIVATE)
        val retrivedAccessToken = preferences.getString("ACCESS_TOKEN",null)

        val result = viewModel.studentDashboard(retrivedAccessToken!!)


        viewModel.studentDashboardModelLiveData.observe(requireActivity()) {
            if (it != null) {

                Log.d("StudentDashboard",result.toString())

            } else {
                Snackbar.make(
                    binding.root,
                    "Some Went Wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun buttonClick() {
        findNavController().navigate(R.id.action_studentDashboard_to_studentFragment)
    }


}