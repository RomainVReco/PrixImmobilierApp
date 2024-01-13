package com.priximmo.retrofitapi

import com.priximmo.geojson.parcelle.Parcelle
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface ParcelleAPI {
    @GET("parcelle?")
    fun searchParcelle(@Query("geom=") query: String): Call<Parcelle>
}