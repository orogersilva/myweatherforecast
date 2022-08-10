package com.orogersilva.myweatherforecast.daily.source

import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast

interface DailyWeatherForecastRemoteDataSource {

    suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): DailyWeatherForecast
}
