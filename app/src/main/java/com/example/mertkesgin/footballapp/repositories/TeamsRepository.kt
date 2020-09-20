package com.example.mertkesgin.footballapp.repositories

import com.example.mertkesgin.footballapp.data.entries.Team
import com.example.mertkesgin.footballapp.data.local.dao.TeamDao
import com.example.mertkesgin.footballapp.data.remote.ApiService
import javax.inject.Inject

class TeamsRepository @Inject constructor(
    val apiService: ApiService,
    val teamDao: TeamDao
) {
    suspend fun fetchAllTeams(league:String) = apiService.getAllTeamsByLeague(league)

    suspend fun fetchTeamDetails(id:String) = apiService.getTeamDetails(id)

    suspend fun insertTeam(team: Team) = teamDao.insertTeam(team)

    suspend fun deleteTeam(team: Team) = teamDao.deleteTeam(team)

    fun getFavouriteTeams() = teamDao.getFavouriteTeams()

    fun isTeamExist(teamId:String) = teamDao.isTeamExist(teamId)
}