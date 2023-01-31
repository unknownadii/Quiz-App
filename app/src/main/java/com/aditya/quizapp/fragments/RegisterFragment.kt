package com.aditya.quizapp.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.DatePicker
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.aditya.quizapp.R
import com.aditya.quizapp.databinding.FragmentRegisterBinding
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.api.UserApi
import com.example.quizapplication.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.utils.Constants
import com.example.quizapplication.viewModels.AuthViewModel
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: AuthViewModel
    private var calender: Calendar = Calendar.getInstance()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)
        val text = arguments?.getString("PersonType")
        Log.d("AdityaClickedRegister", text.toString())
        binding.appLogo.text = "$text Register"

        // api calling for retrofit
        val interfaceApi = RetrofitHelper.getInstance().create(UserApi::class.java)
        val repository = UserRepository(interfaceApi)
        viewModel =
            ViewModelProvider(this, AuthViewModelFactory(repository))[AuthViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivBackArrow.setOnClickListener {
            // without navigating from 2nd fragment  to 1st fragment just clearing the one previous stack to move
            findNavController().popBackStack()
        }

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calender.set(Calendar.YEAR, year)
                calender.set(Calendar.MONTH, monthOfYear)
                calender.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }

        binding.tvDobRegister.setOnClickListener {
            DatePickerDialog(
                requireActivity(),
                dateSetListener,
                calender.get(Calendar.YEAR),
                calender.get(Calendar.MONTH),
                calender.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        binding.btnRegister.setOnClickListener {
            if (binding.tvNameRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Name", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvPasswordRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Password", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvContactRegister.text.isNullOrEmpty() || binding.tvContactRegister.text!!.length > 10 || binding.tvContactRegister.text!!.length < 10) {
                Snackbar.make(it, "Please Enter Valid Number", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvDobRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Dob", Snackbar.LENGTH_SHORT).show()
            } else {
                if (Constants.checkEmail(binding.tvEmailRegister)) {
                    registerUser()
                }
            }
        }
        setUpRegisterObserver()
    }

    private fun registerUser() {
        try {
            val data = UserRegisterRequest(
                "aditya3442396@gmail.com",
                "Aditya",
                "student",
                "1234",
                "9234569876",
                "2002-11-11"
            )

            binding.progressBarRegister.visibility = View.VISIBLE
            viewModel.registerUser(data)
        } catch (e: Exception) {
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun setUpRegisterObserver() {
        viewModel.userRegisterResponseLiveData.observe(requireActivity()) {
            if (it != null) {
                binding.progressBarRegister.visibility = View.GONE
                Log.d("Aditya", it.tokens.toString())
                Toast.makeText(requireActivity(), it.tokens.access, Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_registerFragment2_to_teacherDashboardFragment)
                // findNavController().popBackStack()
            } else {
                binding.progressBarRegister.visibility = View.GONE
                Snackbar.make(
                    binding.root, "Something Went Wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }
    private fun updateDateInView() {
        val myFormat =
            "yyyy-MM-dd" // mention the format you need
        val dateFormat = SimpleDateFormat(
            myFormat,
            Locale.US
        )
        binding.tvDobRegister.setText(dateFormat.format(calender.time))
    }

}