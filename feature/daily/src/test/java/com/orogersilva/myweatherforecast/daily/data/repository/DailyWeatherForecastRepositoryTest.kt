package com.orogersilva.myweatherforecast.daily.data.repository

import app.cash.turbine.test
import com.orogersilva.myweatherforecast.daily.data.remote.FakeDailyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.daily.data.repository.impl.DailyWeatherForecastRepositoryImpl
import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class DailyWeatherForecastRepositoryTest {

    private lateinit var fakeDailyWeatherForecastRemoteDataSource: FakeDailyWeatherForecastRemoteDataSource

    private lateinit var dailyWeatherForecastRepository: DailyWeatherForecastRepository

    @Before fun setUp() {

        fakeDailyWeatherForecastRemoteDataSource = FakeDailyWeatherForecastRemoteDataSource()

        dailyWeatherForecastRepository = DailyWeatherForecastRepositoryImpl(
            fakeDailyWeatherForecastRemoteDataSource
        )
    }

    @Test fun `Get daily forecast, when it is passed related data to user location and date time filter and there was an expected error, then return Result error`() = runTest {

        // ARRANGE

        val expectedException = Exception("404", Throwable())

        fakeDailyWeatherForecastRemoteDataSource.setException(expectedException)

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        // ACT / ASSERT

        dailyWeatherForecastRepository.getDailyForecast(
            latitude,
            longitude,
            startDateStr,
            endDateStr
        ).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get daily forecast, when it is passed related data to user location and date time filter, then return Result Success with daily weather forecast`() = runTest {

        // ARRANGE

        val expectedDailyWeatherForecast = createDailyWeatherForecast()

        fakeDailyWeatherForecastRemoteDataSource.setDailyWeatherForecast(
            expectedDailyWeatherForecast
        )

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        // ACT

        dailyWeatherForecastRepository.getDailyForecast(
            latitude,
            longitude,
            startDateStr,
            endDateStr
        ).test {
            assertEquals(
                expected = Result.Success(expectedDailyWeatherForecast),
                actual = awaitItem()
            )
            awaitComplete()
        }

        // ASSERT
    }

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
}
