package com.example.androidhttplibrarytest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import com.android.volley.RequestQueue
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.androidhttplibrarytest.databinding.ActivityMainBinding
import com.example.androidhttplibrarytest.retrofit.PapagoRes
import com.example.androidhttplibrarytest.retrofit.RetrofitInstance
import com.example.androidhttplibrarytest.volley.VolleyInstance
import com.android.volley.Request as VolleyRequest
import okhttp3.FormBody
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException

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
        val TAG = "call-retrofit"

        RetrofitInstance.papagoService.postTranslateToEnglish(text = text).enqueue(object : Callback<PapagoRes> {
            override fun onResponse(call: Call<PapagoRes>, response: Response<PapagoRes>) {
                if(response.isSuccessful)
                    binding.tvTarget.text = response.body()!!.message.result.translatedText
                else
                    Log.e(TAG, response.raw().toString())
            }

            override fun onFailure(call: Call<PapagoRes>, t: Throwable) {
                Log.e(TAG, t.message.toString())
            }
        })
    }


    private fun translateToJapaneseWithOkHttp(text: String) {
        val TAG = "call-okHttp"

        val client = OkHttpClient()

        val formBody = FormBody.Builder()
            .add("source", "ko")
            .add("target", "ja")
            .add("text", text)
            .build()

        val request = Request.Builder()
            .header("X-Naver-Client-Id", CLIENT_ID)
            .header("X-Naver-Client-Secret", CLIENT_SECRET)
            .url(BASE_URL + "v1/papago/n2mt")
            .post(formBody)
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                if(response.isSuccessful) {
                    val jsonObject = JSONObject(response.body!!.string())
                    val translatedText = jsonObject.getJSONObject("message").getJSONObject("result").getString("translatedText")
                    binding.tvTarget.text = translatedText
                } else {
                    Log.e(TAG, response.message)
                }
            }

            override fun onFailure(call: okhttp3.Call, e: IOException) {
                Log.e(TAG, e.message.toString())
            }
        })
    }

    private fun translateToFrenchWithVolley(text: String) {
        val TAG = "call-volley"

        val request = object : StringRequest(Method.POST, BASE_URL + "v1/papago/n2mt", {
            val translatedText = JSONObject(it).getJSONObject("message").getJSONObject("result").getString("translatedText")
            binding.tvTarget.text = translatedText
        }, {
            Log.e(TAG, it.message.toString())
        }) {
            override fun getHeaders(): MutableMap<String, String> {
                return hashMapOf(
                    "X-Naver-Client-Id" to CLIENT_ID,
                    "X-Naver-Client-Secret" to CLIENT_SECRET
                )
            }

            override fun getParams(): MutableMap<String, String>? {
                return hashMapOf("source" to "ko", "target" to "fr", "text" to text)
            }
        }

        request.setShouldCache(false)
        VolleyInstance.requestQueue!!.add(request)
    }
}