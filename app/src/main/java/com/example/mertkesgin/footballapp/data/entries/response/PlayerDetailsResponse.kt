package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.PlayerDetails
import com.google.gson.annotations.SerializedName

class PlayerDetailsResponse(
    @SerializedName("players")
    val playerDetails:List<PlayerDetails>
) {
}