package com.example.inferno_bot.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkClient {
    private const val BASE_URL = "https://generativelanguage.googleapis.com/"


    private val okHttpClient = OkHttpClient.Builder()
        .build()

    val geminiApi: GeminiApi = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(GeminiApi::class.java)

}
