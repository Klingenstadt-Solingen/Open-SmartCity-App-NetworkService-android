package de.osca.android.networkservice.utils.testing

import de.osca.android.essentials.domain.entity.android.AppProperties
import de.osca.android.networkservice.factory.EnvelopeUnwrapFactory
import de.osca.android.networkservice.interceptor.AuthInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

object NetworkTestUtils {

    class InjectionService {
        @Inject
        lateinit var properties: AppProperties
    }

    val injectionService: InjectionService = InjectionService()

    private val okHttpClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor())
        .addInterceptor(
            AuthInterceptor(
                apiClientKey = injectionService.properties.parseInfo.key,
                apiAppId = injectionService.properties.parseInfo.appId
            )
        )
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(injectionService.properties.parseInfo.endpoint)
        .client(okHttpClient)
        .addConverterFactory(EnvelopeUnwrapFactory())
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}