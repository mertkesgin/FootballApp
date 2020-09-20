package com.example.mertkesgin.footballapp.data.entries

data class MatchDetails(
    val idEvent: String?,
    val dateEvent: String?,
    val strTime: String?,
    val strLeague: String?,
    val idHomeTeam: String?,
    val strHomeTeam: String?,
    val intHomeScore: String?,
    val intHomeShots: String?,
    val intAwayShots: String?,
    val strHomeFormation: String?,
    val strHomeGoalDetails: String?,
    val strHomeLineupGoalkeeper: String?,
    val strHomeLineupDefense: String?,
    val strHomeLineupMidfield: String?,
    val strHomeLineupForward: String?,
    val strHomeLineupSubstitutes: String?,
    val idAwayTeam: String?,
    val strAwayTeam: String?,
    val intAwayScore: String?,
    val strAwayFormation: String?,
    val strAwayGoalDetails: String?,
    val strAwayLineupGoalkeeper: String?,
    val strAwayLineupDefense: String?,
    val strAwayLineupMidfield: String?,
    val strAwayLineupForward: String?,
    val strAwayLineupSubstitutes: String?
) {
}