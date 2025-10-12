package com.example.juegoproyecto.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

object RetrofitClient {
    //Retrofit la requiere aunque usemos URLs completas con @Url
    private const val BASE_URL = "https://docs.google.com/"
    private val client = OkHttpClient.Builder().build()
    val api: ApiService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        //Permite recibir texto plano (para Google Sheets)
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}


