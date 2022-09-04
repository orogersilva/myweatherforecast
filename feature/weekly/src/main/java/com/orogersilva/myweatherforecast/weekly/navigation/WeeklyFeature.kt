package com.orogersilva.myweatherforecast.weekly.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import com.google.android.gms.location.FusedLocationProviderClient
import com.orogersilva.myweatherforecast.dailyapi.DailyFeatureApi
import com.orogersilva.myweatherforecast.weekly.ui.screen.RequestLastLocationPermissionForWeeklyWeatherForecast
import com.orogersilva.myweatherforecast.weeklyapi.WeeklyFeatureApi
import javax.inject.Inject

class WeeklyFeature @Inject constructor (
    private val dailyFeatureApi: DailyFeatureApi,
    private val fusedLocationProviderClient: FusedLocationProviderClient
) : WeeklyFeatureApi {

    private val baseRoute = "WeeklyForecastSummary"

    override fun weeklyRoute(): String = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.composable(baseRoute) {
            RequestLastLocationPermissionForWeeklyWeatherForecast(
                viewModel = hiltViewModel(),
                fusedLocationClient = fusedLocationProviderClient,
                onNavigateToDailyForecast = { latitude, longitude, startDate, endDate ->
                    navHostController.navigate(
                        "${dailyFeatureApi.dailyRoute()}/" +
                            "$latitude/" +
                            "$longitude/" +
                            "$startDate/" +
                            endDate
                    )
                }
            )
        }
    }
}
