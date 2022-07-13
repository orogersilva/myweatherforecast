package com.orogersilva.myweatherforecast.networking.rest

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import com.orogersilva.myweatherforecast.data.BuildConfig
import com.orogersilva.myweatherforecast.networking.BuildConfig.IS_RELEASE_BUILD
import com.orogersilva.myweatherforecast.networking.RetrofitBuilder
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

object RestClient {

    fun <T> getApiClient(
        context: Context,
        serviceClass: Class<T>,
        baseEndpoint: String
    ): T {

        val chuckerCollector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.ONE_HOUR
        )

        val client = OkHttpClient.Builder()
            .addInterceptor(
                ChuckerInterceptor.Builder(context)
                    .collector(chuckerCollector)
                    .maxContentLength(250000L)
                    .redactHeaders(emptySet())
                    .alwaysReadResponseBody(false)
                    .build()
            )

        if (!IS_RELEASE_BUILD) {

            val loggingInterceptor = HttpLoggingInterceptor { message ->
                Timber.tag("OkHttp").d(message)
            }

            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            client.addInterceptor(loggingInterceptor)
        }

        return RetrofitBuilder(baseEndpoint.toHttpUrl(), client.build())
            .create(serviceClass)
    }
}