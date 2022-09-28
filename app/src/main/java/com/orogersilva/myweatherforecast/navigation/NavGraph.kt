package com.orogersilva.myweatherforecast.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.orogersilva.myweatherforecast.dailyapi.DailyFeatureApi
import com.orogersilva.myweatherforecast.featureapi.register
import com.orogersilva.myweatherforecast.weeklyapi.WeeklyFeatureApi

@Composable
fun NavGraph(
    dailyFeatureApi: DailyFeatureApi,
    weeklyFeatureApi: WeeklyFeatureApi
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = weeklyFeatureApi.weeklyRoute()
    ) {
        register(
            dailyFeatureApi,
            navController
        )
        register(
            weeklyFeatureApi,
            navController
        )
    }
}
