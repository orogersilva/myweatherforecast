package com.orogersilva.myweatherforecast.data.domain.model

import com.orogersilva.myweatherforecast.data.enum.WeatherCode

data class WeatherForecastMinMax(
    val temperatureMinMax: Pair<Double, Double>,
    val dateStr: String,
    val weatherCode: WeatherCode
)
