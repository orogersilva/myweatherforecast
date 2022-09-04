package com.orogersilva.myweatherforecast.daily.di

import com.orogersilva.myweatherforecast.daily.data.remote.DailyWeatherForecastRemoteDataSourceImpl
import com.orogersilva.myweatherforecast.daily.data.repository.DailyWeatherForecastRepository
import com.orogersilva.myweatherforecast.daily.data.repository.impl.DailyWeatherForecastRepositoryImpl
import com.orogersilva.myweatherforecast.daily.data.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.daily.navigation.DailyFeature
import com.orogersilva.myweatherforecast.dailyapi.DailyFeatureApi
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DailyWeatherForecastModule {

    @Binds abstract fun bindDailyFeature(
        dailyFeature: DailyFeature
    ): DailyFeatureApi

    @Binds abstract fun bindDailyForecastRemoteDataSource(
        dailyWeatherForecastRemoteDataSourceImpl: DailyWeatherForecastRemoteDataSourceImpl
    ): DailyWeatherForecastRemoteDataSource

    @Binds abstract fun bindDailyForecastRepository(
        dailyWeatherForecastRepositoryImpl: DailyWeatherForecastRepositoryImpl
    ): DailyWeatherForecastRepository
}
