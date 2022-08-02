package com.orogersilva.myweatherforecast.data.domain.converter

import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.enum.WeatherCode

object WeatherForecastConverter {

    fun convertWeeklyWeatherForecastDtoToWeatherForecasts(
        weeklyWeatherForecastDto: WeeklyWeatherForecastDto
    ): List<WeatherForecast> {

        val weatherForecasts = mutableListOf<WeatherForecast>()

        val DAYS_IN_A_WEEK = 7

        for (i in 0 until DAYS_IN_A_WEEK) {

            weatherForecasts.add(
                WeatherForecast(
                    Pair(
                        weeklyWeatherForecastDto.dailyData.temperatureMin[i],
                        weeklyWeatherForecastDto.dailyData.temperatureMax[i]
                    ),
                    weeklyWeatherForecastDto.dailyData.time[i],
                    WeatherCode.valueOf(weeklyWeatherForecastDto.dailyData.weatherCode[i])
                )
            )
        }

        return weatherForecasts
    }
}
