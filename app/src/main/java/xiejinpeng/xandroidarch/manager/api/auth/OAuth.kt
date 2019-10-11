package xiejinpeng.xandroidarch.manager.api.auth

import org.apache.oltu.oauth2.client.OAuthClient
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest
import org.apache.oltu.oauth2.client.request.OAuthClientRequest
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.AuthenticationRequestBuilder
import org.apache.oltu.oauth2.client.request.OAuthClientRequest.TokenRequestBuilder
import org.apache.oltu.oauth2.common.exception.OAuthProblemException
import org.apache.oltu.oauth2.common.exception.OAuthSystemException
import org.apache.oltu.oauth2.common.message.types.GrantType
import org.apache.oltu.oauth2.common.token.BasicOAuthToken

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response

import java.net.HttpURLConnection.HTTP_FORBIDDEN
import java.net.HttpURLConnection.HTTP_UNAUTHORIZED

class OAuth(client: OkHttpClient, var tokenRequestBuilder: TokenRequestBuilder?) : Interceptor {

    @Volatile
    @get:Synchronized
    @set:Synchronized
    var accessToken: String? = null
    private val oauthClient: OAuthClient
    var authenticationRequestBuilder: AuthenticationRequestBuilder? = null

    private var accessTokenListener: AccessTokenListener? = null

    interface AccessTokenListener {
        fun notify(token: BasicOAuthToken)
    }

    init {
        this.oauthClient = OAuthClient(
            OAuthOkHttpClient(
                client
            )
        )
    }

    constructor(requestBuilder: TokenRequestBuilder) : this(OkHttpClient(), requestBuilder) {}

    constructor(flow: OAuthFlow, authorizationUrl: String, tokenUrl: String, scopes: String) : this(
        OAuthClientRequest.tokenLocation(tokenUrl).setScope(scopes)
    ) {
        setFlow(flow)
        authenticationRequestBuilder = OAuthClientRequest.authorizationLocation(authorizationUrl)
    }

    fun setFlow(flow: OAuthFlow) {
        when (flow) {
            OAuthFlow.accessCode, OAuthFlow.implicit -> tokenRequestBuilder!!.setGrantType(GrantType.AUTHORIZATION_CODE)
            OAuthFlow.password -> tokenRequestBuilder!!.setGrantType(GrantType.PASSWORD)
            OAuthFlow.application -> tokenRequestBuilder!!.setGrantType(GrantType.CLIENT_CREDENTIALS)
        }
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        // If the request already have an authorization (eg. Basic auth), do nothing
        if (request.header("Authorization") != null) {
            return chain.proceed(request)
        }

        // If first time, get the token
        val oAuthRequest: OAuthClientRequest
        if (accessToken == null) {
            updateAccessToken(null)
        }

        if (accessToken != null) {
            // Build the request
            val rb = request.newBuilder()

            val requestAccessToken = accessToken
            try {
                oAuthRequest = OAuthBearerClientRequest(request.url.toString())
                    .setAccessToken(requestAccessToken)
                    .buildHeaderMessage()
            } catch (e: OAuthSystemException) {
                throw IOException(e)
            }

            for ((key, value) in oAuthRequest.headers) {
                rb.addHeader(key, value)
            }
            rb.url(oAuthRequest.locationUri)

            //Execute the request
            val response = chain.proceed(rb.build())

            // 401 most likely indicates that access token has expired.
            // Time to refresh and resend the request
            if ((response.code == HTTP_UNAUTHORIZED) or (response.code == HTTP_FORBIDDEN)) {
                if (updateAccessToken(requestAccessToken)) {
                    return intercept(chain)
                }
            }
            return response
        } else {
            return chain.proceed(chain.request())
        }
    }

    /*
     * Returns true if the access token has been updated
     */
    @Synchronized
    @Throws(IOException::class)
    fun updateAccessToken(requestAccessToken: String?): Boolean {
        if (accessToken == null || accessToken == requestAccessToken) {
            try {
                val accessTokenResponse =
                    oauthClient.accessToken(this.tokenRequestBuilder!!.buildBodyMessage())
                if (accessTokenResponse != null && accessTokenResponse.accessToken != null) {
                    accessToken = accessTokenResponse.accessToken
                    if (accessTokenListener != null) {
                        accessTokenListener!!.notify(accessTokenResponse.oAuthToken as BasicOAuthToken)
                    }
                    return accessToken == requestAccessToken
                } else {
                    return false
                }
            } catch (e: OAuthSystemException) {
                throw IOException(e)
            } catch (e: OAuthProblemException) {
                throw IOException(e)
            }

        }
        return true
    }

    fun registerAccessTokenListener(accessTokenListener: AccessTokenListener) {
        this.accessTokenListener = accessTokenListener
    }

}
