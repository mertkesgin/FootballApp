package com.example.mertkesgin.footballapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.data.entries.Matches
import kotlinx.android.synthetic.main.item_match.view.*

class MatchesAdapter :RecyclerView.Adapter<MatchesAdapter.MatchesViewHolder>() {

    inner class MatchesViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView)

    val differCallBack = object : DiffUtil.ItemCallback<Matches>(){
        override fun areItemsTheSame(oldItem: Matches, newItem: Matches): Boolean {
            return oldItem.eventId == newItem.eventId
        }

        override fun areContentsTheSame(oldItem: Matches, newItem: Matches): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        return MatchesViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_match,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener: ((Matches) -> Unit)? = null

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val match = differ.currentList[position]
        holder.itemView.apply {
            tvHomeTeam.text = match.strHomeTeam
            tvAwayTeam.text = match.strAwayTeam
            tvHomeScore.text = match.homeScore
            tvAwayScore.text = match.awayScore
            tvDateMatch.text = "${match.strTime}/${match.dateEvent}"

            setOnClickListener { onItemClickListener?.let { it(match) } }
        }
    }

    fun setOnItemClickListener(listener: (Matches) -> Unit){
        onItemClickListener = listener
    }
}