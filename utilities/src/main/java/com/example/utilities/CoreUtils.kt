package com.example.utilities

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi

import java.time.Instant
import java.time.Duration

object CoreUtils {

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun getTimeDifferenceInDays(givenTime:String):Long {

        val givenDate = Instant.parse(givenTime)
        val currentDate = Instant.now()

        val duration = Duration.between(givenDate, currentDate)

        val days = duration.toDays()
//        val hours = duration.toHours() % 24
//        val minutes = duration.toMinutes() % 60

        return days

    }
}