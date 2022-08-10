package com.orogersilva.myweatherforecast.data.domain.converter

import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecastMinMax
import com.orogersilva.myweatherforecast.data.dto.DailyDataDto
import com.orogersilva.myweatherforecast.data.dto.DailyUnitDto
import com.orogersilva.myweatherforecast.data.dto.DailyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDailyDataDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDailyUnitDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDataDto
import com.orogersilva.myweatherforecast.data.dto.HourlyUnitDto
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import org.junit.Test
import kotlin.test.assertEquals

class WeatherForecastConverterTest {

    @Test fun `Convert weekly forecast DTO to weather forecasts correctly`() {

        // ARRANGE

        val expectedWeatherForecasts = createWeatherForecastsMinMax()

        val weeklyWeatherForecastDto = createWeeklyWeatherForecastDto()

        // ACT

        val weatherForecasts = WeatherForecastConverter
            .convertWeeklyWeatherForecastDtoToWeatherForecastsMinMax(weeklyWeatherForecastDto)

        // ASSERT

        assertEquals(expectedWeatherForecasts, weatherForecasts)
    }

    @Test fun `Convert daily weather forecast DTO to daily weather forecast successfully`() {

        // ARRANGE

        val expectedDailyWeatherForecast = createDailyWeatherForecast()
        val dailyWeatherForecastDto = createDailyWeatherForecastDto()

        // ACT

        val dailyWeatherForecast = WeatherForecastConverter
            .convertDailyWeatherForecastDtoToDailyWeatherForecast(dailyWeatherForecastDto)

        // ASSERT

        assertEquals(expectedDailyWeatherForecast, dailyWeatherForecast)
    }

    private fun createWeatherForecastsMinMax(): List<WeatherForecastMinMax> = listOf(
        WeatherForecastMinMax(
            temperatureMinMax = Pair(12.9, 20.5),
            dateStr = "2022-07-10",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(16.2, 23.2),
            dateStr = "2022-07-11",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(5.5, 21.3),
            dateStr = "2022-07-12",
            weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(0.8, 12.1),
            dateStr = "2022-07-13",
            weatherCode = WeatherCode.FOG
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(5.2, 18.3),
            dateStr = "2022-07-14",
            weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(14.6, 18.8),
            dateStr = "2022-07-15",
            weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(4.4, 15.6),
            dateStr = "2022-07-16",
            weatherCode = WeatherCode.MODERATE_RAIN
        )
    )

    private fun createWeeklyWeatherForecastDto(): WeeklyWeatherForecastDto =
        WeeklyWeatherForecastDto(
            latitude = -29.625,
            longitude = -51.125,
            elevation = 847.3,
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
            dailyUnitDto = DailyUnitDto(
                temperatureMin = "°C",
                temperatureMax = "°C",
                time = "iso8601",
                weatherCode = "wmo code"
            )
        )

    private fun createDailyWeatherForecast(): DailyWeatherForecast =
        DailyWeatherForecast(
            weatherCode = WeatherCode.SLIGHT_RAIN,
            hourlyTimeList = listOf(
                "2022-08-05T00:00",
                "2022-08-05T01:00",
                "2022-08-05T02:00",
                "2022-08-05T03:00",
                "2022-08-05T04:00",
                "2022-08-05T05:00",
                "2022-08-05T06:00",
                "2022-08-05T07:00",
                "2022-08-05T08:00",
                "2022-08-05T09:00",
                "2022-08-05T10:00",
                "2022-08-05T11:00",
                "2022-08-05T12:00",
                "2022-08-05T13:00",
                "2022-08-05T14:00",
                "2022-08-05T15:00",
                "2022-08-05T16:00",
                "2022-08-05T17:00",
                "2022-08-05T18:00",
                "2022-08-05T19:00",
                "2022-08-05T20:00",
                "2022-08-05T21:00",
                "2022-08-05T22:00",
                "2022-08-05T23:00"
            ),
            hourlyTemperatureList = listOf(
                13.5,
                13.0,
                12.3,
                10.0,
                9.2,
                9.1,
                8.9,
                9.0,
                8.9,
                8.8,
                8.7,
                8.6,
                9.0,
                9.4,
                9.7,
                10.5,
                10.8,
                11.1,
                11.4,
                11.0,
                10.2,
                8.9,
                7.9,
                7.6
            )
        )

    private fun createDailyWeatherForecastDto(): DailyWeatherForecastDto =
        DailyWeatherForecastDto(
            latitude = -29.375,
            longitude = -51.625,
            generationTimeInMs = 1.981973648071289,
            utcOffsetSeconds = 0,
            timezone = "UTC",
            timezoneAbbreviation = "UTC",
            elevation = 806.0,
            hourlyUnitDto = HourlyUnitDto(
                time = "iso8601",
                temperatureUnit = "°C"
            ),
            hourlyDataDto = HourlyDataDto(
                time = listOf(
                    "2022-08-05T00:00",
                    "2022-08-05T01:00",
                    "2022-08-05T02:00",
                    "2022-08-05T03:00",
                    "2022-08-05T04:00",
                    "2022-08-05T05:00",
                    "2022-08-05T06:00",
                    "2022-08-05T07:00",
                    "2022-08-05T08:00",
                    "2022-08-05T09:00",
                    "2022-08-05T10:00",
                    "2022-08-05T11:00",
                    "2022-08-05T12:00",
                    "2022-08-05T13:00",
                    "2022-08-05T14:00",
                    "2022-08-05T15:00",
                    "2022-08-05T16:00",
                    "2022-08-05T17:00",
                    "2022-08-05T18:00",
                    "2022-08-05T19:00",
                    "2022-08-05T20:00",
                    "2022-08-05T21:00",
                    "2022-08-05T22:00",
                    "2022-08-05T23:00"
                ),
                temperature = listOf(
                    13.5,
                    13.0,
                    12.3,
                    10.0,
                    9.2,
                    9.1,
                    8.9,
                    9.0,
                    8.9,
                    8.8,
                    8.7,
                    8.6,
                    9.0,
                    9.4,
                    9.7,
                    10.5,
                    10.8,
                    11.1,
                    11.4,
                    11.0,
                    10.2,
                    8.9,
                    7.9,
                    7.6
                )
            ),
            hourlyDailyUnitDto = HourlyDailyUnitDto(
                time = "iso8601",
                weatherCode = "wmo code"
            ),
            hourlyDailyDataDto = HourlyDailyDataDto(
                time = listOf("2022-08-05"),
                weatherCode = listOf(61)
            )
        )
}
