package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.LeaderBoardScoreAdapter
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentLeaderBoardQuizBinding
import com.aditya.quizapp.databinding.FragmentLeaderBoardScoreBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.util.*

class LeaderBoardScoreFragment : Fragment() {
    private lateinit var binding: FragmentLeaderBoardScoreBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var sharePref: SharedPreferences
    private lateinit var subjectName: String
    private lateinit var quizName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardScoreBinding.inflate(layoutInflater)
        subjectName = arguments?.getString("SubjectName").toString()
        quizName = arguments?.getString("QuizName").toString()
        binding.tbLeaderBoardScore.tvToolbarTitle.text = "$quizName LeaderBoard"

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()

        binding.shimmerViewLeaderBoardQuiz.startShimmer()
        binding.rvLeaderBoardScore.layoutManager = LinearLayoutManager(requireActivity())

        binding.tbLeaderBoardScore.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }

        try {
            viewModel.getLeaderBoardScore("Bearer $accessTokens", subjectName, quizName)
            setUpLeaderScoreQuizObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpLeaderScoreQuizObserver() {
        viewModel.responseLeaderBoardScoreLiveData.observe(requireActivity()) { it ->
            if (!it?.data.isNullOrEmpty()) {

                // sorting the data of arraylist
                val sortedList = it!!.data.sortedWith(compareBy({ it.scores }, { it.scores }))
                val adapter =
                    LeaderBoardScoreAdapter(sortedList, onItemClick = { position, quizName ->
                    })
                binding.rvLeaderBoardScore.adapter = adapter
                binding.rvLeaderBoardScore.visibility = View.VISIBLE
            } else if (it?.data == null || it.data.isEmpty()) {
                binding.llContainerLeaderBoardScore.visibility = View.GONE
                binding.noSubjectScore.visibility = View.VISIBLE
                binding.noSubjectScore.text = "No Quiz History Found"
            } else {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                binding.llContainerLeaderBoardScore.visibility = View.GONE
                binding.noSubjectScore.visibility = View.VISIBLE
                binding.noSubjectScore.text = "Failed To Fetch Data"
            }
            binding.shimmerViewLeaderBoardQuiz.stopShimmer()
            binding.shimmerViewLeaderBoardQuiz.visibility = View.GONE
        }
    }
}