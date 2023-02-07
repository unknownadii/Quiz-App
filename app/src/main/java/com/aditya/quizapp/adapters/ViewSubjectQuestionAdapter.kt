package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.databinding.RvChooseSubjectBinding
import com.aditya.quizapp.databinding.RvSubjectQuizBinding
import com.aditya.quizapp.databinding.RvViewSubjectQuizBinding
import com.aditya.quizapp.models.subjectQuestion.Data

class ViewSubjectQuestionAdapter(
    private val mList: List<Data>
) :
    RecyclerView.Adapter<ViewSubjectQuestionAdapter.SubjectQuestionViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectQuestionViewHolder {
        val binding =
            RvSubjectQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectQuestionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectQuestionViewHolder, position: Int) {
        val item = mList[position]
        holder.question.text = item.id.toString() + ". " + item.question
        holder.correctAnswer.text = item.correct
        if (item.a.isNullOrEmpty()) {
            holder.option1.visibility = View.GONE
        } else {
            holder.option1.text = "a. " + item.a
        }
        if (item.b.isNullOrEmpty()) {
            holder.option2.visibility = View.GONE
        } else {
            holder.option2.text = "b. " + item.b
        }
        if (item.c.isNullOrEmpty()) {
            holder.option3.visibility = View.GONE
        } else {
            holder.option3.text = "c. " + item.c
        }
        if (item.d.isNullOrEmpty()) {
            holder.option4.visibility = View.GONE
        } else {
            holder.option4.text = "d. " + item.d
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SubjectQuestionViewHolder(binding: RvSubjectQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val question = binding.tvQuestionSubjectQuiz
        val option1 = binding.tvOption1SubjectQuiz
        val option2 = binding.tvOption2SubjectQuiz
        val option3 = binding.tvOption3SubjectQuiz
        val option4 = binding.tvOption4SubjectQuiz
        val correctAnswer = binding.tvCorrectAnswer
    }
}