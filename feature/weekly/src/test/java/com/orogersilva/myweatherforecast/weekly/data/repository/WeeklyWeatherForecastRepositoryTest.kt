package com.orogersilva.myweatherforecast.weekly.data.repository

import app.cash.turbine.test
import com.orogersilva.myweatherforecast.data.domain.Result
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import com.orogersilva.myweatherforecast.weekly.data.remote.FakeWeeklyWeatherForecastRemoteDataSource
import com.orogersilva.myweatherforecast.weekly.data.repository.impl.WeeklyWeatherForecastRepositoryImpl
import com.orogersilva.myweatherforecast.weekly.data.source.WeeklyWeatherForecastRemoteDataSource
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class WeeklyWeatherForecastRepositoryTest {

    private lateinit var fakeWeeklyWeatherForecastRemoteDataSource: FakeWeeklyWeatherForecastRemoteDataSource

    private lateinit var weeklyWeatherForecastRepository: WeeklyWeatherForecastRepository

    @Before fun setUp() {

        fakeWeeklyWeatherForecastRemoteDataSource = FakeWeeklyWeatherForecastRemoteDataSource()

        weeklyWeatherForecastRepository = WeeklyWeatherForecastRepositoryImpl(
            fakeWeeklyWeatherForecastRemoteDataSource
        )
    }

    @Test fun `Get weekly forecast, when it is passed latitude and longitude and there was an expected error, then return Result error`() = runTest {

        // ARRANGE

        val expectedException = Exception("404", Throwable())

        fakeWeeklyWeatherForecastRemoteDataSource.setException(expectedException)

        val latitude = -29.625
        val longitude = -51.125

        // ACT / ASSERT

        weeklyWeatherForecastRepository.getWeeklyForecast(latitude, longitude).test {
            assertEquals(
                expected = Result.Error(expectedException),
                actual = awaitItem()
            )
            awaitComplete()
        }
    }

    @Test fun `Get weekly forecast, when it is passed latitude and longitude, then return Result Success with weather forecasts`() = runTest {

        // ARRANGE

        val expectedWeatherForecasts = listOf(
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
            )
        )

        fakeWeeklyWeatherForecastRemoteDataSource.setWeatherForecasts(expectedWeatherForecasts)

        val latitude = -29.625
        val longitude = -51.125

        // ACT

        weeklyWeatherForecastRepository.getWeeklyForecast(latitude, longitude).test {
            assertEquals(
                expected = Result.Success(expectedWeatherForecasts),
                actual = awaitItem()
            )
            awaitComplete()
        }

        // ASSERT
    }
}