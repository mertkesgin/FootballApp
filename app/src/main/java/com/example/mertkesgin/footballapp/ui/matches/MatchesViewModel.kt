package com.example.mertkesgin.footballapp.ui.matches

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mertkesgin.footballapp.base.BaseViewModel
import com.example.mertkesgin.footballapp.data.entries.Matches
import com.example.mertkesgin.footballapp.data.entries.response.BadgeResponse
import com.example.mertkesgin.footballapp.data.entries.response.MatchDetailsResponse
import com.example.mertkesgin.footballapp.data.entries.response.MatchesResponse
import com.example.mertkesgin.footballapp.repositories.MatchesRepositories
import com.example.mertkesgin.footballapp.utils.Resource
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

class MatchesViewModel @ViewModelInject constructor(
    val matchesRepositories: MatchesRepositories
): BaseViewModel(){

    private val _lastMatches:MutableLiveData<Resource<MatchesResponse>> = MutableLiveData()
    val lastMatches:LiveData<Resource<MatchesResponse>>
        get() = _lastMatches

    private val _nextMatches:MutableLiveData<Resource<MatchesResponse>> = MutableLiveData()
    val nextMatches:LiveData<Resource<MatchesResponse>>
        get() = _nextMatches

    private val _matchDetails:MutableLiveData<Resource<MatchDetailsResponse>> = MutableLiveData()
    val matchDetails:LiveData<Resource<MatchDetailsResponse>>
        get() = _matchDetails

    private val _teamBadgeHome:MutableLiveData<Resource<BadgeResponse>> = MutableLiveData()
    val teamBadgeHome get() = _teamBadgeHome

    private val _teamBadgeAway:MutableLiveData<Resource<BadgeResponse>> = MutableLiveData()
    val teamBadgeAway get() = _teamBadgeAway

    fun getLastMatches(leaguId:String) = viewModelScope.launch {
        _lastMatches.postValue(Resource.Loading())
        _lastMatches.postValue(getResult { matchesRepositories.fetchLastMatches(leaguId) })
    }

    fun getNextMatches(leaguId:String) = viewModelScope.launch {
        _nextMatches.postValue(Resource.Loading())
        _nextMatches.postValue(getResult { matchesRepositories.fetchNextMatches(leaguId) })
    }

    fun getMatchDetails(matchId:String) = viewModelScope.launch {
        _matchDetails.postValue(Resource.Loading())
        _matchDetails.postValue(getResult { matchesRepositories.fetchMatchDetails(matchId) })
    }

    fun getBadges(teamHomeId:String,teamAwayId:String) = viewModelScope.launch {
        _teamBadgeHome.postValue(Resource.Loading())
        _teamBadgeAway.postValue(Resource.Loading())
        coroutineScope {
            val homeBadge = async { matchesRepositories.fetchTeamBadege(teamHomeId) }
            val awayBadge = async { matchesRepositories.fetchTeamBadege(teamAwayId) }

            val callHome = homeBadge.await()
            val callAway = awayBadge.await()

            _teamBadgeHome.postValue(getResult { callHome })
            _teamBadgeAway.postValue(getResult { callAway })
        }
    }

    fun insertMatch(match:Matches) = viewModelScope.launch {
        matchesRepositories.insertMatch(match)
    }

    val favouritesMatches = matchesRepositories.getFavouriteMatches()

    fun isMatchExist(matchId:String) = matchesRepositories.isMatchExist(matchId)
}