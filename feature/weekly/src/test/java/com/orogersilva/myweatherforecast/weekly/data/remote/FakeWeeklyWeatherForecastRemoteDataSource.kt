package com.orogersilva.myweatherforecast.weekly.data.remote

import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource

class FakeWeeklyWeatherForecastRemoteDataSource : WeeklyWeatherForecastRemoteDataSource {

    private var weatherForecasts: List<WeatherForecast>? = null
    private var exception: Exception? = null

    fun setWeatherForecasts(weatherForecasts: List<WeatherForecast>) {
        this.weatherForecasts = weatherForecasts
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): List<WeatherForecast> = weatherForecasts ?: throw exception!!
}