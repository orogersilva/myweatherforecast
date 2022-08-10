package com.orogersilva.myweatherforecast.daily.data.repository.impl

import com.orogersilva.myweatherforecast.daily.data.repository.DailyWeatherForecastRepository
import com.orogersilva.myweatherforecast.daily.data.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class DailyWeatherForecastRepositoryImpl @Inject constructor(
    private val dailyWeatherForecastRemoteDataSource: DailyWeatherForecastRemoteDataSource
) : DailyWeatherForecastRepository {

    override fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): Flow<Result<DailyWeatherForecast>> =
        flow {
            try {
                val dailyWeatherForecast = dailyWeatherForecastRemoteDataSource
                    .getDailyForecast(latitude, longitude, startDateStr, endDateStr)
                emit(Result.Success(dailyWeatherForecast))
            } catch (e: Exception) {
                emit(Result.Error(e))
            }
        }
}
