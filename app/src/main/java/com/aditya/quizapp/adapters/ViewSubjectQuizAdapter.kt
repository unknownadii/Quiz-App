package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.databinding.RvChooseSubjectBinding
import com.aditya.quizapp.databinding.RvViewSubjectQuizBinding

class ViewSubjectQuizAdapter(
    private val mList: List<String>,
    private val onItemClick: ((position: Int,quizName:String) -> Unit)
) :
    RecyclerView.Adapter<ViewSubjectQuizAdapter.ViewSubjectQuizViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewSubjectQuizViewHolder {
        val binding =
            RvViewSubjectQuizBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewSubjectQuizViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewSubjectQuizViewHolder, position: Int) {
        val item = mList[position]
        holder.quizName.text = item
        holder.viewQuestion.setOnClickListener {
            onItemClick.invoke(position,item)
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class ViewSubjectQuizViewHolder(binding: RvViewSubjectQuizBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val quizName = binding.tvQuizName
        val viewQuestion = binding.ivViewQuizQuestion
    }
}