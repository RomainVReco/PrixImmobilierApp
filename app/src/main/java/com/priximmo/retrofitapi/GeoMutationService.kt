package com.priximmo.retrofitapi

import com.priximmo.geojson.geomutation.Geomutation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoMutationService {
    @GET("geomutations/?")
    fun searchGeomutation(@Query("anneemut_min") anneeMutMin:String, @Query("in_bbox=") bbox:String, @Query("code_insee") codeInsee:String): Call<Geomutation>
}