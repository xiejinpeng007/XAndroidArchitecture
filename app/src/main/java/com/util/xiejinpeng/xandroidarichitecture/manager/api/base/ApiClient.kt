package com.util.xiejinpeng.xandroidarichitecture.manager.api.base

import android.annotation.SuppressLint
import com.util.xiejinpeng.xandroidarichitecture.BuildConfig
import com.util.xiejinpeng.xandroidarichitecture.manager.api.auth.ApiKeyAuth
import com.util.xiejinpeng.xandroidarichitecture.manager.api.auth.HttpBasicAuth
import com.util.xiejinpeng.xandroidarichitecture.manager.api.auth.OAuth
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.apache.oltu.oauth2.client.request.OAuthClientRequest
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.simplexml.SimpleXmlConverterFactory
import timber.log.Timber
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager
import kotlin.reflect.KClass


class ApiClient private constructor(val retrofit: Retrofit, val okHttpClient: OkHttpClient) {

    fun <S> createService(serviceClass: Class<S>): S = retrofit.create(serviceClass)

    fun <S : Any> createService(serviceClass: KClass<S>): S = createService(serviceClass.java)


    class Builder(val apiAuthorizations: MutableMap<String, Interceptor> = LinkedHashMap(),
                  val okBuilder: OkHttpClient.Builder = OkHttpClient.Builder(),
                  val adapterBuilder: Retrofit.Builder = Retrofit.Builder()) {
        /**
         * Helper method to configure the token endpoint of the first oauth found in the apiAuthorizations (there should be only one)
         * @return Token request builder
         */
        val tokenEndPoint: OAuthClientRequest.TokenRequestBuilder?
            get() {
                for (apiAuthorization in apiAuthorizations.values) {
                    if (apiAuthorization is OAuth) {
                        val oauth = apiAuthorization as OAuth
                        return oauth.getTokenRequestBuilder()
                    }
                }
                return null
            }

        /**
         * Helper method to configure authorization endpoint of the first oauth found in the apiAuthorizations (there should be only one)
         * @return Authentication request builder
         */
        val authorizationEndPoint: OAuthClientRequest.AuthenticationRequestBuilder?
            get() {
                for (apiAuthorization in apiAuthorizations.values) {
                    if (apiAuthorization is OAuth) {
                        val oauth = apiAuthorization as OAuth
                        return oauth.getAuthenticationRequestBuilder()
                    }
                }
                return null
            }

        private val allowAllSSLSocketFactory: Pair<SSLSocketFactory, X509TrustManager>?
            get() {
                try {
                    val sslContext = SSLContext.getInstance("TLS")
                    val trustManager = trustManagerAllowAllCerts
                    sslContext.init(null,
                            arrayOf<TrustManager>(trustManager),
                            SecureRandom())

                    return sslContext.socketFactory to trustManager
                } catch (e: Exception) {
                    Timber.e(e, "allowAllSSLSocketFactory has error")
                    return null
                }
            }

        /**
         * すべての証明書を受け付ける信頼マネージャを作成
         * @return 信頼マネージャ
         */
        private// すべての証明書を受け付ける信頼マネージャ
                /*
					 * 認証するピアについて信頼されている、証明書発行局の証明書の配列を返します。
					 *//*
					 * ピアから提出された一部のまたは完全な証明書チェーンを使用して、
					 * 信頼できるルートへの証明書パスを構築し、認証タイプに基づいて
					 * クライアント認証を検証できるかどうか、信頼できるかどうかを返します。
					 *//*
					 * ピアから提出された一部のまたは完全な証明書チェーンを使用して、
					 * 信頼できるルートへの証明書パスを構築し、認証タイプに基づいて
					 * サーバ認証を検証できるかどうか、また信頼できるかどうかを返します。
					 */ val trustManagerAllowAllCerts: X509TrustManager
            get() = object : X509TrustManager {
                override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkClientTrusted(
                        chain: Array<X509Certificate>, authType: String) {
                }

                @SuppressLint("TrustAllX509TrustManager")
                @Throws(CertificateException::class)
                override fun checkServerTrusted(
                        chain: Array<X509Certificate>, authType: String) {
                }
            }

        /**
         * Helper method to configure the first api key found
         * @param apiKey API key
         */
        private fun setApiKey(apiKey: String): Builder {
            for (apiAuthorization in apiAuthorizations.values) {
                if (apiAuthorization is ApiKeyAuth) {
                    val keyAuth = apiAuthorization as ApiKeyAuth
                    keyAuth.setApiKey(apiKey)
                    break
                }
            }

            return this
        }

        /**
         * Helper method to configure the username/password for basic auth or password oauth
         * @param username Username
         * @param password Password
         */
        private fun setCredentials(username: String, password: String): Builder {
            for (apiAuthorization in apiAuthorizations.values) {
                if (apiAuthorization is HttpBasicAuth) {
                    val basicAuth = apiAuthorization as HttpBasicAuth
                    basicAuth.setCredentials(username, password)
                    break
                }
                if (apiAuthorization is OAuth) {
                    val oauth = apiAuthorization as OAuth
                    oauth.getTokenRequestBuilder().setUsername(username).setPassword(password)
                    break
                }
            }
            return this
        }

        /**
         * Helper method to pre-set the oauth access token of the first oauth found in the apiAuthorizations (there should be only one)
         * @param accessToken Access token
         */
        fun setAccessToken(accessToken: String): Builder {
            for (apiAuthorization in apiAuthorizations.values) {
                if (apiAuthorization is OAuth) {
                    val oauth = apiAuthorization as OAuth
                    oauth.setAccessToken(accessToken)
                    break
                }
            }

            return this
        }

        /**
         * Helper method to configure the oauth accessCode/implicit flow parameters
         * @param clientId Client ID
         * @param clientSecret Client secret
         * @param redirectURI Redirect URI
         */
        fun configureAuthorizationFlow(clientId: String, clientSecret: String, redirectURI: String): Builder {
            for (apiAuthorization in apiAuthorizations.values) {
                if (apiAuthorization is OAuth) {
                    val oauth = apiAuthorization as OAuth
                    oauth.getTokenRequestBuilder()
                            .setClientId(clientId)
                            .setClientSecret(clientSecret)
                            .setRedirectURI(redirectURI)
                    oauth.getAuthenticationRequestBuilder()
                            .setClientId(clientId)
                            .setRedirectURI(redirectURI)
                    return this
                }
            }

            return this
        }

        /**
         * Configures a listener which is notified when a new access token is received.
         * @param accessTokenListener Access token listener
         */
        fun registerAccessTokenListener(accessTokenListener: OAuth.AccessTokenListener): Builder {
            for (apiAuthorization in apiAuthorizations.values) {
                if (apiAuthorization is OAuth) {
                    val oauth = apiAuthorization as OAuth
                    oauth.registerAccessTokenListener(accessTokenListener)
                    return this
                }
            }

            return this
        }

        /**
         * Adds an authorization to be used by the client
         * @param authName Authentication name
         * @param authorization Authorization interceptor
         */
        fun addAuthorization(authName: String, authorization: Interceptor): Builder {
            if (apiAuthorizations.containsKey(authName)) {
                throw RuntimeException("auth name \"$authName\" already in api authorizations")
            }
            apiAuthorizations.put(authName, authorization)
            okBuilder.addInterceptor(authorization)

            return this
        }

        fun setAllowAllCerTificates(): Builder {
            allowAllSSLSocketFactory?.apply {
                okBuilder.sslSocketFactory(first, second)
                okBuilder.hostnameVerifier { hostname, session -> true }
            }

            return this
        }

        fun doForOkBuilder(f: (OkHttpClient.Builder) -> Unit): Builder {
            f(okBuilder)
            return this
        }

        fun build(url: String? = null, enableMoshi: Boolean = false): ApiClient {
            val baseUrl = url ?: BuildConfig.BASE_URL
                    .let { url ->
                        if (!url.endsWith("/"))
                            "$url/"
                        else
                            url
                    }
                    .let { url ->
                        if (!url.startsWith("http"))
                            "https://$url"
                        else
                            url
                    }

            adapterBuilder
                    .baseUrl(baseUrl)
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .let {
                        if (enableMoshi)
                            it.addConverterFactory(MoshiConverterFactory.create(globalMoshi))
                        else
                            it.addConverterFactory(SimpleXmlConverterFactory.create(simpleXmlPersister))
                    }

            if (BuildConfig.ALLOW_ALL_CERTIFICATES)
                setAllowAllCerTificates()

            val client = okBuilder.build()
            val retrofit = adapterBuilder.client(client).build()
            return ApiClient(retrofit, client)
        }
    }

    companion object {
        val defaultClient: ApiClient
            get() = Builder().build()
    }
}
