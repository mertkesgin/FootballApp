package com.example.mertkesgin.footballapp.repositories

import com.example.mertkesgin.footballapp.data.entries.Player
import com.example.mertkesgin.footballapp.data.local.dao.PlayerDao
import com.example.mertkesgin.footballapp.data.remote.ApiService
import javax.inject.Inject

class PlayerRepository @Inject constructor(
    val apiService: ApiService,
    val playerDao: PlayerDao
) {
    suspend fun searchPlayer(name:String) = apiService.searchPlayer(name)

    suspend fun fetchPlayerDetails(id:String) = apiService.getPlayerDetails(id)

    suspend fun insertPlayer(player:Player) = playerDao.insertPlayer(player)

    suspend fun deletePlayer(player: Player) = playerDao.deletePlayer(player)

    fun getFavouritePlayers() = playerDao.getFavouritePlayers()

    fun isPlayerExist(playerId:String) = playerDao.isPlayerExist(playerId)
}