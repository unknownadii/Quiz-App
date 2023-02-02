package com.aditya.quizapp.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.adapters.TeacherDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentStudentDashboardBinding
import com.aditya.quizapp.models.studentDashboardModel.StudentDashboardModel
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.aditya.quizapp.viewModels.AuthViewModelFactory
import com.example.quizapplication.retrofit.RetrofitHelper
import com.google.android.material.snackbar.Snackbar


class StudentDashboard : Fragment(){
    private lateinit var binding : FragmentStudentDashboardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var layoutManager :  RecyclerView.LayoutManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        //layoutManager = GridLayoutManager(context, 2)
        binding = FragmentStudentDashboardBinding.inflate(layoutInflater)

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        getData()

        return binding.root
    }

    private fun getData() {

        val data = ArrayList<StudentDashboardModel>()

        val adapter = StudentDashboardAdapter(data)

        binding.subjectslist.adapter = adapter


        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharedPref.getString(R.string.access_token.toString(), null).toString()

        try {
             viewModel.studentDashboard( "Bearer $accessTokens")
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.toString(), Snackbar.LENGTH_SHORT).show()
        }

        //val result = viewModel.studentDashboard(accessTokens)


        viewModel.studentDashboardLiveData.observe(requireActivity()) {
            if (it != null) {

                Log.d("StudentDashboard","api not called")

            } else {
                Snackbar.make(
                    binding.root,
                    "Some Went Wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }



}