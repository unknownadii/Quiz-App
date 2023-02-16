package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.databinding.RvViewSubjectQuizBinding

class StudentDashboardAdapter(
    private val mList: List<String>,
    private val onItemClick: ((position: Int, quizName: String) -> Unit)
) :
    RecyclerView.Adapter<StudentDashboardAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val binding =
            RvViewSubjectQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.subjectName.text = mList[position]
        holder.btnShowDetails.setOnClickListener {
            onItemClick.invoke(position, mList[position])
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SubjectViewHolder(binding: RvViewSubjectQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val subjectName = binding.tvQuizName
        val btnShowDetails = binding.ivViewQuizQuestion
    }
}