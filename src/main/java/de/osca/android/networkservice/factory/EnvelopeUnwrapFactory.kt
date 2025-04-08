package de.osca.android.networkservice.factory

import com.google.gson.Gson
import de.osca.android.essentials.utils.annotations.UnwrappedResponse
import de.osca.android.networkservice.utils.ResponseBodyConverter
import de.osca.android.networkservice.utils.ResponseEnvelope
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class EnvelopeUnwrapFactory : Converter.Factory() {
    private val gson = Gson()
    private val gsonConverterFactory: GsonConverterFactory = GsonConverterFactory.create(gson)

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val isUnwrappedResponse = annotations
            .map { it.annotationClass }
            .contains(UnwrappedResponse::class)

        return when (isUnwrappedResponse) {
            true -> gsonConverterFactory.responseBodyConverter(type, annotations, retrofit)
            false -> getWrappedResponseConverter(type, annotations, retrofit)
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<out Annotation>,
        methodAnnotations: Array<out Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        return gsonConverterFactory.requestBodyConverter(
            type,
            parameterAnnotations,
            methodAnnotations,
            retrofit
        )
    }

    private fun getWrappedResponseConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): ResponseBodyConverter<*> {
        val wrappedType = object : ParameterizedType {
            override fun getActualTypeArguments(): Array<Type> = arrayOf(type)
            override fun getOwnerType(): Type? = null
            override fun getRawType(): Type = ResponseEnvelope::class.java
        }

        val gsonConverter =
            gsonConverterFactory.responseBodyConverter(wrappedType, annotations, retrofit)


        return ResponseBodyConverter(gsonConverter as Converter<ResponseBody, ResponseEnvelope<Any>>)
    }
}