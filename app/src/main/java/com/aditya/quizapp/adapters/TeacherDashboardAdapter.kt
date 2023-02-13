package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class TeacherDashboardAdapter(
    private val mList: List<String>,
    private val onItemClickViewQuiz: ((position: Int, subject: String) -> Unit),
    private val onItemClickAddQuestion: ((position: Int, subject: String) -> Unit)
) :
    RecyclerView.Adapter<TeacherDashboardAdapter.TeacherViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeacherViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.sub_item_teacher, parent, false)
        return TeacherViewHolder(v)
    }

    override fun onBindViewHolder(holder: TeacherViewHolder, position: Int) {
        val items = mList[position]
        holder.tvSubject.text = items
        holder.btnAddQuestion.setOnClickListener {
            onItemClickAddQuestion.invoke(position, holder.tvSubject.text.toString())
        }
        holder.btnViewQuestion.setOnClickListener {
            onItemClickViewQuiz.invoke(position, holder.tvSubject.text.toString())
        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSubject: TextView = view.findViewById(R.id.subject_title)
        var btnViewQuestion: Button = view.findViewById(R.id.button_add_q)
        var btnAddQuestion: Button = view.findViewById(R.id.button_add_s)

    }
}