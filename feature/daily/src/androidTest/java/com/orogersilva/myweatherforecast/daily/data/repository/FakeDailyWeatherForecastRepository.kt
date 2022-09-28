package com.orogersilva.myweatherforecast.daily.data.repository

import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeDailyWeatherForecastRepository : DailyWeatherForecastRepository {

    private var dailyWeatherForecast: DailyWeatherForecast? = null
    private var exception: Exception? = null

    fun setDailyWeatherForecast(dailyWeatherForecast: DailyWeatherForecast) {
        this.dailyWeatherForecast = dailyWeatherForecast
    }

    fun setException(exception: Exception) {
        this.exception = exception
    }

    override fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): Flow<Result<DailyWeatherForecast>> {
        return flow {
            dailyWeatherForecast?.let { dwf ->
                emit(Result.Success(dwf))
            }

            emit(Result.Error(exception!!))
        }
    }
}
