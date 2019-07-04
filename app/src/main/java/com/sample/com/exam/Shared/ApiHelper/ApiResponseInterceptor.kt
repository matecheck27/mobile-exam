package com.sample.com.exam.Shared.ApiHelper

import android.util.Log
import okhttp3.*
import okio.Buffer

class  ApiResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val newHeader = request.headers().newBuilder().build()
        val newRequest = request.newBuilder().headers(newHeader).build()
        val response = chain.proceed(newRequest)

        return response
    }

}