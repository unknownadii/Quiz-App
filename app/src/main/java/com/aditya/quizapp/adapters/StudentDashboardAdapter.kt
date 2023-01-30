package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.models.StudentDashboardModel

class StudentDashboardAdapter(private val click : ClickEvent,private val items: List<StudentDashboardModel>) :RecyclerView.Adapter<StudentDashboardAdapter.SubjectViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sub_item,parent,false)
        return SubjectViewHolder(v)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        val item = items[position]
        holder.textView.text= item.sub.toString()
        holder.btn.text = item.data.toString()
        if(holder.btn.text.isEmpty()){
            holder.btn.text = "START"

        }
        else{
            holder.btn.isEnabled = false
            holder.btn.isClickable = false
        }


    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SubjectViewHolder(view: View):RecyclerView.ViewHolder(view){
        var textView: TextView = view.findViewById(R.id.subject_name)
        var btn: Button = view.findViewById(R.id.button_start)

    }

    interface ClickEvent{
        fun buttonClick()
    }
}