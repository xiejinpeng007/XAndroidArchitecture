package xiejinpeng.xandroidarch.manager.api.auth

import java.io.IOException

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response

class HttpBasicAuth : Interceptor {

    var username: String? = null
    var password: String? = null

    fun setCredentials(username: String, password: String) {
        this.username = username
        this.password = password
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        // If the request already have an authorization (eg. Basic auth), do nothing
        if (request.header("Authorization") == null) {
            val credentials = Credentials.basic(username!!, password!!)
            request = request.newBuilder()
                .addHeader("Authorization", credentials)
                .build()
        }
        return chain.proceed(request)
    }
}
