package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class ViewQuestionAdapter:RecyclerView.Adapter<ViewQuestionAdapter.QuestionViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuestionViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.question_item, parent,false)
        return QuestionViewHolder(v)       }

    override fun onBindViewHolder(holder: QuestionViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    class QuestionViewHolder(view: View): RecyclerView.ViewHolder(view){
        val questionNumber: TextView = view.findViewById(R.id.option_number)
        val quetiontext : TextView = view.findViewById(R.id.option_text)
    }
}