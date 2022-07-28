package com.orogersilva.myweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.orogersilva.myweatherforecast.weekly.ui.screen.WeeklyWeatherForecastSummaryScreen

@Composable
fun NavGraph() {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.WeeklyForecastSummary.route
    ) {
        composable(Screen.WeeklyForecastSummary.route) {
            WeeklyWeatherForecastSummaryScreen(
                viewModel = hiltViewModel()
            )
        }
    }
}