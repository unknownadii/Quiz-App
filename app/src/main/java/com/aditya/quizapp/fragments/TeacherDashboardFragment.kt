package com.aditya.quizapp.Fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.TeacherDashboardAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.FragmentTeacherDashboardBinding
import com.example.quizapplication.models.Tokens
import com.example.quizapplication.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.utils.Constants
import com.example.quizapplication.viewModels.AuthViewModel
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class TeacherDashboardFragment : Fragment() {

    private lateinit var binding: FragmentTeacherDashboardBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var accessTokens: String
    private val subjectData: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTeacherDashboardBinding.inflate(layoutInflater)

        // api calling for retrofit
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Setting layoutmanager for recycler view
        binding.rvStudentDashBoard.layoutManager = GridLayoutManager(requireActivity(), 2)

        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        accessTokens = sharedPref.getString(getString(R.string.access_token), null).toString()

        try {
            binding.progressBarTeacherDashboard.visibility = View.VISIBLE
            viewModel.getTeacherDashBoard("Bearer $accessTokens")
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.toString(), Snackbar.LENGTH_SHORT).show()
        }
        setUpTeacherDashBoardObserver()
    }

    private fun setUpTeacherDashBoardObserver() {
        viewModel.responseTeacherDashboardLiveData.observe(requireActivity()) {
            Log.d("AdityaDashboard", it.toString())
            if (it != null && accessTokens != null) {
                Log.d("DataTesting", it.data.toString())

                //setting up recyclerview data
                val adapter = TeacherDashboardAdapter(it.data)
                binding.rvStudentDashBoard.adapter = adapter
                binding.progressBarTeacherDashboard.visibility = View.GONE
                Toast.makeText(requireContext(), it.data.toString(), Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.progressBarTeacherDashboard.visibility = View.GONE
                Snackbar.make(binding.root, "Something Went Wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /*
    private fun setUpOnBackPressed() {
        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (isEnabled) {
                    Toast.makeText(requireContext(), "GoBack", Toast.LENGTH_SHORT).show()
                    isEnabled = false
                    requireActivity().onBackPressed()
                }
            }

        })
    }

     */
}