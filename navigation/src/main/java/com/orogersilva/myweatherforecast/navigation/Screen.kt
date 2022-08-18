package com.orogersilva.myweatherforecast.navigation

internal sealed class Screen(val route: String) {
    object DailyForecast : Screen("DailyForecast")
    object WeeklyForecastSummary : Screen("WeeklyForecastSummary")
}
