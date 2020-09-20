package com.example.mertkesgin.footballapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mertkesgin.footballapp.data.entries.Player

@Dao
interface PlayerDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlayer(player: Player)

    @Delete
    suspend fun deletePlayer(player: Player)

    @Query("SELECT * FROM player_table")
    fun getFavouritePlayers(): LiveData<List<Player>>

    @Query("SELECT EXISTS(SELECT * FROM player_table WHERE playerId = :playerId)")
    fun isPlayerExist(playerId:String) : LiveData<Boolean>
}