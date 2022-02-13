package com.phillVa.rugbyapp.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    val competition_name = MutableLiveData<String>()
    val selectedItem: LiveData<String> get() = competition_name

    fun CompName(item: String) {
        competition_name.value = item
    }


}




