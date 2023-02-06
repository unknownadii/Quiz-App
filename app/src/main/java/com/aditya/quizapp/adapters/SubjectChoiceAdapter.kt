package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.databinding.RvChooseSubjectBinding

class SubjectChoiceAdapter(
    private val mList: List<String>,
    private val onItemClick: ((position: Int, select: Boolean, subject: String) -> Unit)
) :
    RecyclerView.Adapter<SubjectChoiceAdapter.SubjectChoiceViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectChoiceViewHolder {
        val binding =
            RvChooseSubjectBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return SubjectChoiceViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SubjectChoiceViewHolder, position: Int) {
        val item = mList[position]
        holder.subject.text = item
        holder.subject.setOnClickListener {
            onItemClick.invoke(position, holder.subject.isChecked, holder.subject.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class SubjectChoiceViewHolder(binding: RvChooseSubjectBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val subject = binding.subjectNameTeacherChoice
    }
}