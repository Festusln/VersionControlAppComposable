package com.example.versioncontrolappcomposable

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class VersionControlApp: Application() {
    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "Inside onCreate")
    }

    companion object{
        const val TAG = "VersionControlApp"
    }
}
