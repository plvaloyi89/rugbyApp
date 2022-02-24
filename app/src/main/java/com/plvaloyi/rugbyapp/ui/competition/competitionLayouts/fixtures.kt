package com.phillVa.rugbyapp.ui.competition.competitionLayouts

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.phillVa.rugbyapp.ui.competitions.upcomingMatchesResults
import com.phillVa.rugbyapp.ui.upcoming.UpcomingMatchesViewModel
import com.phillVa.rugbyapp.view.SharedViewModel
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fixtures.view.*
import java.time.LocalDate
import java.util.*


class fixtures : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var competitionList: RecyclerView
    private val viewModel: SharedViewModel by activityViewModels()
    lateinit var connect: FirestoreRecyclerAdapter<upcomingMatchesResults, UpcomingMatchesViewModel>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fixtures, container, false)
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var competitionName = viewModel.competition_name.value
        //Log.d(ContentValues.TAG, "here $competitionName")
        var currentDate = LocalDate.now().toString()

        competitionList = view.findViewById(R.id.mainRecycler)
        db = FirebaseFirestore.getInstance()

        var query = db.collection("competitions/$competitionName/fixtures").orderBy("fixtureDate").whereGreaterThan("fixtureDate", currentDate)


        val options = FirestoreRecyclerOptions.Builder<upcomingMatchesResults>()
            .setQuery(query, upcomingMatchesResults::class.java).build()

        connect = object :
            FirestoreRecyclerAdapter<upcomingMatchesResults, UpcomingMatchesViewModel>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): UpcomingMatchesViewModel {
                return UpcomingMatchesViewModel(
                    LayoutInflater.from(parent.context).inflate(R.layout.fixtures, parent, false)
                )
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onBindViewHolder(
                holder: UpcomingMatchesViewModel,
                position: Int,
                model: upcomingMatchesResults
            ) {
                holder.fixtures(model)
            }

        }

        val layoutManager = LinearLayoutManager(context)
        layoutManager.reverseLayout = false
        competitionList.setHasFixedSize(true)
        competitionList.layoutManager = layoutManager
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

}