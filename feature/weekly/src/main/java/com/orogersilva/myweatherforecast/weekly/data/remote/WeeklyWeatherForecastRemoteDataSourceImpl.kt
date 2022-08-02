package com.orogersilva.myweatherforecast.weekly.data.remote

import com.orogersilva.myweatherforecast.data.domain.converter.WeatherForecastConverter
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.transformer.managedExecution
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource
import javax.inject.Inject

class WeeklyWeatherForecastRemoteDataSourceImpl @Inject constructor (
    private val myWeatherForecastApiClient: MyWeatherForecastApiClient
) : WeeklyWeatherForecastRemoteDataSource {

    override suspend fun getWeeklyForecast(
        latitude: Double,
        longitude: Double
    ): List<WeatherForecast> =
        managedExecution {
            WeatherForecastConverter
                .convertWeeklyWeatherForecastDtoToWeatherForecasts(
                    myWeatherForecastApiClient.getWeeklyWeatherForecast(latitude, longitude)
                )
        }
}
