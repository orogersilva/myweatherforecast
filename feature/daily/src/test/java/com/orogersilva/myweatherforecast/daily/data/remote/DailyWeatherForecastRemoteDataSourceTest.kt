package com.orogersilva.myweatherforecast.daily.data.remote

import com.orogersilva.myweatherforecast.daily.data.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.data.domain.converter.WeatherForecastConverter
import com.orogersilva.myweatherforecast.data.dto.DailyWeatherForecastDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDailyDataDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDailyUnitDto
import com.orogersilva.myweatherforecast.data.dto.HourlyDataDto
import com.orogersilva.myweatherforecast.data.dto.HourlyUnitDto
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.error.RemoteServiceIntegrationError
import com.orogersilva.myweatherforecast.testing.files.FileSystemSupport
import com.orogersilva.myweatherforecast.testing.rest.RestInfrastructureRule
import com.orogersilva.myweatherforecast.testing.rest.wireRestApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class DailyWeatherForecastRemoteDataSourceTest {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var dailyWeatherForecastRemoteDataSource: DailyWeatherForecastRemoteDataSource

    @Before fun setUp() {
        val myWeatherForecastApiClient = restInfrastructureRule.server
            .wireRestApi<MyWeatherForecastApiClient>()

        dailyWeatherForecastRemoteDataSource = DailyWeatherForecastRemoteDataSourceImpl(
            myWeatherForecastApiClient
        )
    }

    @Test fun `Get daily forecast, when there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        // ACT

        val result = kotlin.runCatching {
            dailyWeatherForecastRemoteDataSource.getDailyForecast(
                latitude,
                longitude,
                startDateStr,
                endDateStr
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get daily forecast, when there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        // ACT

        val result = kotlin.runCatching {
            dailyWeatherForecastRemoteDataSource.getDailyForecast(
                latitude,
                longitude,
                startDateStr,
                endDateStr
            )
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get daily forecast, when it is requested to get daily forecast, then return status code 200`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("200_get_daily_forecast_successfully.json")
        )

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        val expectedResponse = createDailyWeatherForecastDto()

        // ACT

        val response = dailyWeatherForecastRemoteDataSource
            .getDailyForecast(
                latitude,
                longitude,
                startDateStr,
                endDateStr
            )

        // ASSERT

        assertEquals(
            expected = WeatherForecastConverter
                .convertDailyWeatherForecastDtoToDailyWeatherForecast(
                    expectedResponse
                ),
            actual = response
        )
    }

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

    private fun unwrapCaughtError(result: Result<*>): Throwable =
        result.exceptionOrNull() ?: throw IllegalArgumentException("Not an error")
}
