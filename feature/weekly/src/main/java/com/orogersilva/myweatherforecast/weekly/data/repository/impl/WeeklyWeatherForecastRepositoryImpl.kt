package com.orogersilva.myweatherforecast.weekly.data.repository.impl

import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.weekly.data.repository.WeeklyWeatherForecastRepository
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class WeeklyWeatherForecastRepositoryImpl constructor(
    private val weeklyWeatherForecastRemoteDataSource: WeeklyWeatherForecastRemoteDataSource
) : WeeklyWeatherForecastRepository {

    override fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): Flow<Result<List<WeatherForecast>>> =
        flow {
            try {
                val weatherForecast = weeklyWeatherForecastRemoteDataSource.getWeeklyForecast(latitude, longitude)
                emit(Result.Success(weatherForecast))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}