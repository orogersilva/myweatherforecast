package com.orogersilva.myweatherforecast.weekly.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import app.cash.paparazzi.DeviceConfig
import app.cash.paparazzi.Paparazzi
import com.android.resources.NightMode
import com.google.testing.junit.testparameterinjector.TestParameter
import com.google.testing.junit.testparameterinjector.TestParameterInjector
import com.orogersilva.myweatherforecast.ui.theme.MyWeatherForecastTheme
import org.junit.Rule
import org.junit.runner.RunWith

@RunWith(TestParameterInjector::class)
abstract class BasePaparazziTest {

    enum class BaseDeviceConfig(
        val deviceConfig: DeviceConfig
    ) {
        NEXUS_5(DeviceConfig.NEXUS_5),
        PIXEL_5(DeviceConfig.PIXEL_5),
        PIXEL_C(DeviceConfig.PIXEL_C)
    }

    @TestParameter
    lateinit var baseDeviceConfig: BaseDeviceConfig

    @TestParameter
    lateinit var nightMode: NightMode

    private val deviceConfig get() = baseDeviceConfig.deviceConfig.copy(
        nightMode = nightMode,
        softButtons = false
    )

    @get:Rule val paparazzi = Paparazzi(
        maxPercentDifference = 0.15
    )

    fun snapshot(composable: @Composable () -> Unit) {
        paparazzi.unsafeUpdateConfig(deviceConfig = deviceConfig)

        paparazzi.snapshot {
            MyWeatherForecastTheme(
                darkTheme = nightMode == NightMode.NIGHT
            ) {
                Box {
                    composable()
                }
            }
        }
    }
}
