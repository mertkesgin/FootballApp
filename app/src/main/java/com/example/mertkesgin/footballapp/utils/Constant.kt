package com.example.mertkesgin.footballapp.utils

object Constant {

    const val BASE_URL = "https://www.thesportsdb.com/api/v1/json/1/"

    const val LAST_MATCHES_ENDPOINT = "eventspastleague.php"
    const val NEXT_MATCHES_ENDPOINT = "eventsnextleague.php"
    const val MATCH_DETAILS_ENDPOINT = "lookupevent.php"
    const val TEAM_BADGE_ENDPOINT = "lookupteam.php"
    const val TEAMS_BY_LEAGUE_ENDPOINT = "search_all_teams.php"
    const val TEAM_DETAILS_ENDPOINT = "lookupteam.php"
    const val PLAYER_ENDPOINT = "searchplayers.php"
    const val PLAYER_DETAIILS_ENDPOINT = "lookupplayer.php"

    const val DATABASE_NAME = "football.db"

    const val GRID_LAYOUT_COLUMN_COUNT = 2

    const val SEARCH_PLAYER_TIME_DELAY = 500L

    fun getScore(homeScore:String?,awayScore:String?):String{
        return if (!homeScore.isNullOrEmpty() && !awayScore.isNullOrEmpty())
            "$homeScore - $awayScore"
        else " - "
    }
}