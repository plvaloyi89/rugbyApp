package com.phillVa.rugbyapp.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Parcelable
import android.provider.Settings.System.DATE_FORMAT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.whenCreated
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.GsonBuilder
import com.google.type.DateTime
import com.phillVa.rugbyapp.R
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.newsfeed.*
import okhttp3.*
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.util.*
import java.util.TimeZone.*
import java.util.concurrent.Executor

class HomeFragment : Fragment() {


   private val client = OkHttpClient()
    lateinit var newsList: RecyclerView



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val root = inflater.inflate(R.layout.fragment_home, container, false)

        fetch()

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsList = view.findViewById(R.id.mainRecycler)

         newsList.layoutManager = LinearLayoutManager(activity)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetch(){
        var differenc = LocalDate.now().minusWeeks(1)
        println(differenc)
        val key = getString(R.string.news_api_key)
        val url = "https://api.thenewsapi.com/v1/news/all?api_token=${key}&search=rugby&language=en&published_after=${differenc}&domains=bbc.co.uk,theguardian.com&sort=published_on"
        val request = Request.Builder().url(url).build()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed = gson.fromJson(body, NewsData::class.java)

                activity?.runOnUiThread{
                    newsList.adapter = NewsListAdapter(homeFeed)
                    newsList.setHasFixedSize(true)
                    newsList.setItemViewCacheSize(100)

                }
            }

            override fun onFailure(call: Call, e: IOException) {
                println("Failed to execute request")
            }
        })
    }




    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
}


