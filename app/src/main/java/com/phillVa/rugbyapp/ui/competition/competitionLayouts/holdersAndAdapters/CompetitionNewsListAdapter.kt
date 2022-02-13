package com.phillVa.rugbyapp.ui.competition.competitionLayouts.holdersAndAdapters

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.phillVa.rugbyapp.ui.home.NewsData
import com.squareup.picasso.MemoryPolicy
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.competition_newsfeed.view.*
import kotlinx.android.synthetic.main.competition_newsfeed.view.newsArticle
import kotlinx.android.synthetic.main.competition_newsfeed.view.newsImage
import kotlinx.android.synthetic.main.newsfeed.view.*


class CompetitionNewsListAdapter(var news: NewsData) :
    RecyclerView.Adapter<CompetitionNewsViewHolder>() {

    lateinit var db: FirebaseFirestore
    private val user = Firebase.auth.currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): CompetitionNewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.competition_newsfeed, parent, false)
        return CompetitionNewsViewHolder(cellForRow)

    }

    override fun getItemCount(): Int {
        return news.data.count()
    }

    override fun onBindViewHolder(holder: CompetitionNewsViewHolder, position: Int) {
        val results = news.data[position]
        val downloadedUrl = results.image_url
        holder.itemView.newsArticle.text = results.title
        holder.itemView.newsSource.text = results.source
        db = FirebaseFirestore.getInstance()

        holder.itemView.newsArticle.setOnClickListener {
            val link = results.url
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(link)

            holder.view.context.startActivity(openUrl)
        }

        if (downloadedUrl.isEmpty()) {
            //ImageView.setImageResource(R.mipmap.ic_launcher_foreground);
        } else {
            Picasso.get().load(downloadedUrl).fit().into(holder.itemView.newsImage)
        }
        holder.newsResults = results

        db.collection("users/$user/articles").get()
            .addOnSuccessListener { result ->
                for (document in result) {
                    if (document.id == results.uuid){
                        holder.view.preStar.setColorFilter(Color.GREEN)
                    }
                }
            }
            .addOnFailureListener { exception ->
                println( "Error getting documents: ")
            }

        holder.itemView.preStar.setOnClickListener {
            val favoriteButton = holder.itemView.preStar
            favoriteButton.isEnabled = false
            favoriteButton.isClickable = false
            favoriteButton.setColorFilter(Color.GREEN)
            val matchId = results.uuid
            val data = hashMapOf(
                "id" to matchId,
                "title" to results.title,
                "link" to results.url,
                "image" to results.image_url,
                "author" to results.source
            )
            var addArticle = db.collection("users").document(user.toString()).collection("articles")
                .document(matchId)
            addArticle.set(data)
        }


    }
}