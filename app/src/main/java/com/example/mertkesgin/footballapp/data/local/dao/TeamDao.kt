package com.example.mertkesgin.footballapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mertkesgin.footballapp.data.entries.Team

@Dao
interface TeamDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertTeam(team: Team)

    @Delete
    suspend fun deleteTeam(team: Team)

    @Query("SELECT * FROM team_table")
    fun getFavouriteTeams(): LiveData<List<Team>>

    @Query("SELECT EXISTS(SELECT * FROM team_table WHERE teamId = :teamId)")
    fun isTeamExist(teamId:String) : LiveData<Boolean>
}