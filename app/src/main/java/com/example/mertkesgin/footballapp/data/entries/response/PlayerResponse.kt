package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.Player
import com.google.gson.annotations.SerializedName

data class PlayerResponse (
    @SerializedName("player")
    val players:List<Player>
){
}