package com.orogersilva.myweatherforecast.daily.ui.screen

import android.graphics.Paint
import android.graphics.PointF
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.orogersilva.myweatherforecast.daily.ui.viewmodel.DailyWeatherForecastViewModel
import com.orogersilva.myweatherforecast.daily.ui.viewmodel.DailyWeatherForecastViewModel.DailyWeatherForecastViewState
import com.orogersilva.myweatherforecast.data.domain.model.DailyWeatherForecast
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import com.orogersilva.myweatherforecast.ui.screen.LoadingSubScreen
import com.orogersilva.myweatherforecast.ui.theme.Orange90
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.toPersistentList
import java.text.DecimalFormat

@Composable
fun DailyWeatherForecastScreen(
    viewModel: DailyWeatherForecastViewModel,
    latitude: Double,
    longitude: Double,
    startDate: String,
    endDate: String
) {
    val uiState: DailyWeatherForecastViewState by viewModel.uiState.collectAsState()

    if (uiState.isLoadingDailyWeatherForecast) {
        LoadingSubScreen()
    } else {
        DailyWeatherForecastOperationStateContent(
            viewModel = viewModel,
            uiState = uiState,
            latitude,
            longitude,
            startDate,
            endDate
        )
    }
}

@Composable
private fun DailyWeatherForecastOperationStateContent(
    viewModel: DailyWeatherForecastViewModel,
    uiState: DailyWeatherForecastViewState,
    latitude: Double,
    longitude: Double,
    startDate: String,
    endDate: String
) {
    if (uiState.isRequiredInitialDailyWeatherForecastLoad) {
        viewModel.loadDailyWeatherForecast(latitude, longitude, startDate, endDate)
    } else {
        if (uiState.hasError) {
            TODO("To build a friendly UI when there is some error in this flow.")
        } else {
            DailyWeatherForecastMainContent(
                uiState = uiState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DailyWeatherForecastMainContent(
    uiState: DailyWeatherForecastViewState
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = requireNotNull(uiState.dailyWeatherForecast?.weatherCode?.backgroundColor),
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    var weatherDescription = ""
    var screenBackgroundColor: Color = Orange90
    var topBarTextColor: Color = Color.Black

    uiState.dailyWeatherForecast?.let { dailyWeatherForecast ->
        weatherDescription = stringResource(
            id = dailyWeatherForecast.weatherCode.weatherDescriptionStrId
        )
        screenBackgroundColor = dailyWeatherForecast.weatherCode.backgroundColor
        topBarTextColor = dailyWeatherForecast.weatherCode.textColor
    }

    Scaffold(
        topBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(screenBackgroundColor)
                    .padding(
                        start = 16.dp,
                        top = 32.dp,
                        end = 16.dp,
                        bottom = 8.dp
                    )
            ) {
                Text(
                    text = weatherDescription.uppercase(),
                    color = topBarTextColor,
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(screenBackgroundColor),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                DailyTemperatureChart(
                    xValues = (1..24).toPersistentList(),
                    yValues = (1..8).map { i -> i * 5 }.toPersistentList(),
                    uiState.dailyWeatherForecast?.hourlyTemperatureList!!.toPersistentList(),
                    16.dp,
                    5,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 16.dp
                        )
                        .height(400.dp)
                )
            }
        }
    )
}

@Composable
private fun DailyTemperatureChart(
    xValues: ImmutableList<Int>,
    yValues: ImmutableList<Int>,
    temperatures: ImmutableList<Double>,
    paddingSpace: Dp,
    verticalStep: Int,
    modifier: Modifier = Modifier
) {
    val twoDigitsFormat = DecimalFormat("00")

    val controlPoints1 = mutableListOf<PointF>()
    val controlPoints2 = mutableListOf<PointF>()
    val coordinates = mutableListOf<PointF>()
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            textSize = density.run { 10.sp.toPx() }
        }
    }

    val dailyTemperatureChartAnimation = remember { Animatable(0.4f) }

    LaunchedEffect("dailyTemperatureChartAnimationKey") {
        dailyTemperatureChartAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(
                delayMillis = 10,
                durationMillis = 100,
                easing = FastOutSlowInEasing
            )
        )
    }

    Box(
        modifier = modifier
            .scale(
                scale = dailyTemperatureChartAnimation.value
            )
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .padding(
                horizontal = 8.dp,
                vertical = 16.dp
            )
    ) {
        Canvas(
            modifier = Modifier.fillMaxSize(),
            onDraw = {
                val xAxisSpace = (size.width - paddingSpace.toPx()) / xValues.size
                val yAxisSpace = size.height / yValues.size

                for (i in xValues.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        twoDigitsFormat.format(xValues[i]),
                        xAxisSpace * (i + 1),
                        size.height - 30,
                        textPaint
                    )
                }

                for (i in yValues.indices) {
                    drawContext.canvas.nativeCanvas.drawText(
                        twoDigitsFormat.format(yValues[i]),
                        paddingSpace.toPx() / 2f,
                        size.height - yAxisSpace * (i + 1),
                        textPaint
                    )
                }

                for (i in temperatures.indices) {
                    val x = xAxisSpace * xValues[i]
                    val y = size.height -
                        (yAxisSpace * temperatures[i].toFloat() / verticalStep.toFloat())

                    coordinates.add(PointF(x, y))
                }

                for (i in 1 until coordinates.size) {
                    controlPoints1.add(
                        PointF(
                            (coordinates[i].x + coordinates[i - 1].x) / 2,
                            coordinates[i - 1].y
                        )
                    )
                    controlPoints2.add(
                        PointF(
                            (coordinates[i].x + coordinates[i - 1].x) / 2,
                            coordinates[i].y
                        )
                    )
                }

                val stroke = Path().apply {
                    reset()

                    moveTo(coordinates.first().x, coordinates.first().y)

                    for (i in 0 until coordinates.size - 1) {
                        cubicTo(
                            controlPoints1[i].x,
                            controlPoints1[i].y,
                            controlPoints2[i].x,
                            controlPoints2[i].y,
                            coordinates[i + 1].x,
                            coordinates[i + 1].y
                        )
                    }
                }

                drawPath(
                    stroke,
                    color = Color.Black,
                    style = Stroke(
                        width = 5f,
                        cap = StrokeCap.Round
                    )
                )
            }
        )
    }
}

