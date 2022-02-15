package com.example.androidhttplibrarytest.retrofit

import com.example.androidhttplibrarytest.BASE_URL
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private val gson = GsonBuilder().setLenient().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val papagoService: PapagoService = retrofit.create(PapagoService::class.java)
}