package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class QuestionAdapter(): RecyclerView.Adapter<QuestionAdapter.ItemViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.option_student, parent, false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        TODO("kulaks")
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.option_box.setOnClickListener {
            if (holder.option_box is CheckBox) {
                val checked: Boolean = holder.option_box.isChecked
                val option_number = holder.option_box.id
            }

        }

    }

    class ItemViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val option_box: CheckBox = view.findViewById(R.id.checkBox)

    }
}

