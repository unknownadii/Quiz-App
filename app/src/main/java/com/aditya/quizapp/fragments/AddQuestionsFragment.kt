package com.aditya.quizapp.Fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.aditya.quizapp.R
import com.aditya.quizapp.dataModel.DynamicOptionsDataModel
import com.aditya.quizapp.adapters.teacherAddQuestionAdapter.TeacherQuestionsAdapter
import com.aditya.quizapp.dataModel.DynamicQuestionsDataModel
import com.aditya.quizapp.databinding.FragmentAddQuestionsBinding

class AddQuestionsFragment : Fragment() {

    private lateinit var binding: FragmentAddQuestionsBinding
    private lateinit var questionsData: ArrayList<DynamicQuestionsDataModel>
    private lateinit var adapterQuestions: TeacherQuestionsAdapter
    //  private lateinit var adapterOption: TeachersOptionsAdapter
    private var count = 0
    //private val childOptionRecyclerView = RecyclerView.RecycledViewPool()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        binding = FragmentAddQuestionsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.rvAddDynamicQuestions.layoutManager = LinearLayoutManager(requireActivity())

        questionsData = ArrayList()
        questionsData.add(
            DynamicQuestionsDataModel(
                count, arrayListOf(
                    DynamicOptionsDataModel(R.drawable.ic_delete)
                )
            )
        )
        adapterQuestions = TeacherQuestionsAdapter(object : TeacherQuestionsAdapter.performClick {
            override fun deleteQuestion(position: Int) {
               // count--
                questionsData.removeAt(position)
                adapterQuestions.notifyItemRemoved(position)
                adapterQuestions.notifyItemChanged(position)
                adapterQuestions.notifyItemRangeChanged(position + 1, questionsData.size - 1)
                // adapterQuestions.notifyItemChanged(position, questionsData)
            }

        }, questionsData)
        binding.rvAddDynamicQuestions.adapter = adapterQuestions

        binding.btnAddQuestion.setOnClickListener {
            count++
            questionsData.add(
                DynamicQuestionsDataModel(
                    count,
                    arrayListOf(DynamicOptionsDataModel(R.drawable.ic_delete))
                )
            )
            adapterQuestions.notifyItemInserted(questionsData.size - 1)
            adapterQuestions.notifyItemChanged(questionsData.size - 1)
        }

    }

    // user clicks on addButton
    private fun addNewView(context: Context) {
        //  val inflater = LayoutInflater.from(context).inflate(R.layout.add_options_layout, null)
        //  binding.rvAddNewView.addView(inflater, binding.rvAddNewView.childCount)
    }


}