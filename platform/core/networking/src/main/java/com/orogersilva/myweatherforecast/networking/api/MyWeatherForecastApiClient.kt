package com.orogersilva.myweatherforecast.networking.api

import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWeatherForecastApiClient {

    @GET("forecast")
    suspend fun getWeeklyWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("daily") daily: String =
            "temperature_2m_max,temperature_2m_min,weathercode&timezone=UTC"
    ): WeeklyWeatherForecastDto
}