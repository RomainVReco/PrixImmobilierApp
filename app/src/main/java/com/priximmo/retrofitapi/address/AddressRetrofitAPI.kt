package com.priximmo.retrofitapi.address

import com.priximmo.servicepublicapi.RequestInterceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

object AddressRetrofitAPI {

    private const val BASE_URL = "https://api-adresse.data.gouv.fr/"

    val okHttpClient = OkHttpClient()
        .newBuilder()
        .addInterceptor(RequestInterceptor)
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    fun getClient() : Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(JacksonConverterFactory.create())
            .build()
}