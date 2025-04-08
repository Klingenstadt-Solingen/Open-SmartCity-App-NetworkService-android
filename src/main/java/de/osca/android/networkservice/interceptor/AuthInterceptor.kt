package de.osca.android.networkservice.interceptor

import de.osca.android.essentials.utils.constants.CONTENT_TYPE_JSON
import de.osca.android.essentials.utils.constants.PARAM_CONTENT_TYPE
import okhttp3.Interceptor
import okhttp3.Response

class AuthInterceptor(
    val apiClientKey: String,
    val apiAppId: String
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder().apply {
            addHeader(PARAM_CONTENT_TYPE, CONTENT_TYPE_JSON)
            addHeader(HEADER_APPLICATION_ID, apiAppId)
            addHeader(HEADER_CLIENT_KEY, apiClientKey)
            addHeader(HEADER_SESSION_TOKEN, sessionToken)
        }

        val response = chain.proceed(requestBuilder.build())

        return response
    }

    companion object {
        const val HEADER_APPLICATION_ID = "X-Parse-Application-Id"
        const val HEADER_CLIENT_KEY = "X-Parse-Client-Key"
        const val HEADER_SESSION_TOKEN = "X-Parse-Session-Token"

        var sessionToken: String = ""
    }
}