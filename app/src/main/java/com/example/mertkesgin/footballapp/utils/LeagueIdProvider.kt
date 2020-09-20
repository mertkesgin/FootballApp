package com.example.mertkesgin.footballapp.utils

object LeagueIdProvider {

    const val TURKISH_LEAGUE = "Turkish Super Lig"
    const val TURKISH_LEAGUE_ID = "4339"
    const val PREMIER_LEAGUE = "English Premier League"
    const val PREMIER_LEAGUE_ID = "4328"
    const val LA_LIGA = "Spanish La Liga"
    const val LA_LIGA_ID = "4335"
    const val BUNDESLIGA = "German Bundesliga"
    const val BUNDESLIGA_ID = "4331"
    const val SERIE_A = "Italian Serie A"
    const val SERIE_A_ID = "4332"
    const val LIGUE_1 = "French Ligue 1"
    const val LIGUE_1_ID = "4484"

    fun getLeagueId(league:String):String{
        return when (league) {
            PREMIER_LEAGUE -> PREMIER_LEAGUE_ID
            LA_LIGA -> LA_LIGA_ID
            BUNDESLIGA -> BUNDESLIGA_ID
            SERIE_A -> SERIE_A_ID
            LIGUE_1 -> LIGUE_1_ID
            TURKISH_LEAGUE -> TURKISH_LEAGUE_ID
            else -> ""
        }
    }
}