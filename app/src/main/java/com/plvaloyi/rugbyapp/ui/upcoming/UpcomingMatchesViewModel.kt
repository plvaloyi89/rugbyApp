package com.plvaloyi.rugbyapp.ui.upcoming

import android.graphics.Color
import android.os.Build
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.plvaloyi.rugbyapp.R
import com.plvaloyi.rugbyapp.ui.competitions.upcomingMatchesResults
import com.plvaloyi.rugbyapp.view.HelperFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fixtures.view.*
import kotlinx.android.synthetic.main.fixtures.view.favorited
import kotlinx.android.synthetic.main.results.view.*

class UpcomingMatchesViewModel(val view: View) :
    RecyclerView.ViewHolder(view){

    var helper = HelperFunctions
    lateinit var db: FirebaseFirestore
    private val user = Firebase.auth.currentUser?.uid

        // displays the results of games played
        @RequiresApi(Build.VERSION_CODES.O)
        fun results(matches: upcomingMatchesResults?){

            val homeTeamImage = matches?.homeTeamImage
            val awayTeamImage = matches?.awayTeamImage
            val date = matches?.fixtureDate
            val time = matches?.fixtureTime
            val formatDateTime = helper.timeZoneCheck("$date $time")

            view.homeTeamResult.text = matches?.homeTeam
            view.awayTeamResult.text = matches?.awayTeam
            view.homeTeamScore.text = matches?.homeScore
            view.awayTeamScore.text = matches?.awayScore
            view.compNameResult.text = matches?.competitionName
            view.fixturedateResult.text = formatDateTime.get("date")

            if (homeTeamImage!!.isEmpty()) {
                Picasso.get().load(R.mipmap.ic_launcher_foreground).fit()
                    .into(view.homeTeamImageResult)
            } else {
                Picasso.get().load(homeTeamImage).fit()
                    .into(view.homeTeamImageResult)
            }

            if (awayTeamImage!!.isEmpty()) {
                Picasso.get().load(R.mipmap.ic_launcher_foreground).fit()
                    .into(view.homeTeamImageResult)
            } else {
                Picasso.get().load(awayTeamImage).fit()
                    .into(view.awayTeamImageResult)
            }
        }

        // displays upcoming fixtures and favoriting them
        @RequiresApi(Build.VERSION_CODES.O)
        fun fixtures(fixture: upcomingMatchesResults?){
            db = FirebaseFirestore.getInstance()
            val homeTeamImage = fixture?.homeTeamImage
            val awayTeamImage = fixture?.awayTeamImage
            val date = fixture?.fixtureDate
            val time = fixture?.fixtureTime
            val dateFormat = helper.timeZoneCheck("$date $time")

            view.homeTeam.text = fixture?.homeTeam
            view.awayTeam.text = fixture?.awayTeam
            view.time.text = dateFormat.get("time")
            view.compName.text = fixture?.competitionName
            view.fixturedate.text = dateFormat.get("date")

            db.collection("users/$user/fixture").get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        if (document.id == fixture?.id){
                            view.favorited.setColorFilter(Color.GREEN)
                        }
                    }
                }
                .addOnFailureListener { exception ->
                    println( "Error getting documents: ")
                }


            if (homeTeamImage!!.isEmpty()) {
                 Picasso.get().load(R.mipmap.ic_launcher_foreground).fit().into(view.homeTeamImage)
            } else {
                Picasso.get().load(homeTeamImage).fit().into(view.homeTeamImage)
            }

            if (awayTeamImage!!.isEmpty()) {
                Picasso.get().load(R.mipmap.ic_launcher_foreground).fit().into(view.awayTeamImage)
            } else {
                Picasso.get().load(awayTeamImage).fit().into(view.awayTeamImage)
            }

            val favoriteButton = view.favorited

            favoriteButton.setOnClickListener {
                favoriteButton.isEnabled = false
                favoriteButton.isClickable = false
                favoriteButton.setColorFilter(Color.GREEN)
                val matchId =fixture.id
                val data = hashMapOf(
                    "id" to matchId,
                    "homeTeam" to fixture.homeTeam,
                    "awayTeam" to fixture.awayTeam,
                    "homeTeamImage" to fixture.homeTeamImage,
                    "awayTeamImage" to fixture.awayTeamImage,
                    "homeScore" to fixture.homeScore,
                    "awayScore" to fixture.awayScore,
                    "fixtureTime" to fixture.fixtureTime,
                    "competitionName" to fixture.competitionName,
                    "fixtureDate" to fixture.fixtureDate
                )

                var addMatch =
                    db.collection("users").document(user.toString()).collection("fixture")
                        .document(matchId)
                addMatch.set(data)

            }

        }


    }



