package com.orogersilva.myweatherforecast.daily.remote

import com.orogersilva.myweatherforecast.daily.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.data.domain.converter.WeatherForecastConverter
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.transformer.managedExecution
import javax.inject.Inject

class DailyWeatherForecastRemoteDataSourceImpl @Inject constructor(
    private val myWeatherForecastApiClient: MyWeatherForecastApiClient
) : DailyWeatherForecastRemoteDataSource {

    override suspend fun getDailyForecast(
        latitude: Double,
        longitude: Double,
        startDateStr: String,
        endDateStr: String
    ): DailyWeatherForecast =
        managedExecution {
            WeatherForecastConverter
                .convertDailyWeatherForecastDtoToDailyWeatherForecast(
                    myWeatherForecastApiClient.getDailyWeatherForecast(
                        latitude,
                        longitude,
                        startDateStr,
                        endDateStr
                    )
                )
        }
}
