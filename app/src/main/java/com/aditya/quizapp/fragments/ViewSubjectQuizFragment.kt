package com.aditya.quizapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.ViewSubjectQuizAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentViewSubjectQuizBinding
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import javax.security.auth.Subject

class ViewSubjectQuizFragment : Fragment() {

    private lateinit var binding: FragmentViewSubjectQuizBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharePref: SharedPreferences
    private lateinit var accessTokens: String
    private lateinit var subjectName: String
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentViewSubjectQuizBinding.inflate(layoutInflater)


        subjectName = arguments?.getString("SubjectName").toString()
        binding.tbViewSub.tvToolbarTitle.text = "$subjectName Quizzes"

        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        binding.shimmerViewQuizTeacher.startShimmer()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvViewQuizTeacher.layoutManager = LinearLayoutManager(requireActivity())

        sharePref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharePref.getString(getString(R.string.access_token), null).toString()

        binding.tbViewSub.ivBackBtnToolbar.setOnClickListener {
            findNavController().popBackStack()
        }
        try {
            viewModel.getSubjectQuiz("Bearer $accessTokens", subjectName)
            getSubjectQuizObserver()
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun getSubjectQuizObserver() {
        viewModel.responseViewSubjectQuizLiveData.observe(requireActivity()) {
            if (!it?.data.isNullOrEmpty()) {
                val adapter = ViewSubjectQuizAdapter(it!!.data, onItemClick = { position ->
                    Toast.makeText(requireActivity(), "$position Clicked", Toast.LENGTH_SHORT)
                        .show()
                })
                binding.rvViewQuizTeacher.adapter = adapter
                binding.noSubjectQuiz.visibility = View.GONE
                binding.shimmerViewQuizTeacher.stopShimmer()
                binding.shimmerViewQuizTeacher.visibility = View.GONE
                binding.rvViewQuizTeacher.visibility = View.VISIBLE

            } else if (it?.data == null || it.data.isEmpty()) {
                binding.llContainerViewSubQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
                binding.shimmerViewQuizTeacher.stopShimmer()
            } else {
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
                binding.llContainerViewSubQuiz.visibility = View.GONE
                binding.noSubjectQuiz.visibility = View.VISIBLE
                binding.noSubjectQuiz.text = "Failed To Fetch Data"
                binding.shimmerViewQuizTeacher.stopShimmer()
            }
        }
    }
}