@file:Suppress("PropertyName")

package com.plvaloyi.rugbyapp.view

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.plvaloyi.rugbyapp.ui.home.News
import com.plvaloyi.rugbyapp.ui.home.NewsData

class SharedViewModel : ViewModel() {

    val competition_name = MutableLiveData<String>()
    val newsinfo = MutableLiveData<List<NewsData>>()

    fun CompName(item: String) {
        competition_name.value = item
    }


    fun getUserMutableLiveData(): MutableLiveData<List<NewsData>> {
        return newsinfo
    }


}




