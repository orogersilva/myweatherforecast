package com.orogersilva.myweatherforecast.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class WeeklyWeatherForecastDto(
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "elevation") val elevation: Int,
    @Json(name = "generationtime_ms") val generationTimeInMs: Double,
    @Json(name = "utc_offset_seconds") val utcOffsetSeconds: Int,
    @Json(name = "daily") val dailyData: DailyDataDto,
    @Json(name = "daily_units") val unitDto: UnitDto
)

@JsonClass(generateAdapter = true)
data class DailyDataDto(
    @Json(name = "temperature_2m_min") val temperatureMin: List<Double>,
    @Json(name = "temperature_2m_max") val temperatureMax: List<Double>,
    @Json(name = "time") val time: List<String>,
    @Json(name = "weathercode") val weatherCode: List<Int>
)

@JsonClass(generateAdapter = true)
data class UnitDto(
    @Json(name = "temperature_2m_min") val temperatureMin: String,
    @Json(name = "temperature_2m_max") val temperatureMax: String,
    @Json(name = "time") val time: String,
    @Json(name = "weathercode") val weatherCode: String
)