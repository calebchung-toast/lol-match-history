package com.example.lolmatchhistory.models

import android.os.Build
import androidx.annotation.RequiresApi
import okhttp3.*
import okhttp3.RequestBody.Companion.toRequestBody
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException

data class Match (
    val matchDate: String,
    val winningTeam: Int,
    val blueSideKills: Int,
    val redSideKills: Int,
    val players: List<Player>,

)
data class Player (
    val summonerName: String,
    val position: String,
    val champName: String,
    val kills: Int,
    val deaths: Int,
    val assists: Int,
    val teamId: Int,
)
enum class Position {

}
class MatchHistoryModel {

    private val apiKey: String = "RGAPI-e47ccb78-7b08-47d7-8b5c-f6d889672c8d"
    private val numMatches: Int = 3

    @RequiresApi(Build.VERSION_CODES.O)
    fun getMatches(summonerName: String): List<Match> {
        System.out.println("Getting matches for $summonerName")
        // Get PUUID based on username
        val userPuuidJson = JSONObject(makeRequest("https://na1.api.riotgames.com/lol/summoner/v4/summoners/by-name/$summonerName?api_key=$apiKey"))
        val puuid = userPuuidJson.get("puuid") as String
        // Get matches based on PUUID
        val matchesJson = JSONArray(makeRequest("https://americas.api.riotgames.com/lol/match/v5/matches/by-puuid/" +
                puuid +
                "/ids?start=0&count=$numMatches&api_key=$apiKey"))

        // Iterate through each match
        var matches: MutableList<Match> = ArrayList()
        for (matchIndex in 0 until matchesJson.length()) {
            val matchId = matchesJson.get(matchIndex)
            var matchJson = JSONObject(makeRequest("https://americas.api.riotgames.com/lol/match/v5/matches/$matchId?api_key=$apiKey"))

            // Convert match JSON to Match class
            // Create list of Player objects
            val participantsArray = matchJson.getJSONObject("info").getJSONArray("participants")
            var players: MutableList<Player> = ArrayList()
            var winningTeam = 100;
            var blueSideKills = 0;
            var redSideKills = 0;
            for (participantIdx in 0 until participantsArray.length()) {
                val participant: JSONObject = participantsArray[participantIdx] as JSONObject
                val summonerName = participant.get("summonerName") as String
                val position = participant.get("teamPosition") as String
                val champName = participant.get("championName") as String
                val kills = participant.get("kills") as Int
                val deaths = participant.get("deaths") as Int
                val assists = participant.get("assists") as Int
                val teamId = participant.get("teamId") as Int
                if (teamId == 100) {
                    blueSideKills += kills
                }
                else {
                    redSideKills += kills
                }
                if (participant.get("win") as Boolean) {
                    winningTeam = teamId
                }
                val playerObject = Player(summonerName, position, champName, kills, deaths, assists, teamId)
                players.add(playerObject)
            }

            // Convert unix timestamp to date
            var matchDate = matchJson.getJSONObject("info").get("gameStartTimestamp")
            matchDate = java.time.format.DateTimeFormatter.ISO_INSTANT
                .format(java.time.Instant.ofEpochMilli(matchDate as Long))
            val match = Match(matchDate, winningTeam, blueSideKills, redSideKills, players)
            matches.add(match)
        }
        System.out.println(matches)
        return matches
    }

    // Make the request and return the response as a JSON
    private fun makeRequest(url: String) : String {
        val okHttpClient = OkHttpClient()
        val request = Request.Builder()
            .method("GET", null)
            .url(url)
            .build()
        val foo = okHttpClient.newCall(request).execute()
        return foo.body?.string()!!
    }
}