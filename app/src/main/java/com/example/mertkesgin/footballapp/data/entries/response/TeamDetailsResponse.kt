package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.TeamDetails
import com.google.gson.annotations.SerializedName

data class TeamDetailsResponse (
    @SerializedName("teams")
    val teamDetails:List<TeamDetails>
) {
}