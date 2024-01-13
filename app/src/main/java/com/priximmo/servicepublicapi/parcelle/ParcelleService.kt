package com.priximmo.servicepublicapi.parcelle

import com.fasterxml.jackson.databind.ObjectMapper
import com.priximmo.geojson.parcelle.Parcelle
import com.priximmo.retrofitapi.ParcelleAPI
import com.priximmo.servicepublicapi.ErrorResponse
import okhttp3.ResponseBody


class ParcelleService(geometryPoint: String) {
    private val retrofit = ParcelleRetrofitAPI.getClient()
    private val parcelleAPI = retrofit.create(ParcelleAPI::class.java)
    val geo = geometryPoint


    fun successfulParcelleResponse() {

        val parcelleResponse = parcelleAPI.searchParcelle(geo).execute()

        val successful = parcelleResponse.isSuccessful
        val httpStatusCode = parcelleResponse.code()
        val httpStatusMessage = parcelleResponse.message()

        val body: Parcelle? = parcelleResponse.body()

        println("Successful: $successful")
        println("HTTP status code: $successful")
        println("HTTP status message: $successful")
        println("Body: $body")

    }

    fun errorUsersResponse() {
            val parcelleResponse = parcelleAPI.searchParcelle(geo).execute()
            val errorBody: ResponseBody? = parcelleResponse.errorBody()

            val mapper = ObjectMapper()

            val mappedBody: ErrorResponse? = errorBody?.let { notNullErrorBody ->
                mapper.readValue(notNullErrorBody.toString(), ErrorResponse::class.java)
            }
        }

    fun headersParcelleReponse() {
        val parcelleResponse = parcelleAPI.searchParcelle(geo).execute()
        val headers = parcelleResponse.headers()
        val customHeaderValue = headers["custom-header"]
    }
}
