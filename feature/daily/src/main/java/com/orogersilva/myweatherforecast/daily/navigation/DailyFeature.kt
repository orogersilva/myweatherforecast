package com.orogersilva.myweatherforecast.daily.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.orogersilva.myweatherforecast.daily.ui.screen.DailyWeatherForecastScreen
import com.orogersilva.myweatherforecast.dailyapi.DailyFeatureApi

class DailyFeature : DailyFeatureApi {

    private val latitudeParameterKey = "latitude"
    private val longitudeParameterKey = "longitude"
    private val startDateParameterKey = "startDate"
    private val endDateParameterKey = "endDate"

    private val baseRoute = "DailyForecast"
    private val complementRoute = "/{$latitudeParameterKey}" +
        "/{$longitudeParameterKey}" +
        "/{$startDateParameterKey}" +
        "/{$endDateParameterKey}"

    override fun dailyRoute(): String = baseRoute

    override fun registerGraph(
        navGraphBuilder: NavGraphBuilder,
        navHostController: NavHostController
    ) {
        navGraphBuilder.composable(
            route = "${baseRoute}$complementRoute",
            arguments = listOf(
                navArgument(latitudeParameterKey) { NavType.StringType },
                navArgument(longitudeParameterKey) { NavType.StringType },
                navArgument(startDateParameterKey) { NavType.StringType },
                navArgument(endDateParameterKey) { NavType.StringType }
            )
        ) { backStackEntry ->

            val arguments = requireNotNull(backStackEntry.arguments)

            val latitude = arguments.getString(latitudeParameterKey)?.toDouble()!!
            val longitude = arguments.getString(longitudeParameterKey)?.toDouble()!!
            val startDate = arguments.getString(startDateParameterKey)!!
            val endDate = arguments.getString(endDateParameterKey)!!

            DailyWeatherForecastScreen(
                viewModel = hiltViewModel(),
                latitude = latitude,
                longitude = longitude,
                startDate = startDate,
                endDate = endDate
            )
        }
    }
}
