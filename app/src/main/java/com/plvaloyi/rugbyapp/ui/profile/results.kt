package com.plvaloyi.rugbyapp.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.ktx.Firebase
import com.plvaloyi.rugbyapp.R
import com.plvaloyi.rugbyapp.ui.competitions.upcomingMatchesResults
import com.plvaloyi.rugbyapp.view.HelperFunctions
import com.plvaloyi.rugbyapp.view.SharedViewModel

class results : Fragment() {

    lateinit var db: FirebaseFirestore
    lateinit var competitionList: RecyclerView
    private var user = Firebase.auth.currentUser?.uid
    lateinit var connect: FirestoreRecyclerAdapter<upcomingMatchesResults, DashboardViewModel>
    var helper = HelperFunctions
    lateinit var options : FirestoreRecyclerOptions<upcomingMatchesResults>
    private val model: SharedViewModel by viewModels()



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


        var query = db.collection("users/$user/fixture").orderBy("fixtureDate", Query.Direction.DESCENDING)
            .whereLessThanOrEqualTo("fixtureDate", currentDate)

         options = FirestoreRecyclerOptions.Builder<upcomingMatchesResults>()
            .setQuery(query, upcomingMatchesResults::class.java).build()

        connect = object :
            FirestoreRecyclerAdapter<upcomingMatchesResults, DashboardViewModel>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): DashboardViewModel {
                return DashboardViewModel(
                    LayoutInflater.from(parent.context).inflate(R.layout.user_favourited_results, parent, false)
                )
            }

            override fun onBindViewHolder(
                holder: DashboardViewModel,
                position: Int,
                model: upcomingMatchesResults
            ) {
                holder.savedResults(model)
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