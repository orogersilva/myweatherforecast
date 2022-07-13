package com.orogersilva.myweatherforecast.weekly.di

import com.orogersilva.myweatherforecast.weekly.data.remote.WeeklyWeatherForecastRemoteDataSourceImpl
import com.orogersilva.myweatherforecast.weekly.data.repository.WeeklyWeatherForecastRepository
import com.orogersilva.myweatherforecast.weekly.data.repository.impl.WeeklyWeatherForecastRepositoryImpl
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class WeeklyWeatherForecastModule {

    @Binds abstract fun bindWeeklyForecastRemoteDataSource(
        weeklyWeatherForecastRemoteDataSourceImpl: WeeklyWeatherForecastRemoteDataSourceImpl
    ): WeeklyWeatherForecastRemoteDataSource

    @Binds abstract fun bindWeeklyForecastRepository(
        weeklyForecastRepositoryImpl: WeeklyWeatherForecastRepositoryImpl
    ): WeeklyWeatherForecastRepository
}