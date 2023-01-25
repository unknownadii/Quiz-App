package com.aditya.quizapp.dataModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R

class TeachersOptionsAdapter(
    private val click: performQuestionsClick,
    private val oList: ArrayList<DynamicOptionsDataModel>
) :
    RecyclerView.Adapter<TeachersOptionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_options_layout, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return oList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = oList[position]
        holder.deleteBtn.setOnClickListener {
            click.deleteOption(position)
        }

    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val deleteBtn: ImageView = itemView.findViewById(R.id.btnDeleteOptions)
    }

    interface performQuestionsClick {
        fun deleteOption(position: Int)
    }

}