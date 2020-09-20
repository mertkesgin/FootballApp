package com.example.mertkesgin.footballapp.data.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "match_table")
data class Matches(
    @SerializedName("idEvent")
    var eventId: String,
    var dateEvent: String? = null,
    var strTime: String? = null,
    @SerializedName("idHomeTeam")
    var homeTeamId: String? = null,
    var strHomeTeam: String? = null,
    @SerializedName("intHomeScore")
    var homeScore: String? = null,
    @SerializedName("idAwayTeam")
    var awayTeamId: String? = null,
    var strAwayTeam: String? = null,
    @SerializedName("intAwayScore")
    var awayScore: String? = null
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var matchId: Int? = null
}