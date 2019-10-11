package xiejinpeng.xandroidarch.manager.api.base

import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //TODO add header logic
        val request = chain.request().newBuilder()
            .build()

        return chain.proceed(request)
    }

}