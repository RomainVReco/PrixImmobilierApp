package com.priximmo.retrofitapi.geomutation

import com.priximmo.geojson.geomutation.Geomutation
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GeoMutationAPI {
    @GET("geomutations/?")
    fun searchGeomutationByAnneeMin(@Query("anneemut_min") anneeMutMin:String, @Query("in_bbox") bbox:String, @Query("code_insee") codeInsee:String): Call<Geomutation>

    @GET("geomutations/")
    fun searchGeomutationForSingleYear(@Query("anneemut") anneeMin:String, @Query("in_bbox") bbox:String, @Query("code_insee") codeInsee:String): Call<Geomutation>
}