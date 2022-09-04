package com.orogersilva.myweatherforecast.daily.di

import com.orogersilva.myweatherforecast.daily.navigation.DailyFeature
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DailyWeatherForecastFeatureModule {

    @Provides
    @Singleton
    fun provideDailyFeature() = DailyFeature()
}
