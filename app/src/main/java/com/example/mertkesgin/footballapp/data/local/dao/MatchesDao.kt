package com.example.mertkesgin.footballapp.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.mertkesgin.footballapp.data.entries.Matches

@Dao
interface MatchesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMatch(match:Matches)

    @Delete
    suspend fun deleteMatch(match:Matches)

    @Query("SELECT * FROM match_table")
    fun getFavouriteMatches(): LiveData<List<Matches>>

    @Query("SELECT EXISTS(SELECT * FROM match_table WHERE eventId = :matchId)")
    fun isMatchExist(matchId:String) : LiveData<Boolean>
}