package com.aditya.quizapp.fragments

import android.app.DatePickerDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.SubjectChoiceAdapter
import com.aditya.quizapp.databinding.FragmentRegisterBinding
import com.aditya.quizapp.models.loginAndRegister.request.UserRegisterRequest
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.databinding.ChooseSubjectLayoutBinding
import com.aditya.quizapp.repository.UserRepository
import com.example.quizapplication.retrofit.RetrofitHelper
import com.aditya.quizapp.utils.Constants
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RegisterFragment : Fragment() {

    private lateinit var binding: FragmentRegisterBinding
    private lateinit var viewModel: AuthViewModel
    private lateinit var dialogBinding: ChooseSubjectLayoutBinding
    private lateinit var dialog: Dialog
    private var calender: Calendar = Calendar.getInstance()
    private var personType: String? = null
    private val listSubject: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRegisterBinding.inflate(layoutInflater)

        personType = arguments?.getString("PersonType")
        Log.d("AdityaClickedRegister", personType.toString())
        binding.appLogo.text = "$personType Register"

        if (personType == "Teacher") {
            binding.containerChooseSubject.visibility = View.VISIBLE
        } else {
            binding.containerChooseSubject.visibility = View.GONE
        }
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

        binding.tvChooseSubject.setOnClickListener {
            showDialog()
        }
        setUpObserverSubjectChoice()

        binding.btnRegister.setOnClickListener {
            if (binding.tvNameRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Name", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvChooseSubject.text.isNullOrEmpty() || listSubject.isNullOrEmpty()) {
                Snackbar.make(it, "Please Choose Subject", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvPasswordRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Password", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvContactRegister.text.isNullOrEmpty() || binding.tvContactRegister.text!!.length > 10 || binding.tvContactRegister.text!!.length < 10) {
                Snackbar.make(it, "Please Enter Valid Number", Snackbar.LENGTH_SHORT).show()
            } else if (binding.tvDobRegister.text.isNullOrEmpty()) {
                Snackbar.make(it, "Please Enter Dob", Snackbar.LENGTH_SHORT).show()
            } else {
                if (Constants.checkEmail(binding.tvEmailRegister)) {
                    binding.btnRegister.isClickable = false
                    registerUser()
                }
            }
        }
        setUpRegisterObserver()
    }


    private fun setUpObserverSubjectChoice() {
        viewModel.responseTeacherSubjectChoiceLiveData.observe(requireActivity()) {
            if (it?.data != null) {
                Log.d("ChooseSubject", it.toString())

                //setting up recycler for subject choice of teacher
                dialogBinding.rvSubjectChoiceTeacher.layoutManager =
                    LinearLayoutManager(requireActivity())
                val adapter =
                    SubjectChoiceAdapter(it.data, onItemClick = { position, select, subject ->
                        if (select && !listSubject.contains(subject)) {
                            listSubject.add(subject)
                        } else if (!select && !listSubject.isNullOrEmpty()) {
                            listSubject.remove(subject)
                        }
                    })

                dialogBinding.rvSubjectChoiceTeacher.adapter = adapter

                dialogBinding.svSubjectChoice.stopShimmer()
                dialogBinding.svSubjectChoice.visibility = View.GONE
                dialogBinding.rvSubjectChoiceTeacher.visibility = View.VISIBLE
                dialogBinding.llSubjectCancel.visibility = View.VISIBLE

            } else if (it?.Message != null) {
                dialogBinding.svSubjectChoice.stopShimmer()
                dialogBinding.svSubjectChoice.visibility = View.GONE
                Snackbar.make(dialogBinding.root, it.Message, Snackbar.LENGTH_SHORT).show()
            } else {
                dialogBinding.svSubjectChoice.stopShimmer()
                dialogBinding.svSubjectChoice.visibility = View.GONE
                Snackbar.make(dialogBinding.root, "Something went Wrong", Snackbar.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun registerUser() {
        try {
            val data = UserRegisterRequest(
                binding.tvEmailRegister.text.toString(),
                listSubject,
                binding.tvNameRegister.text.toString(),
                personType.toString().lowercase(),
                binding.tvPasswordRegister.text.toString(),
                binding.tvContactRegister.text.toString(),
                binding.tvDobRegister.text.toString()
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
                saveData(it.data, it.tokens.access, it.tokens.refresh)
                // Log.d("Aditya", it.tokens.toString())
                //Toast.makeText(requireActivity(), it.tokens.access, Toast.LENGTH_SHORT).show()
                if (it.data == "student") {
                    findNavController().navigate(R.id.action_registerFragment_to_studentDashboard)
                } else if (it.data == "teacher") {
                    findNavController().navigate(R.id.action_registerFragment_to_teacherDashboardFragment)
                }
                // findNavController().popBackStack()
            } else {
                binding.progressBarRegister.visibility = View.GONE
                Snackbar.make(
                    binding.root, "Something Went Wrong",
                    Snackbar.LENGTH_SHORT
                ).show()
                binding.btnRegister.isClickable = false
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

    private fun saveData(personType: String, accessToken: String, refreshToken: String) {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE) ?: return
        with(sharedPref.edit()) {
            putString(getString(R.string.access_token), accessToken)
            putString(getString(R.string.refresh_token), refreshToken)
            putString(getString(R.string.person_type), personType)
            apply()
            commit()
        }
    }

    private fun showDialog() {
        dialog = Dialog(requireActivity())
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)

        dialogBinding = ChooseSubjectLayoutBinding.inflate(layoutInflater)
        dialog.setContentView(dialogBinding.root)
        dialogBinding.svSubjectChoice.visibility = View.VISIBLE
        dialogBinding.svSubjectChoice.startShimmer()
        try {
            viewModel.getTeacherSubjectChoice()
            setUpObserverSubjectChoice()
        } catch (e: Exception) {
            dialogBinding.svSubjectChoice.stopShimmer()
            Snackbar.make(binding.root, e.message.toString(), Snackbar.LENGTH_SHORT).show()
        }

        dialogBinding.btnSubjectCancel.setOnClickListener {
            dialog.dismiss()
        }
        dialogBinding.btnSubjectChoice.setOnClickListener {
            if (listSubject.isEmpty()) {
                Toast.makeText(requireActivity(), "Please select a subject", Toast.LENGTH_SHORT)
                    .show()
            } else {
                binding.tvChooseSubject.setText(listSubject.joinToString(separator = ","))
                dialog.dismiss()
            }
        }
        dialog!!.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.show()
    }

}