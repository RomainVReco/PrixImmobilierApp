package com.priximmo.retrofitapi

import com.priximmo.dataclass.addressBAN.AddressResponse
import com.priximmo.geojson.adresseban.AddressBAN
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface AddressService {
    @GET("search")
    fun searchAddress(@Query("q") query: String): Call<AddressBAN>
}