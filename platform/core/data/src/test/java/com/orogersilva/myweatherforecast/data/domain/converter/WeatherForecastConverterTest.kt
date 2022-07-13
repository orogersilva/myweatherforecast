package com.orogersilva.myweatherforecast.data.domain.converter

import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.data.dto.DailyDataDto
import com.orogersilva.myweatherforecast.data.dto.UnitDto
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import org.junit.Test
import kotlin.test.assertEquals

class WeatherForecastConverterTest {

    @Test fun `Convert weekly forecast DTO to weather forecasts correctly`() {

        // ARRANGE

        val expectedWeatherForecasts = createWeatherForecasts()

        val weeklyWeatherForecastDto = createWeeklyWeatherForecastDto()

        // ACT

        val weatherForecasts = WeatherForecastConverter
            .convertWeeklyWeatherForecastDtoToWeatherForecasts(weeklyWeatherForecastDto)

        // ASSERT

        assertEquals(expectedWeatherForecasts, weatherForecasts)
    }

    private fun createWeatherForecasts(): List<WeatherForecast> = listOf(
        WeatherForecast(
            temperatureMinMax = Pair(12.9, 20.5),
            dateStr = "2022-07-10",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecast(
            temperatureMinMax = Pair(16.2, 23.2),
            dateStr = "2022-07-11",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecast(
            temperatureMinMax = Pair(5.5, 21.3),
            dateStr = "2022-07-12",
            weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
        ),
        WeatherForecast(
            temperatureMinMax = Pair(0.8, 12.1),
            dateStr = "2022-07-13",
            weatherCode = WeatherCode.FOG
        ),
        WeatherForecast(
            temperatureMinMax = Pair(5.2, 18.3),
            dateStr = "2022-07-14",
            weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
        ),
        WeatherForecast(
            temperatureMinMax = Pair(14.6, 18.8),
            dateStr = "2022-07-15",
            weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
        ),
        WeatherForecast(
            temperatureMinMax = Pair(4.4, 15.6),
            dateStr = "2022-07-16",
            weatherCode = WeatherCode.MODERATE_RAIN
        )
    )

    private fun createWeeklyWeatherForecastDto(): WeeklyWeatherForecastDto =
        WeeklyWeatherForecastDto(
            latitude = -29.625,
            longitude = -51.125,
            elevation = 847,
            generationTimeInMs = 0.24902820587158203,
            utcOffsetSeconds = 0,
            dailyData = DailyDataDto(
                temperatureMin = listOf(
                    12.9,
                    16.2,
                    5.5,
                    0.8,
                    5.2,
                    14.6,
                    4.4
                ),
                temperatureMax = listOf(
                    20.5,
                    23.2,
                    21.3,
                    12.1,
                    18.3,
                    18.8,
                    15.6
                ),
                time = listOf(
                    "2022-07-10",
                    "2022-07-11",
                    "2022-07-12",
                    "2022-07-13",
                    "2022-07-14",
                    "2022-07-15",
                    "2022-07-16"
                ),
                weatherCode = listOf(
                    3,
                    3,
                    95,
                    45,
                    81,
                    80,
                    63
                )
            ),
            unitDto = UnitDto(
                temperatureMin = "°C",
                temperatureMax = "°C",
                time = "iso8601",
                weatherCode = "wmo code"
            )
        )
}