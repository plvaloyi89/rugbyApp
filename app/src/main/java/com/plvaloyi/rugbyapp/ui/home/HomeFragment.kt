package com.plvaloyi.rugbyapp.ui.home

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.beust.klaxon.Klaxon
import com.google.gson.GsonBuilder
import com.plvaloyi.rugbyapp.R
import com.plvaloyi.rugbyapp.view.SharedViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.*
import java.io.IOException
import java.time.*

class HomeFragment : Fragment() {


    private val client = OkHttpClient()
    lateinit var newsList: RecyclerView
    private val viewmodel: SharedViewModel by activityViewModels()

    @SuppressLint("UseRequireInsteadOfGet")
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

//    @RequiresApi(Build.VERSION_CODES.O)
//    private fun fetch(sUrl: String): NewsData? {
//        var blogInfo: NewsData? = null
//        lifecycleScope.launch(Dispatchers.IO) {
//            val result = testThis(sUrl)
//            if(result != null){
//                try {
//                    // Parse result string JSON to data class
//                    blogInfo = Klaxon().parse<NewsData>(result)
//
//                    withContext(Dispatchers.Main) {
//                        //newsList.adapter = NewsListAdapter(blogInfo!!)
//                    }
//                }
//                catch(err:Error) {
//                    print("Error when parsing JSON: "+err.localizedMessage)
//                }
//            }
//            else {
//                print("Error: Get request returned no response")
//            }
//        }
//        return blogInfo
//    }




    
//  @RequiresApi(Build.VERSION_CODES.O)
//    fun testThis(url : String){
//      try  {
//          val request =  Request.Builder()
//              .url(url)
//              .build()
//                val response = client.newCall(request).execute()
//                val result = response.body?.string();
//                println(result)
//            } catch (e : IOException){
//                e.printStackTrace();
//            }
//
//
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetch(){
        var differenc = LocalDate.now().minusWeeks(1)
        println(differenc)
        val key = getString(R.string.news_api_key)
        val url = "https://api.thenewsapi.com/v1/news/all?api_token=${key}&search=rugby&language=en&published_after=${differenc}&sort=published_on"
        val request = Request.Builder().url(url).build()


        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body?.string()
                println(body)

                val gson = GsonBuilder().create()

                val homeFeed : NewsData = gson.fromJson(body, NewsData::class.java)

                activity?.runOnUiThread{
                    newsList.adapter = NewsListAdapter(homeFeed)
                    newsList.setHasFixedSize(true)

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

//private fun <T> Klaxon.parse(result: Unit): NewsData? {
//    return null
//}


