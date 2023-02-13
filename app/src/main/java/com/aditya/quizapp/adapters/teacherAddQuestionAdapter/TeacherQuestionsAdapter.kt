package com.aditya.quizapp.adapters.teacherAddQuestionAdapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatEditText
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.R
import com.aditya.quizapp.dataModel.DynamicOptionsDataModel
import com.aditya.quizapp.dataModel.DynamicQuestionsDataModel
import com.aditya.quizapp.models.addQuestionsToQuiz.request.Question
import com.aditya.quizapp.models.responseTeacherDashboard.messages
import okio.blackholeSink

class TeacherQuestionsAdapter(
    private val click: performClick,
    private val qList: ArrayList<DynamicQuestionsDataModel>,
    private val onSubmitQuiz: ((position: Int, messages: String, showOrNot: Boolean, question: Question?) -> Unit)
) : RecyclerView.Adapter<TeacherQuestionsAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.add_questions_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = qList[position]

        holder.btnDeleteQuestion.setOnClickListener {
            click.deleteQuestion(holder.absoluteAdapterPosition)
        }

        if (item.questionType == "True/False") {
            holder.option3.visibility = View.GONE
            holder.option3.text = null
            holder.option4.visibility = View.GONE
            holder.option4.text = null
            holder.checkBox3.visibility = View.GONE
            holder.checkBox4.visibility = View.GONE
        }

        holder.btnSubmitQuestion.setOnClickListener {
            if (holder.etQuestion.text.isNullOrEmpty()) {
                onSubmitQuiz(position, "Please Enter Question", true, null)
            } else if ((item.questionType == "True/False" && holder.option1.text.isNullOrEmpty()) ||
                (item.questionType == "True/False" && holder.option2.text.isNullOrEmpty())
            ) {
                onSubmitQuiz(position, "Please Enter Option For True/False", true, null)
            } else if ((item.questionType == "Multiple Choice") && (holder.option1.text.isNullOrEmpty() ||
                        holder.option2.text.isNullOrEmpty() || holder.option3.text.isNullOrEmpty() || holder.option4.text.isNullOrEmpty())
            ) {
                onSubmitQuiz(position, "Please Enter Option", true, null)
            } else if (item.questionType == "True/False" && (!holder.checkBox1.isChecked && !holder.checkBox2.isChecked)) {
                onSubmitQuiz(
                    position,
                    "Please select one correct option for True/False",
                    true,
                    null
                )
            } else if (!holder.checkBox1.isChecked && !holder.checkBox2.isChecked && !holder.checkBox3.isChecked && !holder.checkBox4.isChecked) {
                onSubmitQuiz(position, "Please select one correct option", true, null)
            } else {
                var correctAns = ""
                if (holder.checkBox1.isChecked) {
                    correctAns = holder.option1.text.toString()
                } else if (holder.checkBox2.isChecked) {
                    correctAns = holder.option2.text.toString()
                } else if (holder.checkBox3.isChecked) {
                    correctAns = holder.option3.text.toString()
                } else if (holder.checkBox4.isChecked) {
                    correctAns = holder.option4.text.toString()
                }
                var dataList: Question?  = null
                if (item.questionType == "True/False") {
                    dataList =
                        Question(
                            correctAns, arrayListOf(
                                holder.option1.text.toString(),
                                holder.option2.text.toString(),
                                holder.option3.text.toString(),
                                holder.option4.text.toString(),
                            ), holder.etQuestion.text.toString()
                        )
                }
                else {
                    dataList =
                        Question(
                            correctAns, arrayListOf(
                                holder.option1.text.toString(),
                                holder.option2.text.toString(),
                                holder.option3.text.toString(),
                                holder.option4.text.toString(),
                            ), holder.etQuestion.text.toString()
                        )
                }

                Log.d("CorrectAnswer", dataList.toString())
                onSubmitQuiz(position, "Added in queue to upload", false, dataList)
                click.deleteQuestion(holder.absoluteAdapterPosition)
            }
        }
    }

    override fun getItemCount(): Int {
        return qList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val btnDeleteQuestion: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnDeleteQuestion)
        val btnSubmitQuestion: androidx.appcompat.widget.AppCompatButton =
            ItemView.findViewById(R.id.btnSubmitQuestion)
        val etQuestion: AppCompatEditText = ItemView.findViewById(R.id.etEnterQuestion)
        val option1 = ItemView.findViewById<AppCompatEditText>(R.id.etOption1)
        val option2 = ItemView.findViewById<AppCompatEditText>(R.id.etOption2)
        val option3 = ItemView.findViewById<AppCompatEditText>(R.id.etOption3)
        val option4 = ItemView.findViewById<AppCompatEditText>(R.id.etOption4)
        val radioGrp = ItemView.findViewById<RadioGroup>(R.id.rgOptions)
        val checkBox1 = ItemView.findViewById<RadioButton>(R.id.cbOption1)
        val checkBox2 = ItemView.findViewById<RadioButton>(R.id.cbOption2)
        val checkBox3 = ItemView.findViewById<RadioButton>(R.id.cbOption3)
        val checkBox4 = ItemView.findViewById<RadioButton>(R.id.cbOption4)
    }

    interface performClick {
        fun deleteQuestion(position: Int)
    }
}