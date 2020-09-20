package com.example.mertkesgin.footballapp.repositories

import com.example.mertkesgin.footballapp.data.entries.Matches
import com.example.mertkesgin.footballapp.data.local.dao.MatchesDao
import com.example.mertkesgin.footballapp.data.remote.ApiService
import javax.inject.Inject

class MatchesRepositories @Inject constructor(
    val apiService: ApiService,
    val matchesDao: MatchesDao
) {
    suspend fun fetchLastMatches(id:String) = apiService.getLastMatches(id)

    suspend fun fetchNextMatches(id:String) = apiService.getNextMatches(id)

    suspend fun fetchMatchDetails(id: String) = apiService.getMatchDetails(id)

    suspend fun fetchTeamBadege(id: String) = apiService.getBadge(id)

    suspend fun insertMatch(match:Matches) = matchesDao.insertMatch(match)

    suspend fun deleteMatch(match: Matches) = matchesDao.deleteMatch(match)

    fun getFavouriteMatches() = matchesDao.getFavouriteMatches()

    fun isMatchExist(matchId:String) = matchesDao.isMatchExist(matchId)
}