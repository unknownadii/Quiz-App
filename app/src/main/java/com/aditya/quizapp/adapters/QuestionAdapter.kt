package com.example.quizapplication

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.example.quizapplication.models.Quetions

class QuestionAdapter(private val mList: List<Quetions>,val context: Context): RecyclerView.Adapter<ItemViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val v =LayoutInflater.from(parent.context).inflate(R.layout.rvitem,parent,false)
        return ItemViewHolder(v)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val items = mList[position]
        holder.question.text = items.question
        holder.option1.text = items.option1
        holder.option2.text = items.option2
        holder.option3.text = items.option3
        holder.option4.text = items.option4

        holder.option1.setOnClickListener{
            if (items.option1 == items.answer) {
                holder.option1.setBackgroundColor(ContextCompat.getColor(context, R.color.black))
            }
        }

    }

}
class ItemViewHolder(view : View):RecyclerView.ViewHolder(view){
    var question =view.findViewById<TextView>(R.id.textView2)
    var option1 = view.findViewById<Button>(R.id.button1)
    var option2 = view.findViewById<Button>(R.id.button2)
    var option3 = view.findViewById<Button>(R.id.button3)
    var option4 = view.findViewById<Button>(R.id.button4)

}

