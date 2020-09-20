package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.MatchDetails
import com.google.gson.annotations.SerializedName

data class MatchDetailsResponse(
    @SerializedName("events")
    val eventsDetails: List<MatchDetails>
) {

}