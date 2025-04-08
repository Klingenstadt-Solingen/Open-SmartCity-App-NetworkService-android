package de.osca.android.networkservice.utils

import okhttp3.ResponseBody
import retrofit2.Converter

class ResponseBodyConverter<T>(
    private val converter: Converter<ResponseBody, ResponseEnvelope<T>>
) : Converter<ResponseBody, T> {

    override fun convert(responseBody: ResponseBody): T? {
        val response = converter.convert(responseBody)
        return response?.data
    }
}