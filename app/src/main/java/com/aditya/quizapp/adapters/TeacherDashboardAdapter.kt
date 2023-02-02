package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class TeacherDashboardAdapter(
    private val mList: List<String>
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
        holder.btnViewQuestion.setOnClickListener {
            // Navigation.findNavController(it).navigate(R.id.action_teachersDashboard_to_teacherFragment)
        }
        holder.btnAddQuestion.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class TeacherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvSubject: TextView = view.findViewById(R.id.subject_title)
        var btnViewQuestion: Button = view.findViewById(R.id.button_add_s)
        var btnAddQuestion: Button = view.findViewById(R.id.button_add_q)

    }
}