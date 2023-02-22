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
import com.aditya.quizapp.databinding.FragmentStudentDashboardBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class LeaderBoardFragment : Fragment() {
    private lateinit var binding: FragmentLeaderBoardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private lateinit var sharePref: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLeaderBoardBinding.inflate(layoutInflater)

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]

        binding.tbLeaderBoard.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.tbLeaderBoard.tvToolbarTitle.text = "LeaderBoard"
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()
        binding.rvLeaderBoard.layoutManager = LinearLayoutManager(requireActivity())
        binding.shimmerViewContainerLeaderBoard.startShimmer()
        try {
            viewModel.getLeaderBoard("Bearer $accessTokens")
            setUpLeaderBoardObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpLeaderBoardObserver() {
        viewModel.responseLeaderBoardLiveData.observe(requireActivity()) {
            if (!it?.data.isNullOrEmpty()) {

                Log.d("LeaderBoardData", it.toString())
                val adapter =
                    StudentDashboardAdapter(it!!.data, onItemClick = { position, subjectName ->
                        val bundle = Bundle()
                        bundle.putString("SubjectName", subjectName)
                        findNavController().navigate(
                            R.id.action_leaderBoardFragment_to_leaderBoardQuizFragment,
                            bundle
                        )
                    })
                binding.rvLeaderBoard.adapter = adapter
                binding.shimmerViewContainerLeaderBoard.stopShimmer()
                binding.shimmerViewContainerLeaderBoard.visibility = View.GONE
                binding.rvLeaderBoard.visibility = View.VISIBLE
            } else if (it != null) {
                binding.shimmerViewContainerLeaderBoard.stopShimmer()
                binding.shimmerViewContainerLeaderBoard.visibility = View.GONE
                Snackbar.make(binding.root, it.Message, Snackbar.LENGTH_SHORT).show()
            } else {
                binding.shimmerViewContainerLeaderBoard.stopShimmer()
                binding.shimmerViewContainerLeaderBoard.visibility = View.GONE
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }
}