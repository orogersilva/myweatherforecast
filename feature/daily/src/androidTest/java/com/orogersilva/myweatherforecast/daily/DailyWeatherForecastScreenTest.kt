package com.orogersilva.myweatherforecast.daily

import android.content.Context
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.hilt.navigation.compose.hiltViewModel
import com.orogersilva.myweatherforecast.DailyWeatherForecastTestActivity
import com.orogersilva.myweatherforecast.daily.data.repository.FakeDailyWeatherForecastRepository
import com.orogersilva.myweatherforecast.daily.ui.screen.DailyWeatherForecastScreen
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DailyWeatherForecastScreenTest {

    @get:Rule(order = 0)
    val hiltTestRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<DailyWeatherForecastTestActivity>()

    private val context: Context
        get() = composeTestRule.activity

    @Inject
    lateinit var fakeDailyWeatherForecastRepository: FakeDailyWeatherForecastRepository

    @Before fun setUp() {
        hiltTestRule.inject()
    }

    @Test fun dailyWeatherForecastScreen_whenItIsDetailedWeatherForecastRequestedIsOk_thenShowDailyWeatherForecast() {
        // ARRANGE

        val dailyWeatherForecast = createDailyWeatherForecast()

        fakeDailyWeatherForecastRepository.setDailyWeatherForecast(dailyWeatherForecast)

        val latitude = -29.625
        val longitude = -51.125
        val startDateStr = "2022-08-05"
        val endDateStr = "2022-08-05"

        composeTestRule.setContent {
            DailyWeatherForecastScreen(
                viewModel = hiltViewModel(),
                latitude = latitude,
                longitude = longitude,
                startDate = startDateStr,
                endDate = endDateStr
            )
        }

        // ASSERT

        composeTestRule.onNodeWithText(
            context.getString(WeatherCode.SLIGHT_RAIN.weatherDescriptionStrId).uppercase()
        ).assertIsDisplayed()
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
