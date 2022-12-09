package com.example.lolmatchhistory.adapters

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.example.lolmatchhistory.R
import com.example.lolmatchhistory.models.Match
import com.example.lolmatchhistory.ui.main.MatchDialogFragment

class MatchAdapter(
    private val context: Context,
    private val dataset: List<Match>,
    private val fragmentManager: FragmentManager
) : RecyclerView.Adapter<MatchAdapter.ItemViewHolder>() {

    class ItemViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val dateText: TextView = view.findViewById(R.id.match_date)
        val championText: TextView = view.findViewById(R.id.user_champion)
        val scoreText: TextView = view.findViewById(R.id.match_score)
        val kdaText: TextView = view.findViewById(R.id.user_kda)
        val matchResultText: TextView = view.findViewById(R.id.match_result)
    }

    /**
     * Create new views (invoked by the layout manager)
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        // create a new view
        val adapterLayout = LayoutInflater.from(parent.context)
            .inflate(R.layout.match_item, parent, false)
        return ItemViewHolder(adapterLayout)
    }

    /**
     * Replace the contents of a view (invoked by the layout manager)
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val match = dataset[position]
        holder.dateText.text = match.matchDate.substringBefore("T")
        val user = match.players.find { player ->
            (player.summonerName.lowercase() == match.playerName.lowercase()) }!!
        holder.itemView.setOnClickListener {
            System.out.println("hello")
            MatchDialogFragment(match).show(fragmentManager, "MatchDialog")
        }
        holder.championText.text = user.champName
        holder.scoreText.text = match.blueSideKills.toString() + "-" + match.redSideKills.toString()
        holder.kdaText.text = user.kills.toString() + "/" + user.deaths.toString() + "/" + user.assists.toString()
        holder.matchResultText.text = if (match.winningTeam == user.teamId) { "VICTORY" } else { "DEFEAT" }
        holder.matchResultText.setTextColor(if (match.winningTeam == user.teamId) { Color.GREEN } else { Color.RED })
    }

    /**
     * Return the size of your dataset (invoked by the layout manager)
     */
    override fun getItemCount() = dataset.size
}