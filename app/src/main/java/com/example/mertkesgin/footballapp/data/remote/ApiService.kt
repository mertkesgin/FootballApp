package com.example.mertkesgin.footballapp.data.remote

import com.example.mertkesgin.footballapp.data.entries.response.*
import com.example.mertkesgin.footballapp.utils.Constant.LAST_MATCHES_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.MATCH_DETAILS_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.NEXT_MATCHES_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.PLAYER_DETAIILS_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.PLAYER_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.TEAMS_BY_LEAGUE_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.TEAM_BADGE_ENDPOINT
import com.example.mertkesgin.footballapp.utils.Constant.TEAM_DETAILS_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET(LAST_MATCHES_ENDPOINT)
    suspend fun getLastMatches(
        @Query("id") id: String
    ): Response<MatchesResponse>

    @GET(NEXT_MATCHES_ENDPOINT)
    suspend fun getNextMatches(
        @Query("id") id: String
    ): Response<MatchesResponse>

    @GET(MATCH_DETAILS_ENDPOINT)
    suspend fun getMatchDetails(
        @Query("id") id: String
    ): Response<MatchDetailsResponse>

    @GET(TEAM_BADGE_ENDPOINT)
    suspend fun getBadge(
        @Query("id") id: String
    ): Response<BadgeResponse>

    @GET(TEAMS_BY_LEAGUE_ENDPOINT)
    suspend fun getAllTeamsByLeague(
        @Query("l") league: String
    ): Response<TeamsResponse>

    @GET(TEAM_DETAILS_ENDPOINT)
    suspend fun getTeamDetails(
        @Query("id") id: String
    ): Response<TeamDetailsResponse>

    @GET(PLAYER_ENDPOINT)
    suspend fun searchPlayer(
        @Query("p") name:String
    ): Response<PlayerResponse>

    @GET(PLAYER_DETAIILS_ENDPOINT)
    suspend fun getPlayerDetails(
        @Query("id") id: String
    ): Response<PlayerDetailsResponse>
}