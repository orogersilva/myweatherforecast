package com.orogersilva.myweatherforecast.networking

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitBuilder {

    operator fun invoke(url: HttpUrl, httpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(httpClient)
            .build()
}
