package com.orogersilva.myweatherforecast.daily.data.remote

import com.orogersilva.myweatherforecast.daily.data.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast

class FakeDailyWeatherForecastRemoteDataSource : DailyWeatherForecastRemoteDataSource {

    private var dailyWeatherForecast: DailyWeatherForecast? = null
    private var exception: Exception? = null

    fun setDailyWeatherForecast(dailyWeatherForecast: DailyWeatherForecast) {
        this.dailyWeatherForecast = dailyWeatherForecast
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): DailyWeatherForecast = dailyWeatherForecast ?: throw exception!!
}
