package com.orogersilva.myweatherforecast.data.domain.model

import com.orogersilva.myweatherforecast.data.enum.WeatherCode

data class DailyWeatherForecast(
    val weatherCode: WeatherCode,
    val hourlyTimeList: List<String>,
    val hourlyTemperatureList: List<Double>
)
