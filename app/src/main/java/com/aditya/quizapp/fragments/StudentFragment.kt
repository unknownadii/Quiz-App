package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.ViewSubjectQuestionAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentStudentBinding
import com.aditya.quizapp.models.submitQuestionStudent.ResponseSubmitQuizStudent
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.QuestionAdapter
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class StudentFragment : Fragment() {
    private lateinit var binding: FragmentStudentBinding
    private lateinit var subjectName: String
    private lateinit var quizName: String
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var refreshTokens: String
    private lateinit var sharePref: SharedPreferences
    private var page = 1
    private val ansList: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(inflater)

        //getting data from previous fragment to set title
        quizName = arguments?.getString( "QuizName").toString()
       subjectName = arguments?.getString("SubjectName").toString()
        binding.tbGiveQuizStudent.tvToolbarTitle.text = "$quizName"

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

        binding.tbGiveQuizStudent.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.shimmerQuestionsStudent.startShimmer()
        try {
            viewModel.getStudentQuizQuestion("Bearer $accessTokens", subjectName, quizName, page)
            binding.btnNextQuestion.setOnClickListener {
                if (binding.option1Student.isChecked || binding.option2Student.isChecked
                    || binding.option3Student.isChecked || binding.option4Student.isChecked
                ) {
                    binding.cvSubQuizStu.visibility = View.GONE
                    binding.btnNextQuestion.visibility = View.GONE
                    binding.shimmerQuestionsStudent.startShimmer()
                    binding.shimmerQuestionsStudent.visibility = View.VISIBLE
                    page++
                    if (page < 5) {
                        viewModel.getStudentQuizQuestion(
                            "Bearer $accessTokens", subjectName, quizName, page
                        )
                    } else {
                        val bundle = Bundle()
                        bundle.putString("YourScore", "30")
                        findNavController().navigate(
                            R.id.action_studentFragment_to_scoreBoard,
                            bundle
                        )
                        Toast.makeText(requireActivity(), "Submit Question", Toast.LENGTH_SHORT)
                            .show()
                    }
                } else {
                    Toast.makeText(requireActivity(), "Select a option", Toast.LENGTH_SHORT)
                        .show()
                }
            }
            setUpQuestionsObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpQuestionsObserver() {
        viewModel.responseStudentQuizQuestionLiveData.observe(requireActivity()) {
            if (it?.data != null) {
                processData(it)
            } else if (it?.data == null) {
                // all questions are over and now move to wining page
                binding.shimmerQuestionsStudent.stopShimmer()
                binding.shimmerQuestionsStudent.visibility = View.GONE
                Snackbar.make(binding.root, "Already Attempted", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            } else {
                binding.shimmerQuestionsStudent.stopShimmer()
                binding.shimmerQuestionsStudent.visibility = View.GONE
                Snackbar.make(binding.root, "Something Went Wrong ", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun processData(it: ResponseSubmitQuizStudent) {
        Log.d("DataAditya", it.toString())
        binding.quizQuestionStudent.text = it.data.question
        val options = it.data.options
        if (!options[0].isNullOrEmpty()) {
            binding.option1Student.text = options[0]
        } else {
            binding.option1Student.visibility = View.GONE
        }
        if (!options[1].isNullOrEmpty()) {
            binding.option2Student.text = options[1]
        } else {
            binding.option2Student.visibility = View.GONE
        }
        if (!options[2].isNullOrEmpty()) {
            binding.option3Student.text = options[2]
        } else {
            binding.option3Student.visibility = View.GONE
        }
        if (!options[3].isNullOrEmpty()) {
            binding.option4Student.text = options[3]
        } else {
            binding.option4Student.visibility = View.GONE
        }
        binding.cvSubQuizStu.visibility = View.VISIBLE
        binding.btnNextQuestion.visibility = View.VISIBLE
        binding.shimmerQuestionsStudent.stopShimmer()
        binding.shimmerQuestionsStudent.visibility = View.GONE
    }

}