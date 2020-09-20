package com.example.mertkesgin.footballapp.ui.teams

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mertkesgin.footballapp.base.BaseViewModel
import com.example.mertkesgin.footballapp.data.entries.Team
import com.example.mertkesgin.footballapp.data.entries.response.TeamDetailsResponse
import com.example.mertkesgin.footballapp.data.entries.response.TeamsResponse
import com.example.mertkesgin.footballapp.repositories.TeamsRepository
import com.example.mertkesgin.footballapp.utils.Resource
import kotlinx.coroutines.launch

class TeamsViewModel @ViewModelInject constructor(
    val teamsRepository: TeamsRepository
):BaseViewModel() {

    private val _teams:MutableLiveData<Resource<TeamsResponse>> = MutableLiveData()
    val teams:LiveData<Resource<TeamsResponse>>
        get() = _teams

    private val _teamDetails:MutableLiveData<Resource<TeamDetailsResponse>> = MutableLiveData()
    val teamDetails:LiveData<Resource<TeamDetailsResponse>>
        get() = _teamDetails

    fun getTeams(league:String) = viewModelScope.launch {
        _teams.postValue(Resource.Loading())
        _teams.postValue(getResult { teamsRepository.fetchAllTeams(league) })
    }

    fun getTeamDetails(id:String) = viewModelScope.launch {
        _teamDetails.postValue(Resource.Loading())
        _teamDetails.postValue(getResult { teamsRepository.fetchTeamDetails(id) })
    }

    fun insertTeam(team:Team) = viewModelScope.launch {
        teamsRepository.insertTeam(team)
    }

    fun deleteTeam(team: Team) = viewModelScope.launch {
        teamsRepository.deleteTeam(team)
    }

    val favouriteTeams = teamsRepository.getFavouriteTeams()

    fun isTeamExist(teamId:String) = teamsRepository.isTeamExist(teamId)
}