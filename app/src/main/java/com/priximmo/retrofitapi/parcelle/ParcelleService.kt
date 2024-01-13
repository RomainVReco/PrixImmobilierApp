package com.priximmo.retrofitapi.parcelle

import android.util.Log
import com.priximmo.geojson.parcelle.Parcelle
import com.priximmo.retrofitapi.ParcelleAPI
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ParcelleService(geometryPoint: String) {
    private val retrofit = ParcelleRetrofitAPI.getClient()
    private val parcelleAPI = retrofit.create(ParcelleAPI::class.java)
    val geo = geometryPoint
    val Tag = "ParcelleService"


    fun launchCallParcelleAPI() {
        val call = parcelleAPI.searchParcelle(geo)
        call.enqueue(object : Callback<Parcelle> {
            override fun onResponse(call: Call<Parcelle>, response: Response<Parcelle>) {
                if (response.isSuccessful) {
                    Log.d(Tag, response.code().toString())
                    Log.d(Tag, response.body().toString())
                    val parcelleResponse = response.body()
                    if (parcelleResponse != null) {
                      Log.d(Tag, "y'a bien un contenu")
                    }
                }
            }

            override fun onFailure(call: Call<Parcelle>, t: Throwable) {
                Log.d(Tag, "onFailure")
                t.printStackTrace()
            }
        })
    }
}
