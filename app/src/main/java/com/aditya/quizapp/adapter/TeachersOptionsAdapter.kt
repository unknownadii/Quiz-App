package com.aditya.quizapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.dataModel.DynamicOptionsDataModel

class TeachersOptionsAdapter(
    private val click: performQuestionsClick,
    private val oList: ArrayList<DynamicOptionsDataModel>, private val parentPosition: Int
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
            click.deleteOption(holder.adapterPosition, parentPosition)
        }
        holder.etOption.setText("${holder.adapterPosition}")
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val deleteBtn: ImageView = ItemView.findViewById(R.id.btnDeleteOptions)
        val etOption: AppCompatEditText = ItemView.findViewById(R.id.etAddOptions)
    }

    interface performQuestionsClick {
        fun deleteOption(childPosition: Int, parentPosition: Int)
    }

}