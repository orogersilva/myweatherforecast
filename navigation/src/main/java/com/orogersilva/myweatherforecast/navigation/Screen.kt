package com.orogersilva.myweatherforecast.navigation

internal sealed class Screen(val route: String) {
    object WeeklyForecastSummary : Screen("WeeklyForecastSummary")
}
