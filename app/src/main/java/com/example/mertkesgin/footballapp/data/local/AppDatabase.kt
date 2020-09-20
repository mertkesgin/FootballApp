package com.example.mertkesgin.footballapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mertkesgin.footballapp.data.entries.Matches
import com.example.mertkesgin.footballapp.data.entries.Player
import com.example.mertkesgin.footballapp.data.entries.Team
import com.example.mertkesgin.footballapp.data.local.dao.MatchesDao
import com.example.mertkesgin.footballapp.data.local.dao.PlayerDao
import com.example.mertkesgin.footballapp.data.local.dao.TeamDao

@Database(
    entities = [Matches::class, Team::class, Player::class],
    version = 1
)
@TypeConverters(RoomDbTypeConverters::class)
abstract class AppDatabase : RoomDatabase(){

    abstract fun getMatchDao(): MatchesDao

    abstract fun getTeamDao(): TeamDao

    abstract fun getPlayerDao(): PlayerDao
}