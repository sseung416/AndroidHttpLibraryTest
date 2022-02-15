package com.example.androidhttplibrarytest

import android.app.Application
import com.example.androidhttplibrarytest.volley.VolleyInstance

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        VolleyInstance.create(applicationContext)
    }
}