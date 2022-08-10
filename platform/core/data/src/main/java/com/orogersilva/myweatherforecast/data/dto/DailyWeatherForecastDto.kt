package com.orogersilva.myweatherforecast.data.dto

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DailyWeatherForecastDto(
    @Json(name = "latitude") val latitude: Double,
    @Json(name = "longitude") val longitude: Double,
    @Json(name = "generationtime_ms") val generationTimeInMs: Double,
    @Json(name = "utc_offset_seconds") val utcOffsetSeconds: Int,
    @Json(name = "timezone") val timezone: String,
    @Json(name = "timezone_abbreviation") val timezoneAbbreviation: String,
    @Json(name = "elevation") val elevation: Double,
    @Json(name = "hourly_units") val hourlyUnitDto: HourlyUnitDto,
    @Json(name = "hourly") val hourlyDataDto: HourlyDataDto,
    @Json(name = "daily_units") val hourlyDailyUnitDto: HourlyDailyUnitDto,
    @Json(name = "daily") val hourlyDailyDataDto: HourlyDailyDataDto
)

@JsonClass(generateAdapter = true)
data class HourlyUnitDto(
    @Json(name = "time") val time: String,
    @Json(name = "temperature_2m") val temperatureUnit: String
)

@JsonClass(generateAdapter = true)
data class HourlyDataDto(
    @Json(name = "time") val time: List<String>,
    @Json(name = "temperature_2m") val temperature: List<Double>
)

@JsonClass(generateAdapter = true)
data class HourlyDailyUnitDto(
    @Json(name = "time") val time: String,
    @Json(name = "weathercode") val weatherCode: String
)

@JsonClass(generateAdapter = true)
data class HourlyDailyDataDto(
    @Json(name = "time") val time: List<String>,
    @Json(name = "weathercode") val weatherCode: List<Int>
)
