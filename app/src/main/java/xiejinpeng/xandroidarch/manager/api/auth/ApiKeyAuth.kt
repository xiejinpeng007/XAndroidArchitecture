package xiejinpeng.xandroidarch.manager.api.auth

import java.io.IOException
import java.net.URI
import java.net.URISyntaxException

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyAuth(val location: String, val paramName: String) : Interceptor {

    var apiKey: String? = null

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val paramValue: String
        var request = chain.request()

        if (location === "query") {
            var newQuery: String? = request.url.toUri().query
            paramValue = "$paramName=$apiKey"
            if (newQuery == null) {
                newQuery = paramValue
            } else {
                newQuery += "&$paramValue"
            }

            val newUri: URI
            try {
                newUri = URI(
                    request.url.toUri().scheme, request.url.toUri().authority,
                    request.url.toUri().path, newQuery, request.url.toUri().fragment
                )
            } catch (e: URISyntaxException) {
                throw IOException(e)
            }

            request = request.newBuilder().url(newUri.toURL()).build()
        } else if (location === "header") {
            request = request.newBuilder()
                .addHeader(paramName, apiKey!!)
                .build()
        }
        return chain.proceed(request)
    }
}