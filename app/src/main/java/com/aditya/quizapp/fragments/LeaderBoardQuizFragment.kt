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
import com.aditya.quizapp.adapters.StudentDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentLeaderBoardBinding
import com.aditya.quizapp.databinding.FragmentLeaderBoardQuizBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LeaderBoardQuizFragment : Fragment() {
    private lateinit var binding: FragmentLeaderBoardQuizBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var sharePref: SharedPreferences
    private lateinit var subjectName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardQuizBinding.inflate(layoutInflater)

        subjectName = arguments?.getString("SubjectName").toString()
        binding.tbLeaderBoardQuiz.tvToolbarTitle.text = "$subjectName LeaderBoard"

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
        binding.rvLeaderBoardQuiz.layoutManager = LinearLayoutManager(requireActivity())

        binding.tbLeaderBoardQuiz.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }
        try {
            viewModel.getLeaderBoardQuiz("Bearer $accessTokens", subjectName)
            setUpLeaderBoardQuizObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpLeaderBoardQuizObserver() {
        viewModel.responseLeaderBoardQuizLiveData.observe(requireActivity()) { it ->
            if (!it?.data.isNullOrEmpty()) {
                val adapter =
                    StudentDashboardAdapter(it!!.data, onItemClick = { position, quizName ->
                        val bundle = Bundle()
                        bundle.putString("QuizName", quizName)
                        bundle.putString("SubjectName", subjectName)
                        findNavController().navigate(
                            R.id.action_leaderBoardQuizFragment_to_leaderBoardScoreFragment,
                            bundle
                        )
                    })
                binding.rvLeaderBoardQuiz.adapter = adapter
                binding.rvLeaderBoardQuiz.visibility = View.VISIBLE
            } else if (it?.data == null || it.data.isEmpty()) {
                binding.llContainerLeaderBoardQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
            } else {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
                binding.llContainerLeaderBoardQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
                binding.noSubjectQuiz.text = "Failed To Fetch Data"
            }
            binding.shimmerViewLeaderBoardQuiz.stopShimmer()
            binding.shimmerViewLeaderBoardQuiz.visibility = View.GONE
        }
    }

}