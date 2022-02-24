package com.phillVa.rugbyapp.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.firestore.FirebaseFirestore
import com.phillVa.rugbyapp.R
import com.phillVa.rugbyapp.ui.competitions.upcomingMatchesResults
import com.phillVa.rugbyapp.ui.upcoming.UpcomingMatchesViewModel
import com.phillVa.rugbyapp.view.HelperFunctions
import kotlinx.android.synthetic.main.newsfeed.view.*

class results : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var competitionList: RecyclerView
    lateinit var connect: FirestoreRecyclerAdapter<upcomingMatchesResults, UpcomingMatchesViewModel>
    var helper = HelperFunctions
    lateinit var options : FirestoreRecyclerOptions<upcomingMatchesResults>


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
        var currentDate = helper.currentDate()


        competitionList = view.findViewById(R.id.mainRecycler)
        db = FirebaseFirestore.getInstance()

        var query = db.collectionGroup("fixtures")

         options = FirestoreRecyclerOptions.Builder<upcomingMatchesResults>()
            .setQuery(query, upcomingMatchesResults::class.java).build()

        connect = object :
            FirestoreRecyclerAdapter<upcomingMatchesResults, UpcomingMatchesViewModel>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): UpcomingMatchesViewModel {
                return UpcomingMatchesViewModel(
                    LayoutInflater.from(parent.context).inflate(R.layout.results, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: UpcomingMatchesViewModel,
                position: Int,
                model: upcomingMatchesResults
            ) {
                holder.results(model)
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

    override fun onPause() {
        super.onPause()
        connect.updateOptions(options)
    }

    override fun onStop() {
        super.onStop()
        connect.updateOptions(options)
    }

}