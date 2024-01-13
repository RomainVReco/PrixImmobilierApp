package com.priximmo.servicepublicapi

import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response

object RequestInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val maxRetries = 3
        var request = chain.request()
        var response = chain.proceed(request)
        var retryCount = 0
        Log.d("RequestInterceptor", "Outgoing request to ${request.url()}")
        while (!response.isSuccessful && retryCount < maxRetries) {
            println("RequestInterceptor : retryCount.toString()")
            retryCount++
            request = request.newBuilder().build()
            response = chain.proceed(request)
        }
        return response
    }
}