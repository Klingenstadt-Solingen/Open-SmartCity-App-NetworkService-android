package de.osca.android.networkservice.utils

import kotlinx.coroutines.coroutineScope
import retrofit2.Response

open class RequestHandler {
    suspend fun <T> makeRequest(block: suspend () -> Response<T>): T? {
        return coroutineScope {
            try {
                block().run {
                    if (isSuccessful) {
                        body()
                    } else {
                        throw Exception()
                    }
                }
            } catch (t: Exception) {
                throw t
            }
        }
    }
}
