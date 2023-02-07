package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.ViewSubjectQuestionAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentQuizQuestionBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class QuizQuestionFragment : Fragment() {

    private lateinit var binding: FragmentQuizQuestionBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharePref: SharedPreferences
    private lateinit var accessTokens: String
    private lateinit var subjectName: String
    private lateinit var quizName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentQuizQuestionBinding.inflate(layoutInflater)

        subjectName = arguments?.getString("SubjectName").toString()
        quizName = arguments?.getString("QuizName").toString()
        binding.tbViewQuestions.tvToolbarTitle.text = "$quizName"

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tbViewQuestions.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.shimmerSubjectQuiz.startShimmer()
        binding.rvSubjectQuizQuestion.layoutManager = LinearLayoutManager(requireActivity())

        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()

        try {
            viewModel.getSubjectQuestion("Bearer $accessTokens", subjectName, quizName)
            getSubjectQuestionObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getSubjectQuestionObserver() {
        viewModel.responseViewSubjectQuestionLiveData.observe(requireActivity()) {
            if (!it?.data.isNullOrEmpty()) {
                Log.d("SubjectQuestionData", it.toString())
                val adapter = ViewSubjectQuestionAdapter(it!!.data)
                binding.rvSubjectQuizQuestion.adapter = adapter
                binding.shimmerSubjectQuiz.stopShimmer()
                binding.noSubjectQuestion.visibility = View.GONE
                binding.shimmerSubjectQuiz.visibility = View.GONE
                binding.rvSubjectQuizQuestion.visibility = View.VISIBLE
            } else if (it?.data == null || it.data.isEmpty()) {
                binding.shimmerSubjectQuiz.stopShimmer()
                binding.shimmerSubjectQuiz.visibility = View.GONE
                binding.noSubjectQuestion.visibility = View.VISIBLE
            } else {
                binding.shimmerSubjectQuiz.stopShimmer()
                binding.shimmerSubjectQuiz.visibility = View.GONE
                binding.noSubjectQuestion.visibility = View.GONE
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}