package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.models.getQuestion.StudentQuestion

class QuestionAdapter(private val question: List<StudentQuestion>) :
    RecyclerView.Adapter<QuestionAdapter.ItemViewHolder>() {

    lateinit var optionsAdapter: OptionsAdapter



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.option_student, parent, false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return question.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = question[position]
        holder.question.text = items.data.toString()
        holder.options.text = items.Message


    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val question: TextView = view.findViewById(R.id.questions)
        val options : CheckBox = view.findViewById(R.id.checkBox)

    }
}

