package com.aditya.quizapp.Fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.adapters.teacherAddQuestionAdapter.TeacherQuestionsAdapter
import com.aditya.quizapp.api.UserApi
import com.aditya.quizapp.dataModel.DynamicQuestionsDataModel
import com.aditya.quizapp.databinding.FragmentAddQuestionsBinding
import com.aditya.quizapp.models.addQuestionsToQuiz.request.Question
import com.aditya.quizapp.models.addQuestionsToQuiz.request.RequestAddQuizQuestion
import com.aditya.quizapp.repository.UserRepository
import com.aditya.quizapp.viewModels.AuthViewModel
import com.example.quizapplication.retrofit.RetrofitHelper
import com.example.quizapplication.viewModels.AuthViewModelFactory
import com.google.android.material.snackbar.Snackbar

class AddQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentAddQuestionsBinding
    private lateinit var questionsData: ArrayList<DynamicQuestionsDataModel>
    private val questionsList: ArrayList<Question> = ArrayList()
    private lateinit var adapterQuestions: TeacherQuestionsAdapter
    private lateinit var viewModel: AuthViewModel
    private lateinit var sharePref: SharedPreferences
    private lateinit var accessTokens: String
    private lateinit var subjectName: String
    private var count = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddQuestionsBinding.inflate(layoutInflater)

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
        subjectName = arguments?.getString("SubjectName").toString()

        binding.toolbar.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
        binding.toolbar.btnUploadQuestion.setOnClickListener {
//            if (questionsList.size < 5) {
//                Snackbar.make(
//                    binding.root,
//                    "Please add atleast 5 Questions ",
//                    Snackbar.LENGTH_SHORT
//                ).show()
//            } else {
            viewModel.addQuizQuestion(
                "Bearer $accessTokens", subjectName,
                RequestAddQuizQuestion("Hard", questionsList)
            )

        }
        binding.rvAddDynamicQuestions.layoutManager = LinearLayoutManager(requireActivity())

        questionsData = ArrayList()
        questionsData.add(
            DynamicQuestionsDataModel("True/False")
        )
        adapterQuestions = TeacherQuestionsAdapter(object : TeacherQuestionsAdapter.performClick {
            override fun deleteQuestion(position: Int) {
                questionsData.removeAt(position)
                adapterQuestions.notifyItemRemoved(position)
            }

        }, questionsData, onSubmitQuiz = { position, messages, showOrNot, question ->
            if (showOrNot) {
                Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
            } else {
                Log.d("CorrectAnswer", question.toString())
                questionsList.add(question!!)
                Snackbar.make(binding.root, messages, Snackbar.LENGTH_SHORT).show()
            }
        })
        binding.rvAddDynamicQuestions.adapter = adapterQuestions

        binding.btnAddeQuestion.setOnClickListener {
            if (questionsData.size < 5) {
                questionsData.add(
                    0,
                    DynamicQuestionsDataModel(
                        binding.chooseTypeQuestion.text.toString()
                    )
                )
                adapterQuestions.notifyItemInserted(0)
                adapterQuestions.notifyItemChanged(0)
            } else {
                Snackbar.make(
                    binding.root,
                    "First submit 5 questions to add more ",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }

        setUpAddQuestionObserver()
    }

    private fun setUpAddQuestionObserver() {
        viewModel.responseAddQuizQuestionLiveData.observe(requireActivity()) {
            if (it != null) {
                Snackbar.make(binding.root, it.Message, Snackbar.LENGTH_SHORT).show()
                Log.d("AddQuestion Data", it.Message)
            } else {
                Snackbar.make(binding.root, "Something went wrong", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    /* user clicks on addButton
    private fun addNewView(context: Context) {
        //  val inflater = LayoutInflater.from(context).inflate(R.layout.add_options_layout, null)
        //  binding.rvAddNewView.addView(inflater, binding.rvAddNewView.childCount)
    }
     */

    override fun onResume() {
        super.onResume()
        Log.d("AdityaClicked", "Clicked")
        val languages = resources.getStringArray(R.array.question_type)
        val arrayAdapter = ArrayAdapter(binding.root.context, R.layout.dropdown_menu, languages)
        // get reference to the autocomplete text view
        val autocompleteTV = binding.chooseTypeQuestion
        // set adapter to the autocomplete tv to the arrayAdapter
        autocompleteTV.setAdapter(arrayAdapter)
    }

}