package com.plvaloyi.rugbyapp.ui.profile

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.plvaloyi.rugbyapp.ui.competitions.upcomingMatchesResults
import com.plvaloyi.rugbyapp.ui.home.UsersArticles
import com.plvaloyi.rugbyapp.view.HelperFunctions
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.results.view.*
import kotlinx.android.synthetic.main.results.view.awayTeamImageResult
import kotlinx.android.synthetic.main.results.view.awayTeamResult
import kotlinx.android.synthetic.main.results.view.awayTeamScore
import kotlinx.android.synthetic.main.results.view.compNameResult
import kotlinx.android.synthetic.main.results.view.homeTeamImageResult
import kotlinx.android.synthetic.main.results.view.homeTeamResult
import kotlinx.android.synthetic.main.results.view.homeTeamScore
import kotlinx.android.synthetic.main.user_favourited_results.view.*
import kotlinx.android.synthetic.main.users_favorited_fixtures.view.*
import kotlinx.android.synthetic.main.users_newsfeed.view.*

class DashboardViewModel(val view: View) :
    RecyclerView.ViewHolder(view){

    lateinit var db: FirebaseFirestore
    val helper = HelperFunctions


        fun savedArticles(article : UsersArticles){
            val downloadedUrl = article.image
            view.usersNewsArticle.text = article.title
            view.usersNewsSource.text = article.source

            view.usersNewsArticle.setOnClickListener {
                val link = article.link
                val openUrl = Intent(Intent.ACTION_VIEW)
                openUrl.data = Uri.parse(link)

                view.context.startActivity(openUrl)
            }

            if (downloadedUrl.isEmpty()) {
                //ImageView.setImageResource(R.mipmap.ic_launcher_foreground);
            } else {
                Picasso.get().load(downloadedUrl).fit().into(view.usersNewsImage)
            }

            view.delete.setOnClickListener {
                val user = Firebase.auth.currentUser?.uid
                db = FirebaseFirestore.getInstance()
                val matchId = article.id

                var deleteMatch =
                    db.collection("users").document(user.toString()).collection("articles")
                        .document(matchId)
                deleteMatch.delete()

            }
        }


        @RequiresApi(Build.VERSION_CODES.O)
        fun savedFixtures(saved : upcomingMatchesResults){
            val homeTeamImage = saved.homeTeamImage
            val awayTeamImage = saved.awayTeamImage
            val date = saved.fixtureDate
            val time = saved.fixtureTime
            val dateFormat = helper.timeZoneCheck("$date $time")
            view.savedhomeTeam.text = saved.homeTeam
            view.savedawayTeam.text = saved.awayTeam
            view.savedtime.text = dateFormat.get("time")
            view.savedcompName.text = saved.competitionName
            view.savedfixturedate.text = dateFormat.get("date")

            if (homeTeamImage.isEmpty()) {
                // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
            } else {
                Picasso.get().load(homeTeamImage).fit().into(view.savedHomeTeamImage)
            }

            if (awayTeamImage.isEmpty()) {
                // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
            } else {
                Picasso.get().load(awayTeamImage).fit().into(view.savedAwayTeamImage)
            }

            view.delete_fixture.setOnClickListener {
                val user = Firebase.auth.currentUser?.uid
                db = FirebaseFirestore.getInstance()
                val matchId = saved.id

                var deleteMatch =
                    db.collection("users").document(user.toString()).collection("fixture")
                        .document(matchId)
                deleteMatch.delete()

            }
        }

        // displays the results of games played

        fun savedResults(matches: upcomingMatchesResults?){

            val homeTeamImage = matches?.homeTeamImage
            val awayTeamImage = matches?.awayTeamImage
            val date = matches?.fixtureDate

            view.homeTeamResult.text = matches?.homeTeam
            view.awayTeamResult.text = matches?.awayTeam
            view.homeTeamScore.text = matches?.homeScore
            view.awayTeamScore.text = matches?.awayScore
            view.compNameResult.text = matches?.competitionName

            if (homeTeamImage!!.isEmpty()) {
                // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
            } else {
                Picasso.get().load(homeTeamImage).fit()
                    .into(view.homeTeamImageResult)
            }

            if (awayTeamImage!!.isEmpty()) {
                // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
            } else {
                Picasso.get().load(awayTeamImage).fit()
                    .into(view.awayTeamImageResult)
            }

            view.delete_result.setOnClickListener {
                val user = Firebase.auth.currentUser?.uid
                db = FirebaseFirestore.getInstance()
                val matchId = matches.id

                var deleteMatch =
                    db.collection("users").document(user.toString()).collection("fixture")
                        .document(matchId)
                deleteMatch.delete()

            }
        }



    }