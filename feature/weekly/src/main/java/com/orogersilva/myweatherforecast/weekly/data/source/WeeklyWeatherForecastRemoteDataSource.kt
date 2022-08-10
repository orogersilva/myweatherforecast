package com.orogersilva.myweatherforecast.weekly.data.source

import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecastMinMax

interface WeeklyWeatherForecastRemoteDataSource {

    suspend fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): List<WeatherForecastMinMax>
}
