package com.example.androidhttplibrarytest.retrofit

import com.example.androidhttplibrarytest.CLIENT_ID
import com.example.androidhttplibrarytest.CLIENT_SECRET
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Header
import retrofit2.http.POST

interface PapagoService {
    @FormUrlEncoded
    @POST("v1/papago/n2mt")
    fun postTranslateToEnglish(
        @Header("X-Naver-Client-Id") clientId: String = CLIENT_ID,
        @Header("X-Naver-Client-Secret") clientSecret: String = CLIENT_SECRET,
        @Field("source") source: String = "ko",
        @Field("target") target: String = "en",
        @Field("text") text: String
    ): Call<PapagoRes>
}