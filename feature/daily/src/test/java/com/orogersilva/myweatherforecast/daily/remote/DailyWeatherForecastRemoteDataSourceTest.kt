package com.orogersilva.myweatherforecast.daily.remote

import com.orogersilva.myweatherforecast.daily.source.DailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.networking.api.MyWeatherForecastApiClient
import com.orogersilva.myweatherforecast.networking.error.RemoteServiceIntegrationError
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

    private fun unwrapCaughtError(result: Result<*>): Throwable =
        result.exceptionOrNull() ?: throw IllegalArgumentException("Not an error")
}
