package com.priximmo.retrofitapi

import com.priximmo.geojson.parcelle.Parcelle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ParcelleAPI {
    @Headers("Content-Type: application/json", "User-Agent: Mozilla/5.0")
    @GET("parcelle?")
    fun searchParcelle(@Query("geom=") query: String): Call<Parcelle>
}