package com.example.amazingtalkerhomework.network

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response


class AuthInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        val url = request.url.newBuilder().build()
        val newRequest: Request = request.newBuilder()
            .url(url)
            .build()
        return chain.proceed(newRequest)
    }
}