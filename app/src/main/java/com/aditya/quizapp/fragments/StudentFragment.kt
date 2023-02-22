package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentStudentBinding
import com.aditya.quizapp.models.submitQuestionStudent.Data
import com.aditya.quizapp.models.submitQuizQuestion.Request.RequestSubmitQuizQuestion
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
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
    private var totalQuestion = 0
    private var questionNumber = 0
    private val ansList: ArrayList<String> = ArrayList()
    private var questionsList: ArrayList<Data> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentStudentBinding.inflate(inflater)

        //getting data from previous fragment to set title
        quizName = arguments?.getString("QuizName").toString()
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
            viewModel.getStudentQuizQuestion("Bearer $accessTokens", subjectName, quizName)
            setUpQuestionsObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        binding.btnNextQuestion.setOnClickListener {
            if (binding.option1Student.isChecked || binding.option2Student.isChecked || binding.option3Student.isChecked || binding.option4Student.isChecked) {
                if (questionNumber < totalQuestion) {
                    addAnswerInList()
                }
                if (questionNumber < totalQuestion - 1) {
                    questionNumber++
                    binding.pbSubmitQuestion.visibility = View.VISIBLE
                    Handler(Looper.getMainLooper()).also {
                        it.postDelayed({
                            processData(questionsList[questionNumber])
                            binding.pbSubmitQuestion.visibility = View.GONE
                        }, 500)
                    }
                } else if (questionNumber >= totalQuestion - 1) {
                    //Do the api call for submitting the quiz and getting the the score
                    try {
                        binding.pbSubmitQuestion.visibility = View.VISIBLE
                        viewModel.submitStudentQuizQuestion(
                            "Bearer $accessTokens",
                            subjectName,
                            quizName,
                            RequestSubmitQuizQuestion(ans = ansList)
                        )
                    } catch (e: Exception) {
                        binding.pbSubmitQuestion.visibility = View.GONE
                        Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT)
                            .show()
                    }
                }
            } else {
                Toast.makeText(requireActivity(), "Select a option", Toast.LENGTH_SHORT).show()
            }
        }
        setUpSubmitQuestionsObserver()
    }

    private fun addAnswerInList() {
        if (binding.option1Student.isChecked) {
            ansList.add("a")
        } else if (binding.option2Student.isChecked) {
            ansList.add("b")
        } else if (binding.option3Student.isChecked) {
            ansList.add("c")
        } else if (binding.option4Student.isChecked) {
            ansList.add("d")
        }
    }

    private fun setUpSubmitQuestionsObserver() {
        viewModel.responseSubmitQuizQuestionLiveData.observe(requireActivity()) {
            if (it?.data != null) {
                val bundle = Bundle()
                bundle.putString("YourScore", it.data.scores.toString())
                findNavController().navigate(
                    R.id.action_studentFragment_to_scoreBoard, bundle
                )
                Toast.makeText(requireActivity(), "Question Submitted", Toast.LENGTH_SHORT)
                    .show()
                binding.pbSubmitQuestion.visibility = View.GONE
            } else {
                binding.pbSubmitQuestion.visibility = View.GONE
                Snackbar.make(binding.root, "Failed To Upload Questions", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun setUpQuestionsObserver() {
        viewModel.responseStudentQuizQuestionLiveData.observe(requireActivity()) {
            if (it!!.given && it.score != null) {
                // all questions are over and now move to wining page
                binding.shimmerQuestionsStudent.stopShimmer()
                binding.shimmerQuestionsStudent.visibility = View.GONE
                Snackbar.make(binding.root, "Already Attempted", Snackbar.LENGTH_SHORT).show()
                val bundle = Bundle()
                bundle.putString("YourScore", it.score.toString())
                findNavController().navigate(R.id.action_studentFragment_to_scoreBoard, bundle)
            } else if (it?.data != null && !it.given) {
                questionsList.addAll(it.data)
                totalQuestion = questionsList.size
                if (questionNumber < totalQuestion) {
                    processData(questionsList[questionNumber])
                } else {
                    binding.shimmerQuestionsStudent.stopShimmer()
                    binding.shimmerQuestionsStudent.visibility = View.GONE
                    Snackbar.make(binding.root, "Something Went Wrong ", Snackbar.LENGTH_SHORT)
                        .show()
                    findNavController().popBackStack()
                }
            } else {
                binding.shimmerQuestionsStudent.stopShimmer()
                binding.shimmerQuestionsStudent.visibility = View.GONE
                Snackbar.make(binding.root, "Something Went Wrong ", Snackbar.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        }
    }

    private fun processData(it: Data) {
        Log.d("DataAditya", it.toString())
        binding.quizQuestionStudent.text = it.question
        val options = it.options
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