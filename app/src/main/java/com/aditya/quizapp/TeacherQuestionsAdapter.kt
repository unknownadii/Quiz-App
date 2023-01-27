package com.aditya.quizapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.dataModel.DynamicQuestionsDataModel

class TeacherQuestionsAdapter(
    private val click: performClick,
    private val qList: ArrayList<DynamicQuestionsDataModel>
) :
    RecyclerView.Adapter<TeacherQuestionsAdapter.ViewHolder>() {

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

        holder.etQuestion.setText("${holder.adapterPosition}")
    }

    override fun getItemCount(): Int {
        return qList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnDeleteQuestion: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnDeleteQuestion)
        val btnAddOption: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnAddOptionQuestions)

        val etQuestion: AppCompatEditText = ItemView.findViewById(R.id.et_register_email)
    }

    interface performClick {
        fun deleteQuestion(position: Int)
        fun addOption()
    }
}