package com.orogersilva.myweatherforecast.daily.data.repository

import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import kotlinx.coroutines.flow.Flow

interface DailyWeatherForecastRepository {

    fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): Flow<Result<DailyWeatherForecast>>
}
