package com.orogersilva.myweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.android.gms.location.FusedLocationProviderClient
import com.orogersilva.myweatherforecast.daily.ui.screen.DailyWeatherForecastScreen
import com.orogersilva.myweatherforecast.weekly.ui.screen.RequestLastLocationPermissionForWeeklyWeatherForecast

@Composable
fun NavGraph(fusedLocationClient: FusedLocationProviderClient) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.WeeklyForecastSummary.route
    ) {
        composable(
            route = Screen.DailyForecast.route + "/{latitude}/{longitude}/{startDate}/{endDate}",
            arguments = listOf(
                navArgument("latitude") { NavType.StringType },
                navArgument("longitude") { NavType.StringType },
                navArgument("startDate") { NavType.StringType },
                navArgument("endDate") { NavType.StringType }
            )
        ) { backStackEntry ->

            val latitude = backStackEntry.arguments?.getString("latitude")?.toDouble()!!
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDouble()!!
            val startDate = backStackEntry.arguments?.getString("startDate")!!
            val endDate = backStackEntry.arguments?.getString("endDate")!!

            DailyWeatherForecastScreen(
                viewModel = hiltViewModel(),
                latitude = latitude,
                longitude = longitude,
                startDate = startDate,
                endDate = endDate
            )
        }

        composable(Screen.WeeklyForecastSummary.route) {
            RequestLastLocationPermissionForWeeklyWeatherForecast(
                viewModel = hiltViewModel(),
                fusedLocationClient = fusedLocationClient,
                onNavigateToDailyForecast = { latitude, longitude, startDate, endDate ->
                    navController.navigate(
                        "${Screen.DailyForecast.route}/" +
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
