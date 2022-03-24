package com.plvaloyi.rugbyapp.ui.competition.competitionLayouts


import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.plvaloyi.rugbyapp.R
import com.plvaloyi.rugbyapp.ui.competition.competitionLayouts.holdersAndAdapters.StandingsViewHolder
import com.plvaloyi.rugbyapp.ui.competitions.standingsTeam
import com.plvaloyi.rugbyapp.view.SharedViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query


class standings : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var competitionList: RecyclerView
    lateinit var connect: FirestoreRecyclerAdapter<standingsTeam, StandingsViewHolder>
    private val viewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_standings, container, false)


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var competitionName = viewModel.competition_name.value

        competitionList = view.findViewById(R.id.table_recycler_view)
        db = FirebaseFirestore.getInstance()

        var query = db.collection("competitions/$competitionName/teams").orderBy("point", Query.Direction.DESCENDING)


        val options = FirestoreRecyclerOptions.Builder<standingsTeam>()
            .setQuery(query, standingsTeam::class.java)
            .build()

        connect =
            object : FirestoreRecyclerAdapter<standingsTeam, StandingsViewHolder>(options) {
                override fun onCreateViewHolder(
                    parent: ViewGroup,
                    viewType: Int
                ): StandingsViewHolder {
                    return StandingsViewHolder(
                        LayoutInflater.from(parent.context)
                            .inflate(R.layout.teamstanding, parent, false)
                    )
                }

                override fun onBindViewHolder(
                    holder: StandingsViewHolder,
                    position: Int,
                    model: standingsTeam
                ) {
                    holder.bind(model)
                }


            }


        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = false
        competitionList.setHasFixedSize(true)
        competitionList.layoutManager = layoutManager
        competitionList.itemAnimator = null
        competitionList.adapter = connect


    }


    override fun onStart() {
        super.onStart()
        connect.startListening()
    }

    override fun onStop() {
        super.onStop()
        connect.stopListening()
    }


    companion object {
        fun newInstance() = standings()
    }


}