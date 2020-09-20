package com.example.mertkesgin.footballapp.ui.player

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.mertkesgin.footballapp.base.BaseViewModel
import com.example.mertkesgin.footballapp.data.entries.Player
import com.example.mertkesgin.footballapp.data.entries.response.PlayerDetailsResponse
import com.example.mertkesgin.footballapp.data.entries.response.PlayerResponse
import com.example.mertkesgin.footballapp.repositories.PlayerRepository
import com.example.mertkesgin.footballapp.utils.Resource
import kotlinx.coroutines.launch

class PlayerViewModel @ViewModelInject constructor (
    val playerRepository: PlayerRepository
): BaseViewModel() {

    private val _player: MutableLiveData<Resource<PlayerResponse>> = MutableLiveData()
    val player: LiveData<Resource<PlayerResponse>>
        get() = _player

    private val _playerDetails: MutableLiveData<Resource<PlayerDetailsResponse>> = MutableLiveData()
    val playerDetails: LiveData<Resource<PlayerDetailsResponse>>
        get() = _playerDetails

    fun searchPlayer(name:String) = viewModelScope.launch {
        _player.postValue(Resource.Loading())
        _player.postValue(getResult { playerRepository.searchPlayer(name) })
    }

    fun getPlayerDetails(id:String) = viewModelScope.launch {
        _playerDetails.postValue(Resource.Loading())
        _playerDetails.postValue(getResult { playerRepository.fetchPlayerDetails(id) })
    }

    fun insertPlayer(player:Player) = viewModelScope.launch {
        playerRepository.insertPlayer(player)
    }

    fun deletePlayer(player: Player) = viewModelScope.launch {
        playerRepository.deletePlayer(player)
    }

    val favouritePlayers = playerRepository.getFavouritePlayers()

    fun isPlayerExist(playerId:String) = playerRepository.isPlayerExist(playerId)
}