package com.example.androidhttplibrarytest.volley

import android.content.Context
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

object VolleyInstance {
    var requestQueue: RequestQueue? = null

    fun create(context: Context) {
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context)
        }
    }
}