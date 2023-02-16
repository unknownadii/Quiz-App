package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentSubjectQuizStudentBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class SubjectQuizStudent : Fragment() {
    private lateinit var binding: FragmentSubjectQuizStudentBinding
    private lateinit var subjectName: String
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var refreshTokens: String
    private lateinit var sharePref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubjectQuizStudentBinding.inflate(layoutInflater)

        //getting data from previous fragment to set title
        subjectName = arguments?.getString("SubjectName").toString()
        binding.tbSubQuizStu.tvToolbarTitle.text = "$subjectName Quizzes"

        //api calling setup
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // getting access token for the api calling
        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()
        refreshTokens = sharePref.getString(getString(R.string.refresh_token), null).toString()

        binding.shimmerViewStuSubQuiz.startShimmer()
        binding.rvStuSubQuiz.layoutManager = LinearLayoutManager(requireActivity())

        binding.tbSubQuizStu.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        try {
            viewModel.getStudentSubjectQuiz("Bearer $accessTokens", subjectName)
            setUpStudentQuizObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpStudentQuizObserver() {
        viewModel.responseStudentSubjectQuizLiveData.observe(requireActivity()) { it ->
            if (!it?.data.isNullOrEmpty()) {
                val adapter =
                    StudentDashboardAdapter(it!!.data, onItemClick = { position, quizName ->
                        val bundle = Bundle()
                        bundle.putString("QuizName",quizName)
                        bundle.putString("SubjectName",subjectName)
                        findNavController().navigate(R.id.action_subjectQuizStudent_to_studentFragment,bundle)
                    })
                binding.rvStuSubQuiz.adapter = adapter
                binding.rvStuSubQuiz.visibility = View.VISIBLE
            } else if (it?.data == null || it.data.isEmpty()) {
                binding.llContainerStuSubQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
            } else {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                binding.llContainerStuSubQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
                binding.noSubjectQuiz.text = "Failed To Fetch Data"
            }
            binding.shimmerViewStuSubQuiz.stopShimmer()
            binding.shimmerViewStuSubQuiz.visibility = View.GONE
        }
    }
}