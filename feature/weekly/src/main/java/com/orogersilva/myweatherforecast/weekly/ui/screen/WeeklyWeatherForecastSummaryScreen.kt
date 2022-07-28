package com.orogersilva.myweatherforecast.weekly.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecast
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import com.orogersilva.myweatherforecast.ui.screen.LoadingSubScreen
import com.orogersilva.myweatherforecast.weekly.ui.viewmodel.WeeklyForecastSummaryViewModel
import com.orogersilva.myweatherforecast.weekly.ui.viewmodel.WeeklyForecastSummaryViewModel.WeeklyWeatherForecastSummaryViewState
import java.text.DecimalFormat
import java.time.LocalDate

@Composable
fun WeeklyWeatherForecastSummaryScreen(
    viewModel: WeeklyForecastSummaryViewModel
) {

    val uiState: WeeklyWeatherForecastSummaryViewState by viewModel.uiState.collectAsState()

    if (uiState.isLoadingWeeklyWeatherForecastSummary) {
        LoadingSubScreen()
    } else {
        WeatherForecastOperationStateContent(viewModel, uiState)
    }
}

@Composable
fun WeatherForecastOperationStateContent(
    viewModel: WeeklyForecastSummaryViewModel,
    uiState: WeeklyWeatherForecastSummaryViewState
) {

    if (uiState.isRequiredInitialWeeklyWeatherForecastSummaryLoad) {
        viewModel.loadWeeklyWeatherForecastSummary(-29.7509082, -51.2131746)
    } else {

        if (uiState.hasError) {
            Text(text = "ERRO!")
        } else {
            WeatherForecastMainContent(uiState = uiState)
        }
    }
}

@Composable
fun WeatherForecastMainContent(
    uiState: WeeklyWeatherForecastSummaryViewState
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center
    ) {
        WeeklyWeatherForecastCarousel(uiState = uiState)
    }
}

@Composable
fun WeeklyWeatherForecastCarousel(
    uiState: WeeklyWeatherForecastSummaryViewState
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.background(Color.Red)
    ) {
        items(uiState.weatherForecasts) { weatherForecast ->
            DayWeatherContent(
                dateStr = weatherForecast.dateStr,
                min = weatherForecast.temperatureMinMax.first,
                max = weatherForecast.temperatureMinMax.second
            )
        }
    }
}

@Composable
fun DayWeatherContent(dateStr: String,
                      min: Double,
                      max: Double) {

    val weatherLocalDate = LocalDate.parse(dateStr)
    val temperatureDecimalFormat = DecimalFormat("#.0")

    Column(
        modifier = Modifier
            .size(200.dp, 200.dp)
            .padding(16.dp)
            .clip(RoundedCornerShape(16.dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween) {
        Text(
            text = "${weatherLocalDate.month} ${weatherLocalDate.dayOfMonth}",
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .background(Color.Green)
                .wrapContentHeight(),
            textAlign = TextAlign.Center,
            fontSize = 24.sp,
        )
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .background(Color.Yellow)
        ) {
            Text(
                text = temperatureDecimalFormat.format(min),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
            Text(
                text = temperatureDecimalFormat.format(max),
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontSize = 24.sp
            )
        }
    }
}

@Preview
@Composable
fun WeatherForecastMainContentPreview() {

    val weeklyWeatherForecastSummaryViewState = WeeklyWeatherForecastSummaryViewState(
        weatherForecasts = mutableListOf(
            WeatherForecast(
                temperatureMinMax = Pair(12.9, 20.5),
                dateStr = "2022-07-10",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecast(
                temperatureMinMax = Pair(16.2, 23.2),
                dateStr = "2022-07-11",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecast(
                temperatureMinMax = Pair(5.5, 21.3),
                dateStr = "2022-07-12",
                weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
            ),
            WeatherForecast(
                temperatureMinMax = Pair(0.8, 12.1),
                dateStr = "2022-07-13",
                weatherCode = WeatherCode.FOG
            ),
            WeatherForecast(
                temperatureMinMax = Pair(5.2, 18.3),
                dateStr = "2022-07-14",
                weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
            ),
            WeatherForecast(
                temperatureMinMax = Pair(14.6, 18.8),
                dateStr = "2022-07-15",
                weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
            ),
            WeatherForecast(
                temperatureMinMax = Pair(4.4, 15.6),
                dateStr = "2022-07-16",
                weatherCode = WeatherCode.MODERATE_RAIN
            )
        ),
        isRequiredInitialWeeklyWeatherForecastSummaryLoad = false,
        isLoadingWeeklyWeatherForecastSummary = false,
        hasError = false
    )

    WeatherForecastMainContent(uiState = weeklyWeatherForecastSummaryViewState)
}

@Preview
@Composable
fun WeeklyWeatherForecastCarouselPreview() {

    val weeklyWeatherForecastSummaryViewState = WeeklyWeatherForecastSummaryViewState(
        weatherForecasts = mutableListOf(
            WeatherForecast(
                temperatureMinMax = Pair(12.9, 20.5),
                dateStr = "2022-07-10",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecast(
                temperatureMinMax = Pair(16.2, 23.2),
                dateStr = "2022-07-11",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecast(
                temperatureMinMax = Pair(5.5, 21.3),
                dateStr = "2022-07-12",
                weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
            ),
            WeatherForecast(
                temperatureMinMax = Pair(0.8, 12.1),
                dateStr = "2022-07-13",
                weatherCode = WeatherCode.FOG
            ),
            WeatherForecast(
                temperatureMinMax = Pair(5.2, 18.3),
                dateStr = "2022-07-14",
                weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
            ),
            WeatherForecast(
                temperatureMinMax = Pair(14.6, 18.8),
                dateStr = "2022-07-15",
                weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
            ),
            WeatherForecast(
                temperatureMinMax = Pair(4.4, 15.6),
                dateStr = "2022-07-16",
                weatherCode = WeatherCode.MODERATE_RAIN
            )
        ),
        isRequiredInitialWeeklyWeatherForecastSummaryLoad = false,
        isLoadingWeeklyWeatherForecastSummary = false,
        hasError = false
    )

    WeeklyWeatherForecastCarousel(weeklyWeatherForecastSummaryViewState)
}

@Preview
@Composable
fun DayWeatherContentPreview() {
    DayWeatherContent(
        dateStr = "2022-07-25",
        min = 13.0,
        max = 23.7
    )
}