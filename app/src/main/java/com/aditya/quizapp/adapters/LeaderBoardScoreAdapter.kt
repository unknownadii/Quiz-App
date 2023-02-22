package com.aditya.quizapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.quizapp.databinding.RvLeaderboardScoreBinding
import com.aditya.quizapp.databinding.RvViewSubjectQuizBinding
import com.aditya.quizapp.models.responseLeaderboardScore.Data

class LeaderBoardScoreAdapter(
    private val mList: List<Data>,
    private val onItemClick: ((position: Int, quizName: String) -> Unit)
) :
    RecyclerView.Adapter<LeaderBoardScoreAdapter.LeaderBoardScoreViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardScoreViewHolder {
        val binding =
            RvLeaderboardScoreBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderBoardScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderBoardScoreViewHolder, position: Int) {
        val item = mList[position]
        holder.studentId.text = item.id.toString()
        holder.studentName.text = "Student ${item.student_id}"
        holder.studentScore.text = item.score.toString()
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    class LeaderBoardScoreViewHolder(binding: RvLeaderboardScoreBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val studentName = binding.tvScoreStuName
        val studentId = binding.tvScoreID
        val studentScore = binding.tvScoreStuScore
    }
}