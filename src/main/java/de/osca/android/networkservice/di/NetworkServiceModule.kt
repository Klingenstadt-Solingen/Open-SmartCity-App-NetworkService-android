package de.osca.android.networkservice.di

import android.content.Context
import co.infinum.retromock.Retromock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import de.osca.android.essentials.domain.entity.android.AppProperties
import de.osca.android.essentials.utils.testing.ResourceBodyFactory
import de.osca.android.networkservice.factory.EnvelopeUnwrapFactory
import de.osca.android.networkservice.interceptor.AuthInterceptor
import de.osca.android.networkservice.utils.RequestHandler
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkServiceModule {
    @Singleton
    @Provides
    fun providesHttpLoggingInterceptor(): HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        properties: AppProperties,
    ): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .addInterceptor(
                AuthInterceptor(
                    apiClientKey = properties.parseInfo.key,
                    apiAppId = properties.parseInfo.appId,
                ),
            )
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        properties: AppProperties,
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(EnvelopeUnwrapFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(properties.parseInfo.endpoint)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideRequestHandler(): RequestHandler = RequestHandler()

    @Singleton
    @Provides
    fun provideRetroMock(
        retrofit: Retrofit,
        @ApplicationContext context: Context,
    ): Retromock {
        return Retromock.Builder()
            .retrofit(retrofit)
            .defaultBodyFactory(ResourceBodyFactory(context))
            .defaultBehavior { 50 } // Response delay in millis
            .build()
    }
}
