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

        binding.btnTranslate.setOnClickListener {
            val text = binding.etText.text.toString()

            when(binding.spinner.selectedItemPosition) {
                0 -> translateToEnglishWithRetrofit(text)
                1 -> translateToJapaneseWithOkHttp(text)
                2 -> translateToFrenchWithVolley(text)
            }
        }
    }

    private fun translateToEnglishWithRetrofit(text: String) {
        val tag = "call-retrofit"

        RetrofitInstance.papagoService.postTranslateToEnglish(text = text).enqueue(object : Callback<PapagoRes> {
            override fun onResponse(call: Call<PapagoRes>, response: Response<PapagoRes>) {
                if(response.isSuccessful)
                    binding.tvTarget.text = response.body()!!.message.result.translatedText
                else
                    Log.e(tag, response.raw().toString())
            }

            override fun onFailure(call: Call<PapagoRes>, t: Throwable) {
                Log.e(tag, t.message.toString())
            }
        })
    }

    private fun translateToJapaneseWithOkHttp(text: String) {

    }

    private fun translateToFrenchWithVolley(text: String) {

    }
}