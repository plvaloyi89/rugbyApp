package com.plvaloyi.rugbyapp.ui.profile

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
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
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

class Upcoming : Fragment() {

    lateinit var db: FirebaseFirestore
    private var user = Firebase.auth.currentUser?.uid
    lateinit var competitionList: RecyclerView
    lateinit var connect: FirestoreRecyclerAdapter<upcomingMatchesResults, DashboardViewModel>
    var helper = HelperFunctions
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
            .whereGreaterThanOrEqualTo("fixtureDate", currentDate)

        val options = FirestoreRecyclerOptions.Builder<upcomingMatchesResults>()
            .setQuery(query, upcomingMatchesResults::class.java).build()

        connect = object :
            FirestoreRecyclerAdapter<upcomingMatchesResults, DashboardViewModel>(options) {
            override fun onCreateViewHolder(
                parent: ViewGroup,
                viewType: Int
            ): DashboardViewModel {
                return DashboardViewModel(
                    LayoutInflater.from(parent.context)
                        .inflate(R.layout.users_favorited_fixtures, parent, false)
                )
            }

            @RequiresApi(Build.VERSION_CODES.O)
            override fun onBindViewHolder(
                holder: DashboardViewModel,
                position: Int,
                model: upcomingMatchesResults
            ) {
                holder.savedFixtures(model)
                checkTimeDate(model.fixtureTime, model.fixtureDate,model.homeTeam,model.awayTeam)
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

    @RequiresApi(Build.VERSION_CODES.O)
    fun checkTimeDate(gameTime: String, gameDate : String ,homeTeam:String,awayTeam:String){
        val currentDate = LocalDate.now().toString()
        val currentHour = LocalTime.now().hour
        val currentMinutes = LocalTime.now().minute
        val currentTime = "${currentHour}:${currentMinutes}"
        println("$currentTime and $gameTime")
        val time = LocalTime.now()
        val mytime = LocalTime.parse(gameTime)
        val calculated = Duration.between(time,mytime).toMinutes()

       if (currentDate == gameDate && calculated < 60){
            sendNotification(homeTeam,awayTeam)
        }

    }


    private  fun sendNotification(homeTeam:String,awayTeam:String){
        var channel_ID = ""
        val notification_ID = 101
        val builder : NotificationCompat.Builder = NotificationCompat.Builder(requireContext(),channel_ID)
            .setSmallIcon(R.drawable.ic_sports_rugby_black_24dp)
            .setContentTitle("Rugby Info")
            .setContentText("Upcoming Match: $homeTeam vs $awayTeam")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)

        with(NotificationManagerCompat.from(requireContext())){
            notify(notification_ID,builder.build())
        }

    }


}