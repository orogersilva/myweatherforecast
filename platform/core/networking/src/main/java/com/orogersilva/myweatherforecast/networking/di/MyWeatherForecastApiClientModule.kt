package com.orogersilva.myweatherforecast.networking.di

import android.content.Context
import com.orogersilva.myweatherforecast.networking.BuildConfig
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.rest.RestClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyWeatherForecastApiClientModule {

    @Provides
    @Singleton
    fun provideMyWeatherForecastApiClient(
        @ApplicationContext context: Context
    ): MyWeatherForecastApiClient =
        RestClient.getApiClient(
            context,
            MyWeatherForecastApiClient::class.java,
            BuildConfig.BASE_API_URL
        )
}