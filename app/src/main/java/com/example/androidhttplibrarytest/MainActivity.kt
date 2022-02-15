package com.example.androidhttplibrarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.example.androidhttplibrarytest.databinding.ActivityMainBinding
import com.example.androidhttplibrarytest.retrofit.PapagoRes
import com.example.androidhttplibrarytest.retrofit.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.spinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, resources.getStringArray(R.array.spinner_target))
    }
}