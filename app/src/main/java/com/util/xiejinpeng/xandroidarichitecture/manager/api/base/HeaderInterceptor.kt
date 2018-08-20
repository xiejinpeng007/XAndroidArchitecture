package com.util.xiejinpeng.xandroidarichitecture.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain?): Response? {
        if (chain == null)
            return null

        //TODO add header logic
        val request = chain.request().newBuilder()
                .build()

        return chain.proceed(request)
    }
}