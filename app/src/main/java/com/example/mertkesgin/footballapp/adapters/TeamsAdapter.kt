package com.example.mertkesgin.footballapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.data.entries.Team
import com.example.mertkesgin.footballapp.utils.ImageHelper
import kotlinx.android.synthetic.main.item_team.view.*

class TeamsAdapter : RecyclerView.Adapter<TeamsAdapter.TeamsViewHolder>() {

    inner class TeamsViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    val differCallBack = object : DiffUtil.ItemCallback<Team>(){
        override fun areItemsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem.teamId == newItem.teamId
        }

        override fun areContentsTheSame(oldItem: Team, newItem: Team): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamsViewHolder {
        return TeamsViewHolder(
            LayoutInflater.from(parent.context).inflate(
            R.layout.item_team,
            parent,
            false
        ))
    }

    private var onItemClickListener: ((Team) -> Unit)? = null

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: TeamsViewHolder, position: Int) {
        val team = differ.currentList[position]
        holder.itemView.apply {
            tvItemTeam.text = team.strTeam
            ImageHelper().loadUrl(team.strTeamBadge!!,imgItemTeam)

            setOnClickListener { onItemClickListener?.let { it(team) } }
        }
    }

    fun setOnItemClickListener(listener: (Team) -> Unit){
        onItemClickListener = listener
    }
}