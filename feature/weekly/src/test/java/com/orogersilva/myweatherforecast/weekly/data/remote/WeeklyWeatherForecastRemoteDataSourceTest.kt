package com.orogersilva.myweatherforecast.weekly.data.remote

import com.orogersilva.myweatherforecast.data.domain.converter.WeatherForecastConverter
import com.orogersilva.myweatherforecast.data.dto.DailyDataDto
import com.orogersilva.myweatherforecast.data.dto.DailyUnitDto
import com.orogersilva.myweatherforecast.data.dto.WeeklyWeatherForecastDto
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.error.RemoteServiceIntegrationError
import com.orogersilva.myweatherforecast.testing.files.FileSystemSupport
import com.orogersilva.myweatherforecast.testing.rest.RestInfrastructureRule
import com.orogersilva.myweatherforecast.testing.rest.wireRestApi
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class WeeklyWeatherForecastRemoteDataSourceTest {

    @get:Rule val restInfrastructureRule = RestInfrastructureRule()

    private lateinit var weeklyWeatherForecastRemoteDataSource: WeeklyWeatherForecastRemoteDataSource

    @Before fun setUp() {
        val myWeatherForecastApiClient = restInfrastructureRule.server
            .wireRestApi<MyWeatherForecastApiClient>()

        weeklyWeatherForecastRemoteDataSource = WeeklyWeatherForecastRemoteDataSourceImpl(
            myWeatherForecastApiClient
        )
    }

    @Test fun `Get weekly forecast, when there is some issue incoming from client, then return client system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(400)

        val latitude = -29.625
        val longitude = -51.125

        // ACT

        val result = runCatching {
            weeklyWeatherForecastRemoteDataSource.getWeeklyForecast(latitude, longitude)
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.ClientOrigin,
            actual = unwrappedError
        )
    }

    @Test fun `Get weekly forecast, when there is some issue incoming from server, then return remote system error`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(500)

        val latitude = -29.625
        val longitude = -51.125

        // ACT

        val result = runCatching {
            weeklyWeatherForecastRemoteDataSource.getWeeklyForecast(latitude, longitude)
        }

        // ASSERT

        val unwrappedError = unwrapCaughtError(result)

        assertEquals(
            expected = RemoteServiceIntegrationError.RemoteSystem,
            actual = unwrappedError
        )
    }

    @Test fun `Get weekly forecast, when it is requested to get weekly forecast, then return status code 200`() = runTest {
        // ARRANGE

        restInfrastructureRule.restScenario(
            statusCode = 200,
            response = FileSystemSupport.loadFile("200_get_weekly_forecast_successfully.json")
        )

        val latitude = -29.625
        val longitude = -51.125

        val expectedResponse = createWeeklyWeatherForecastDto()

        // ACT

        val response = weeklyWeatherForecastRemoteDataSource
            .getWeeklyForecast(latitude, longitude)

        // ASSERT

        assertEquals(
            expected = WeatherForecastConverter.convertWeeklyWeatherForecastDtoToWeatherForecastsMinMax(
                expectedResponse
            ),
            actual = response
        )
    }

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

    private fun unwrapCaughtError(result: Result<*>): Throwable =
        result.exceptionOrNull() ?: throw IllegalArgumentException("Not an error")
}
