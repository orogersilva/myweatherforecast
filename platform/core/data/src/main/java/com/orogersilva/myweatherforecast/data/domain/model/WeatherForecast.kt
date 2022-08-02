package com.orogersilva.myweatherforecast.data.domain.model

import com.orogersilva.myweatherforecast.data.enum.WeatherCode

data class WeatherForecast(
    val temperatureMinMax: Pair<Double, Double>,
    val dateStr: String,
    val weatherCode: WeatherCode
)
