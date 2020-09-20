package com.example.mertkesgin.footballapp.data.entries.response

import com.example.mertkesgin.footballapp.data.entries.Matches
import com.google.gson.annotations.SerializedName

data class MatchesResponse(
    @SerializedName("events")
    val results: List<Matches>
) {
}