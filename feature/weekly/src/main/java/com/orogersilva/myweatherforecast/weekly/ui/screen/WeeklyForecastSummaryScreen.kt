package com.orogersilva.myweatherforecast.weekly.ui.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.orogersilva.myweatherforecast.weekly.ui.viewmodel.WeeklyForecastSummaryViewModel

@Composable
fun WeeklyForecastSummaryScreen(
    weeklyForecastSummaryViewModel: WeeklyForecastSummaryViewModel
) {

}

@Composable
fun DayWeatherContent() {
    
    Column(
        modifier = Modifier
            .size(200.dp, 200.dp)) {

    }
}

@Preview
@Composable
fun DayWeatherContentPreview() {
    DayWeatherContent()
}