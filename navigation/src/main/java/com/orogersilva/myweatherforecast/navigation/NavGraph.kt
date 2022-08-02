package com.orogersilva.myweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.android.gms.location.FusedLocationProviderClient
import com.orogersilva.myweatherforecast.weekly.ui.screen.RequestLastLocationPermissionForWeeklyWeatherForecast

@Composable
fun NavGraph(fusedLocationClient: FusedLocationProviderClient) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.WeeklyForecastSummary.route
    ) {
        composable(Screen.WeeklyForecastSummary.route) {
            RequestLastLocationPermissionForWeeklyWeatherForecast(
                viewModel = hiltViewModel(),
                fusedLocationClient = fusedLocationClient
            )
        }
    }
}
