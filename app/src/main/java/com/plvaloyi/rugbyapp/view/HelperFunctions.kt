package com.phillVa.rugbyapp.view

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.provider.Settings.Secure.getString
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContentProviderCompat.requireContext
import com.phillVa.rugbyapp.R
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.*
import java.time.format.DateTimeFormatter
import java.util.*

object HelperFunctions {

    private val client = OkHttpClient()

    //converts the fixtureTime to devices default time zone
    // Credit for this function goes to mkyong , https://mkyong.com/java/java-convert-date-and-time-between-timezone/
    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun timeZoneCheck(dateTime : String): Map<String, String> {
        val dateFormat = "yyyy-MM-dd HH:mm"
        val outputFormat: DateFormat = SimpleDateFormat("dd MMM yyyy", Locale.US)
        val inputFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        //val dateTime = "2022-01-30 14:00"
        //get device timezone
        val userstimeZone = TimeZone.getDefault().toZoneId()
       // println("Zone ID : ${userstimeZone}")

        //fixture time from firebase which is in EST
        val localdateTime = LocalDateTime.parse(dateTime, DateTimeFormatter.ofPattern(dateFormat))
        val fixtureTimeZone = ZoneId.of("America/New_York")
        val fixtureRegionTime = localdateTime.atZone(fixtureTimeZone)

        //time zone to be converted to
        val fixtureActualTime = fixtureRegionTime.withZoneSameInstant(userstimeZone)

        val dateTimeStyle = SimpleDateFormat("yyyy-MM-dd HH:mm")
        val datestyle = SimpleDateFormat("yyyy-MM-dd")
        val timestyle = SimpleDateFormat("HH:mm")

        // formats the new time zone to custom format
        val customformat = DateTimeFormatter.ofPattern(dateFormat)
        val extractTime = customformat.format(fixtureActualTime)
        val splitDateTime = dateTimeStyle.parse(extractTime)

        val inputText = datestyle.format(splitDateTime)
        val date = inputFormat.parse(inputText)

        val outPutDate = outputFormat.format(date)
        val outPutTime = timestyle.format(splitDateTime)

        val displayDateTime = mapOf("date" to outPutDate, "time" to outPutTime)

//        println("Date Time Formatter")
//        println("Time zone after conversion ${fixtureActualTime}")
//        println(extractTime)
//        println(timestyle.format(splitDateTime))

        return displayDateTime


    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun currentDate(): String {

        val currentDay = LocalDate.now().toString()

        return currentDay
    }





}