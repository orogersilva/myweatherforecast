package com.orogersilva.myweatherforecast.data.domain.converter

import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecastMinMax
import com.orogersilva.myweatherforecast.data.dto.DailyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.enum.WeatherCode

object WeatherForecastConverter {

    fun convertWeeklyWeatherForecastDtoToWeatherForecastsMinMax(
        weeklyWeatherForecastDto: WeeklyWeatherForecastDto
    ): List<WeatherForecastMinMax> {
        val weatherForecastsMinMax = mutableListOf<WeatherForecastMinMax>()

        val DAYS_IN_A_WEEK = 7

        for (i in 0 until DAYS_IN_A_WEEK) {
            weatherForecastsMinMax.add(
                WeatherForecastMinMax(
                    Pair(
                        weeklyWeatherForecastDto.dailyData.temperatureMin[i],
                        weeklyWeatherForecastDto.dailyData.temperatureMax[i]
                    ),
                    weeklyWeatherForecastDto.dailyData.time[i],
                    WeatherCode.valueOf(weeklyWeatherForecastDto.dailyData.weatherCode[i])
                )
            )
        }

        return weatherForecastsMinMax
    }

    fun convertDailyWeatherForecastDtoToDailyWeatherForecast(
        dailyWeatherForecastDto: DailyWeatherForecastDto
    ): DailyWeatherForecast =
        DailyWeatherForecast(
            weatherCode = WeatherCode.valueOf(
                dailyWeatherForecastDto.hourlyDailyDataDto.weatherCode.first()
            ),
            hourlyTimeList = dailyWeatherForecastDto.hourlyDataDto.time,
            hourlyTemperatureList = dailyWeatherForecastDto.hourlyDataDto.temperature
        )
}
