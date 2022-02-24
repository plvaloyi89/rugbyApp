package com.phillVa.rugbyapp.ui.home

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
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.newsfeed.view.*

class NewsListAdapter(var news: NewsData) : RecyclerView.Adapter<NewsViewHolder>() {

    private lateinit var db: FirebaseFirestore
    private val user = Firebase.auth.currentUser?.uid

    override fun onCreateViewHolder(parent: ViewGroup, p1: Int): NewsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val cellForRow = layoutInflater.inflate(R.layout.newsfeed, parent, false)
        return NewsViewHolder(cellForRow)

    }

    override fun getItemCount(): Int = news.data.count()

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {
        val results = news.data[position]
        db = FirebaseFirestore.getInstance()
        holder.itemView.newsArticle.text = results.title
        holder.itemView.source.text = results.source
        val downloadedUrl = results.image_url


        if (downloadedUrl.isEmpty()) {
            // Rimage.setImageResource(R.mipmap.ic_placeholder_icon);
        } else {
            Picasso.get().load(downloadedUrl).fit().into(holder.itemView.newsImage)
        }
        holder.newsResults = results

         db.collection("users/$user/articles").get()
             .addOnSuccessListener { result ->
                 for (document in result) {
                     println( " ")
                     if (document.id == results.uuid){
                         holder.view.favorited.setColorFilter(Color.GREEN)
                     }
                 }
             }
             .addOnFailureListener { exception ->
                 println( "Error getting documents: ")
             }


        holder.itemView.newsArticle.setOnClickListener {
            val link = results.url
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(link)
            holder.view.context.startActivity(openUrl)
        }

        holder.itemView.newsImage.setOnClickListener {
            val link = results.url
            val openUrl = Intent(Intent.ACTION_VIEW)
            openUrl.data = Uri.parse(link)
            holder.view.context.startActivity(openUrl)
        }


        holder.itemView.favorited.setOnClickListener {
            val favoriteButton = holder.view.favorited
            favoriteButton.isEnabled = false
            favoriteButton.isClickable = false
            favoriteButton.setColorFilter(Color.GREEN)

            val matchId = results.uuid
            val data = hashMapOf(
                "id" to matchId,
                "title" to results.title,
                "link" to results.url,
                "image" to results.image_url,
                "source" to results.source
            )

            val addArticle = db.collection("users").document(user.toString()).collection("articles")
                .document(matchId)
            addArticle.set(data)

        }

    }

}