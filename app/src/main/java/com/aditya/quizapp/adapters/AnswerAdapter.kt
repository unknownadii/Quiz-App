package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class AnswerAdapter : RecyclerView.Adapter<AnswerAdapter.AnswerViewHolder>() {

    class AnswerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var answers = view.findViewById<TextView>(R.id.answer)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnswerViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.answers_item, parent, false)
        return AnswerViewHolder(v)
    }

    override fun onBindViewHolder(holder: AnswerViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
}