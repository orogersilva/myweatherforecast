package com.orogersilva.myweatherforecast.weekly.ui.screen

import com.orogersilva.myweatherforecast.weekly.ui.BasePaparazziTest
import org.junit.Test

class WeeklyWeatherForecastSummaryScreenScreenshotTest : BasePaparazziTest() {

    @Test fun `Verify WeatherForecastMainContentPreview`() {
        paparazzi.snapshot {
            WeatherForecastMainContentPreview()
        }
    }

    @Test fun `Verify ClearSkyDayWeatherContentPreview`() {
        paparazzi.snapshot {
            ClearSkyDayWeatherContentPreview()
        }
    }

    @Test fun `Verify FogDayWeatherContentPreview`() {
        paparazzi.snapshot {
            FogDayWeatherContentPreview()
        }
    }
}
