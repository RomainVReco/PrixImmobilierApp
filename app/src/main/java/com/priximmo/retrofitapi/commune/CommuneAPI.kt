package com.priximmo.retrofitapi.commune

import com.priximmo.dataclass.commune.CommuneData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface CommuneAPI {
    @Headers(
        "Prefer: respond-async")

    @GET("communes")
    fun searchCommune(@Query("codePostal") query: String): Call<List<CommuneData>>

}