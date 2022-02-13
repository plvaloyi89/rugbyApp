package com.phillVa.rugbyapp.ui.competition.competitionLayouts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.phillVa.rugbyapp.R
import com.phillVa.rugbyapp.ui.competition.competitionLayouts.holdersAndAdapters.CompetitionNewsListAdapter
import com.phillVa.rugbyapp.view.SharedViewModel
import com.google.gson.GsonBuilder
import com.phillVa.rugbyapp.ui.home.NewsData
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.*
import java.io.IOException
import java.time.LocalDate


class news : Fragment() {

    lateinit var newsList: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_news, container, false)

        fetchJson()
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsList = view.findViewById(R.id.mainRecycler)
        newsList.layoutManager = LinearLayoutManager(activity)
    }

    fun fetchJson() {

        val client = OkHttpClient()
        var differenc = LocalDate.now().minusWeeks(1)

        val viewModel: SharedViewModel by activityViewModels()
        val key = getString(R.string.news_api_key)
        var name = viewModel.competition_name.value?.replace(" ", "-")

        val url = "https://api.thenewsapi.com/v1/news/all?api_token=${key}&search=${name}&language=en&published_after=${differenc}&domains=bbc.co.uk,theguardian.com&sort=published_on"


        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {

            override fun onFailure(call: Call, e: IOException) {
                println("There was a failure getting data")
            }

            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()

                val gson = GsonBuilder().create()

                val newsFeed: NewsData =
                    gson.fromJson(body, NewsData::class.java)

                activity?.runOnUiThread {

                    newsList.adapter = CompetitionNewsListAdapter(newsFeed)
                    newsList.setHasFixedSize(true)
                    newsList.setItemViewCacheSize(100)
                }
                if (!response.isSuccessful) {
                    println(response)
                } else {
                    println("Nothing returned " + response.message)
                }
            }
        })
    }

    companion object {
        fun newInstance(): news = news()

    }
}