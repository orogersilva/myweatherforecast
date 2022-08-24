package com.orogersilva.myweatherforecast.networking.api

import com.orogersilva.myweatherforecast.data.dto.DailyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MyWeatherForecastApiClient {

    @GET("forecast?daily=temperature_2m_max,temperature_2m_min,weathercode&timezone=auto")
    suspend fun getWeeklyWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double
    ): WeeklyWeatherForecastDto

    @GET("forecast?hourly=temperature_2m&daily=weathercode&timezone=auto")
    suspend fun getDailyWeatherForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("start_date") startDateStr: String,
        @Query("end_date") endDateStr: String
    ): DailyWeatherForecastDto
}
