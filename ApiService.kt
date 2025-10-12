package com.example.juegoproyecto.network

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

interface ApiService {
    @GET
    suspend fun obtenerSheetRaw(@Url url: String): Response<String>
}

