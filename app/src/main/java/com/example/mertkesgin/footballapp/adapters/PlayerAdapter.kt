package com.example.mertkesgin.footballapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mertkesgin.footballapp.R
import com.example.mertkesgin.footballapp.data.entries.Player
import com.example.mertkesgin.footballapp.utils.ImageHelper
import kotlinx.android.synthetic.main.item_player.view.*

class PlayerAdapter : RecyclerView.Adapter<PlayerAdapter.PlayerViewholder>() {

    inner class PlayerViewholder(view: View) : RecyclerView.ViewHolder(view)

    val differCallback = object : DiffUtil.ItemCallback<Player>(){
        override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem.playerId == newItem.playerId
        }

        override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this,differCallback)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewholder {
        return PlayerViewholder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_player,
            parent,
            false
        ))
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    private var onItemClickListener : ((Player) -> Unit)? = null

    override fun onBindViewHolder(holder: PlayerViewholder, position: Int) {
        val player = differ.currentList[position]
        holder.itemView.apply {
            if(player.strCutout != null)
                ImageHelper().loadUrl(player.strCutout,imgItemPlayer)
            tvItemPlayerName.text = player.strPlayer

            setOnClickListener { onItemClickListener?.let { it(player) } }
        }
    }

    fun setOnItemClickListener(listener: (Player) -> Unit){
        onItemClickListener = listener
    }
}