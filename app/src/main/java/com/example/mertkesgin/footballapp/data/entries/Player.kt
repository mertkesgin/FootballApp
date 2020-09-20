package com.example.mertkesgin.footballapp.data.entries

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity(tableName = "player_table")
data class Player(
    @SerializedName("idPlayer")
    @NonNull
    @PrimaryKey
    var playerId:String,
    var strPlayer:String? = null,
    var strCutout:String
): Serializable {
}