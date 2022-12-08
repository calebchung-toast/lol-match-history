package com.example.lolmatchhistory.ui.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import com.example.lolmatchhistory.R
import com.example.lolmatchhistory.models.Match

class MatchDialogFragment(val match: Match) : DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.match_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val user = match.players.find { player ->
            (player.summonerName.lowercase() == match.playerName.lowercase()) }!!

        view.findViewById<TextView>(R.id.match_date).text = match.matchDate.substringBefore("T")
        view.findViewById<TextView>(R.id.blue_team_user_0).text = match.players[0].summonerName
        view.findViewById<TextView>(R.id.blue_team_user_1).text = match.players[1].summonerName
        view.findViewById<TextView>(R.id.blue_team_user_2).text = match.players[2].summonerName
        view.findViewById<TextView>(R.id.blue_team_user_3).text = match.players[3].summonerName
        view.findViewById<TextView>(R.id.blue_team_user_4).text = match.players[4].summonerName
        view.findViewById<TextView>(R.id.red_team_user_0).text = match.players[5].summonerName
        view.findViewById<TextView>(R.id.red_team_user_1).text = match.players[6].summonerName
        view.findViewById<TextView>(R.id.red_team_user_2).text = match.players[7].summonerName
        view.findViewById<TextView>(R.id.red_team_user_3).text = match.players[8].summonerName
        view.findViewById<TextView>(R.id.red_team_user_4).text = match.players[9].summonerName

        view.findViewById<TextView>(R.id.match_score).text = match.blueSideKills.toString() + "-" + match.redSideKills.toString()
        view.findViewById<TextView>(R.id.match_result).text = if (match.winningTeam == user.teamId) { "VICTORY" } else { "DEFEAT" }
    }
}