package com.plvaloyi.rugbyapp.ui.competition.competitionLayouts.holdersAndAdapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.plvaloyi.rugbyapp.ui.competitions.standingsTeam
import kotlinx.android.synthetic.main.teamstanding.view.*

class StandingsViewHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {


    fun bind(team: standingsTeam) {
        itemView.teamName.text = team.name
        itemView.played.text = team.played
        itemView.won.text = team.won
        itemView.draw.text = team.Draw
        itemView.lost.text = team.Lost
        itemView.point.text = team.point.toString()


    }

}