@Preview
@Composable
private fun DailyWeatherForecastMainContentPreview() {
    DailyWeatherForecastMainContent(
        DailyWeatherForecastViewState(
            dailyWeatherForecast = DailyWeatherForecast(
                weatherCode = WeatherCode.SLIGHT_RAIN,
                hourlyTimeList = listOf(
                    "2022-08-05T00:00",
                    "2022-08-05T01:00",
                    "2022-08-05T02:00",
                    "2022-08-05T03:00",
                    "2022-08-05T04:00",
                    "2022-08-05T05:00",
                    "2022-08-05T06:00",
                    "2022-08-05T07:00",
                    "2022-08-05T08:00",
                    "2022-08-05T09:00",
                    "2022-08-05T10:00",
                    "2022-08-05T11:00",
                    "2022-08-05T12:00",
                    "2022-08-05T13:00",
                    "2022-08-05T14:00",
                    "2022-08-05T15:00",
                    "2022-08-05T16:00",
                    "2022-08-05T17:00",
                    "2022-08-05T18:00",
                    "2022-08-05T19:00",
                    "2022-08-05T20:00",
                    "2022-08-05T21:00",
                    "2022-08-05T22:00",
                    "2022-08-05T23:00"
                ),
                hourlyTemperatureList = listOf(
                    13.5,
                    13.0,
                    12.3,
                    10.0,
                    9.2,
                    9.1,
                    8.9,
                    9.0,
                    8.9,
                    8.8,
                    8.7,
                    8.6,
                    9.0,
                    9.4,
                    9.7,
                    10.5,
                    10.8,
                    11.1,
                    11.4,
                    11.0,
                    10.2,
                    8.9,
                    7.9,
                    7.6
                )
            ),
            isRequiredInitialDailyWeatherForecastLoad = false,
            isLoadingDailyWeatherForecast = false,
            hasError = false
        )
    )
}

@Preview
@Composable
private fun DailyTemperatureChartPreview() {
    DailyTemperatureChart(
        xValues = (0..23).toPersistentList(),
        yValues = (1..8).map { i -> i * 5 }.toPersistentList(),
        temperatures = persistentListOf(
            13.5,
            13.0,
            12.3,
            10.0,
            9.2,
            9.1,
            8.9,
            9.0,
            8.9,
            8.8,
            8.7,
            8.6,
            9.0,
            9.4,
            9.7,
            10.5,
            10.8,
            11.1,
            11.4,
            11.0,
            10.2,
            8.9,
            7.9,
            7.6
        ),
        paddingSpace = 16.dp,
        verticalStep = 5,
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
    )
}
