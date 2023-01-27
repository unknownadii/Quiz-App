package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class StudentDashboardAdapter :RecyclerView.Adapter<StudentDashboardAdapter.SubjectViewHolder>() {

    val items = arrayListOf("d116df5",
        "36ffc75", "f5cfe78", "5b87628",
        "db8d14e", "9913dc4", "e120f96",
        "466251b")


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubjectViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.sub_item,parent,false)
        return SubjectViewHolder(v)
    }

    override fun onBindViewHolder(holder: SubjectViewHolder, position: Int) {
        holder.textView.text=items[position]
        holder.btn.text = items.toString()
        if(holder.btn.text.isEmpty()){
            holder.btn.setOnClickListener{

            }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }

    class SubjectViewHolder(view: View):RecyclerView.ViewHolder(view){
        var textView: TextView = view.findViewById(R.id.subject_name)
        var btn: Button = view.findViewById(R.id.button_start)


    }
}