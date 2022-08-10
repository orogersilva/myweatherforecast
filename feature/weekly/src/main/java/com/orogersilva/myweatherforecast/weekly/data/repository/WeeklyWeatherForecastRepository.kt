package com.orogersilva.myweatherforecast.weekly.data.repository

import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecastMinMax
import kotlinx.coroutines.flow.Flow

interface WeeklyWeatherForecastRepository {

    fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): Flow<Result<List<WeatherForecastMinMax>>>
}
