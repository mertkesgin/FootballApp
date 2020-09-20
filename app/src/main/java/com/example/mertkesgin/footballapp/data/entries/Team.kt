package com.example.mertkesgin.footballapp.data.entries

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "team_table")
data class Team(
    @SerializedName("idTeam")
    var teamId: String,
    var strTeamBadge: String? = null,
    var strTeam: String? = null,
    @SerializedName("intFormedYear")
    var formedYear: String? = null,
    var strStadium: String? = null,
    var strDescriptionEN: String? = null
): Serializable {
    @PrimaryKey(autoGenerate = true)
    var teamRdId: Int? = null
}