package com.aditya.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.dataModel.DynamicOptionsDataModel
import com.aditya.quizapp.dataModel.DynamicQuestionsDataModel

class TeacherQuestionsAdapter(
    private val click: performClick,
    private val qList: ArrayList<DynamicQuestionsDataModel>
) :
    RecyclerView.Adapter<TeacherQuestionsAdapter.ViewHolder>(),
    TeachersOptionsAdapter.performQuestionsClick {

    private lateinit var teacherOptionAdapter: TeachersOptionsAdapter
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_questions_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = qList[position]

        holder.btnDeleteQuestion.setOnClickListener {
            click.deleteQuestion(holder.adapterPosition)
        }
        holder.btnAddOption.setOnClickListener {
            qList[position].optionList.add(DynamicOptionsDataModel(R.drawable.ic_delete))
            teacherOptionAdapter.notifyItemInserted(qList[holder.adapterPosition].optionList.size - 1)
            teacherOptionAdapter.notifyItemChanged(qList[holder.adapterPosition].optionList.size - 1)
        }
        holder.etQuestion.setText("${holder.adapterPosition}")

        teacherOptionAdapter =
            TeachersOptionsAdapter(this, qList[position].optionList, holder.adapterPosition)
        holder.rvOptions.adapter = teacherOptionAdapter
    }

    override fun getItemCount(): Int {
        return qList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnDeleteQuestion: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnDeleteQuestion)
        val btnAddOption: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnAddOptionQuestions)
        val etQuestion: AppCompatEditText = ItemView.findViewById(R.id.etEnterQuestion)
        val rvOptions: RecyclerView = ItemView.findViewById(R.id.rvAddNewOption)
    }

    interface performClick {
        fun deleteQuestion(position: Int)
    }

    override fun deleteOption(childPosition: Int, parentPosition: Int) {
        qList[parentPosition].optionList.removeAt(childPosition)
        teacherOptionAdapter.notifyItemRemoved(childPosition)
        teacherOptionAdapter.notifyItemChanged(childPosition)
        teacherOptionAdapter.notifyItemRangeChanged(
            childPosition + 1,
            qList[parentPosition].optionList.size - 1
        )
    }
}