package com.priximmo.retrofitapi.address

import com.priximmo.geojson.adresseban.AddressBAN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressAPI {
    @GET("search")
    fun searchAddress(@Query("q") query: String): Call<AddressBAN>
}