package com.orogersilva.myweatherforecast.weekly.ui.screen

import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.location.Location
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.MultiplePermissionsState
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.LocationSettingsRequest
import com.google.android.gms.location.LocationSettingsResponse
import com.google.android.gms.location.Priority
import com.google.android.gms.location.SettingsClient
import com.google.android.gms.tasks.Task
import com.orogersilva.myweatherforecast.data.domain.model.WeatherForecastMinMax
import com.orogersilva.myweatherforecast.data.enum.WeatherCode
import com.orogersilva.myweatherforecast.extension.getActivity
import com.orogersilva.myweatherforecast.system.ConnectivityManager
import com.orogersilva.myweatherforecast.ui.dialog.GenericDialog
import com.orogersilva.myweatherforecast.ui.dialog.NoConnectivityDialog
import com.orogersilva.myweatherforecast.ui.screen.LoadingSubScreen
import com.orogersilva.myweatherforecast.ui.theme.Blue40
import com.orogersilva.myweatherforecast.ui.theme.Orange90
import com.orogersilva.myweatherforecast.weekly.R
import com.orogersilva.myweatherforecast.weekly.ui.viewmodel.WeeklyForecastSummaryViewModel
import com.orogersilva.myweatherforecast.weekly.ui.viewmodel.WeeklyForecastSummaryViewModel.WeeklyWeatherForecastSummaryViewState
import java.text.DecimalFormat
import java.time.LocalDate
import kotlin.system.exitProcess

@SuppressLint("MissingPermission")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun RequestLastLocationPermissionForWeeklyWeatherForecast(
    viewModel: WeeklyForecastSummaryViewModel,
    fusedLocationClient: FusedLocationProviderClient,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    var areGrantedLocationPermissions by remember { mutableStateOf(false) }

    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION
        )
    ) { areGrantedPermissions ->

        val isGrantedCoarseLocation =
            areGrantedPermissions[android.Manifest.permission.ACCESS_COARSE_LOCATION]
        val isGrantedFineLocation =
            areGrantedPermissions[android.Manifest.permission.ACCESS_FINE_LOCATION]

        areGrantedLocationPermissions = isGrantedCoarseLocation != null &&
            isGrantedFineLocation != null &&
            isGrantedCoarseLocation &&
            isGrantedFineLocation
    }

    val context = LocalContext.current

    var shouldShowCheckLocationSetting by remember { mutableStateOf(false) }
    var shouldShowWeeklyWeatherForecastSummaryScreen by remember { mutableStateOf(false) }
    var isRevokedLocationSetting by remember { mutableStateOf(false) }
    var isAvailableConnectivityState by remember {
        mutableStateOf(ConnectivityManager.isAvailableInternet(context))
    }

    val locationRequest = LocationRequest.create().apply {
        interval = 10000
        fastestInterval = 500
        priority = Priority.PRIORITY_HIGH_ACCURACY
    }

    val locationSettingResultRequest = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->
        if (activityResult.resultCode == RESULT_OK) {
            fusedLocationClient.requestLocationUpdates(
                locationRequest,
                object : LocationCallback() {
                    override fun onLocationResult(locationResult: LocationResult) {
                        shouldShowWeeklyWeatherForecastSummaryScreen = true
                    }
                },
                null
            )
        } else {
            isRevokedLocationSetting = true
        }
    }

    if (!isAvailableConnectivityState) {
        NoConnectivityDialog(
            dismissLabel = stringResource(id = R.string.weekly_no_internet_dialog_dismiss_label),
            onDismiss = {
                context.getActivity()?.finishAffinity()
                exitProcess(0)
            },
            confirmLabel = stringResource(id = R.string.weekly_no_internet_dialog_confirm_label),
            onConfirm = {
                isAvailableConnectivityState = ConnectivityManager.isAvailableInternet(context)
            }
        )
    } else if (shouldShowWeeklyWeatherForecastSummaryScreen) {
        WeeklyWeatherForecastSummaryScreen(
            viewModel = viewModel,
            fusedLocationClient = fusedLocationClient,
            onNavigateToDailyForecast = onNavigateToDailyForecast
        )
    } else if (isRevokedLocationSetting) {
        RevokedLocationSettingAlert {
            isRevokedLocationSetting = false
        }
    } else if (shouldShowCheckLocationSetting) {
        CheckLocationSetting(
            context = context,
            locationRequest = locationRequest,
            onDisabled = { intentSenderRequest ->
                locationSettingResultRequest.launch(intentSenderRequest)
            },
            onEnabled = {
                shouldShowWeeklyWeatherForecastSummaryScreen = true
            }
        )
    } else {
        HandleLocationPermission(
            locationPermissionsState = locationPermissionsState,
            areGrantedLocationPermissions = areGrantedLocationPermissions,
            deniedContent = { shouldShowRationale ->
                DeniedLocationPermissionContent(
                    shouldShowRationale = shouldShowRationale,
                    onRequestPermission = {
                        locationPermissionsState.launchMultiplePermissionRequest()
                    }
                )
            },
            grantedContent = {
                shouldShowCheckLocationSetting = true
            }
        )
    }
}

@Composable
private fun DeniedLocationPermissionContent(
    shouldShowRationale: Boolean,
    onRequestPermission: () -> Unit
) {
    val locationPermissionDescription = if (shouldShowRationale) {
        stringResource(
            id = R.string.weekly_location_permission_dialog_rationale_description
        )
    } else {
        stringResource(
            id = R.string.weekly_location_permission_dialog_description
        )
    }

    AlertDialog(
        onDismissRequest = { },
        title = {
            Text(
                text = stringResource(
                    id = R.string.weekly_location_permission_dialog_title
                )
            )
        },
        text = {
            Text(text = locationPermissionDescription)
        },
        confirmButton = {
            TextButton(
                onClick = { onRequestPermission() }
            ) {
                Text(
                    text = stringResource(
                        id = R.string.weekly_location_permission_dialog_confirm_button_label
                    )
                )
            }
        }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun HandleLocationPermission(
    locationPermissionsState: MultiplePermissionsState,
    areGrantedLocationPermissions: Boolean,
    deniedContent: @Composable (Boolean) -> Unit,
    grantedContent: @Composable () -> Unit
) {
    if (!areGrantedLocationPermissions && !locationPermissionsState.shouldShowRationale) {
        SideEffect {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    } else if (locationPermissionsState.shouldShowRationale) {
        deniedContent(locationPermissionsState.shouldShowRationale)
    } else {
        grantedContent()
    }
}

@Composable
private fun WeeklyWeatherForecastSummaryScreen(
    viewModel: WeeklyForecastSummaryViewModel,
    fusedLocationClient: FusedLocationProviderClient,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    val systemUiController = rememberSystemUiController()
    val useDarkIcons = !isSystemInDarkTheme()

    DisposableEffect(systemUiController, useDarkIcons) {
        systemUiController.setSystemBarsColor(
            color = Orange90,
            darkIcons = useDarkIcons
        )

        onDispose {}
    }

    val uiState: WeeklyWeatherForecastSummaryViewState by viewModel.uiState.collectAsState()

    if (uiState.isLoadingWeeklyWeatherForecastSummary) {
        LoadingSubScreen()
    } else {
        WeatherForecastOperationStateContent(
            viewModel,
            uiState,
            fusedLocationClient,
            onNavigateToDailyForecast
        )
    }
}

@SuppressLint("MissingPermission")
@Composable
private fun WeatherForecastOperationStateContent(
    viewModel: WeeklyForecastSummaryViewModel,
    uiState: WeeklyWeatherForecastSummaryViewState,
    fusedLocationClient: FusedLocationProviderClient,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    if (uiState.isRequiredInitialWeeklyWeatherForecastSummaryLoad) {
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    viewModel.loadWeeklyWeatherForecastSummary(
                        location.latitude,
                        location.longitude
                    )
                } else {
                    // TODO("To handle this flow when location is null.")
                }
            }
            .addOnFailureListener {
                TODO("To handle this exception in the future.")
            }
    } else {
        if (uiState.hasError) {
            TODO("To build a friendly UI when there is some error in this flow.")
        } else {
            WeatherForecastMainContent(
                viewModel = viewModel,
                uiState = uiState,
                fusedLocationClient = fusedLocationClient,
                onNavigateToDailyForecast = onNavigateToDailyForecast
            )
        }
    }
}

@Composable
private fun CheckLocationSetting(
    context: Context,
    locationRequest: LocationRequest,
    onDisabled: (IntentSenderRequest) -> Unit,
    onEnabled: () -> Unit
) {
    val client: SettingsClient = LocationServices.getSettingsClient(context)
    val builder: LocationSettingsRequest.Builder = LocationSettingsRequest
        .Builder()
        .addLocationRequest(locationRequest)

    val gpsSettingTask: Task<LocationSettingsResponse> =
        client.checkLocationSettings(builder.build())

    gpsSettingTask.addOnSuccessListener { onEnabled() }
    gpsSettingTask.addOnFailureListener { exception ->
        if (exception is ResolvableApiException) {
            try {
                val intentSenderRequest = IntentSenderRequest
                    .Builder(exception.resolution)
                    .build()
                onDisabled(intentSenderRequest)
            } catch (sendEx: IntentSender.SendIntentException) {
            }
        }
    }
}

@Composable
private fun RevokedLocationSettingAlert(
    onRetryLocationSetting: () -> Unit
) {
    val context = LocalContext.current

    GenericDialog(
        title = stringResource(id = R.string.weekly_revoked_location_setting_dialog_title),
        description = stringResource(
            id = R.string.weekly_revoked_location_setting_dialog_description
        ),
        dismissLabel = stringResource(
            id = R.string.weekly_revoked_location_setting_dialog_dismiss_option
        ),
        onDismiss = {
            context.getActivity()?.finishAffinity()
            exitProcess(0)
        },
        confirmLabel = stringResource(
            id = R.string.weekly_revoked_location_setting_dialog_confirm_option
        ),
        onConfirm = { onRetryLocationSetting.invoke() }
    )
}

@SuppressLint("MissingPermission")
@Composable
private fun WeatherForecastMainContent(
    viewModel: WeeklyForecastSummaryViewModel,
    uiState: WeeklyWeatherForecastSummaryViewState,
    fusedLocationClient: FusedLocationProviderClient,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    SwipeRefresh(
        state = rememberSwipeRefreshState(
            isRefreshing = uiState.isLoadingWeeklyWeatherForecastSummary
        ),
        onRefresh = {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location: Location? ->
                    if (location != null) {
                        viewModel.loadWeeklyWeatherForecastSummary(
                            location.latitude,
                            location.longitude
                        )
                    } else {
                        // TODO("To handle this flow when location is null.")
                    }
                }
                .addOnFailureListener {
                    TODO("To handle this exception in the future.")
                }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Orange90),
            verticalArrangement = Arrangement.Center
        ) {
            WeeklyWeatherForecastCarousel(
                uiState = uiState,
                onNavigateToDailyForecast = onNavigateToDailyForecast
            )
        }
    }
}

@Composable
private fun WeeklyWeatherForecastCarousel(
    uiState: WeeklyWeatherForecastSummaryViewState,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    LazyRow(
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 24.dp)
    ) {
        items(uiState.weatherForecasts) { weatherForecast ->
            DayWeatherContent(
                dateStr = weatherForecast.dateStr,
                min = weatherForecast.temperatureMinMax.first,
                max = weatherForecast.temperatureMinMax.second,
                backgroundColor = weatherForecast.weatherCode.backgroundColor,
                textColor = weatherForecast.weatherCode.textColor,
                latitude = uiState.lastLatitude,
                longitude = uiState.lastLongitude,
                onNavigateToDailyForecast = onNavigateToDailyForecast
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DayWeatherContent(
    dateStr: String,
    min: Double,
    max: Double,
    backgroundColor: Color,
    textColor: Color,
    latitude: Double,
    longitude: Double,
    onNavigateToDailyForecast: (Double, Double, String, String) -> Unit
) {
    val weatherLocalDate = LocalDate.parse(dateStr)
    val temperatureDecimalFormat = DecimalFormat("0.0")

    Card(
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier
            .size(200.dp, 200.dp)
            .padding(16.dp)
            .clickable {
                onNavigateToDailyForecast.invoke(latitude, longitude, dateStr, dateStr)
            },
        elevation = CardDefaults
            .cardElevation(10.dp),
        colors = CardDefaults
            .cardColors(
                containerColor = backgroundColor
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "${weatherLocalDate.month} ${weatherLocalDate.dayOfMonth}",
                color = textColor,
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                textAlign = TextAlign.Center,
                fontSize = 18.sp
            )
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = temperatureDecimalFormat.format(min),
                    color = textColor,
                    modifier = Modifier
                        .fillMaxSize()
                        .weight(1f)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    fontSize = 24.sp
                )
                Text(
                    text = temperatureDecimalFormat.format(max),
                    color = textColor,
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
}

@Preview
@Composable
private fun RevokedLocationSettingAlertPreview() {
    RevokedLocationSettingAlert(
        onRetryLocationSetting = { }
    )
}

/*@Preview
@Composable
private fun WeatherForecastMainContentPreview() {

    val weatherForecasts = mutableListOf(
        WeatherForecastMinMax(
            temperatureMinMax = Pair(12.9, 20.5),
            dateStr = "2022-07-10",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(16.2, 23.2),
            dateStr = "2022-07-11",
            weatherCode = WeatherCode.OVERCAST
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(5.5, 21.3),
            dateStr = "2022-07-12",
            weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(0.8, 12.1),
            dateStr = "2022-07-13",
            weatherCode = WeatherCode.FOG
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(5.2, 18.3),
            dateStr = "2022-07-14",
            weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(14.6, 18.8),
            dateStr = "2022-07-15",
            weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
        ),
        WeatherForecastMinMax(
            temperatureMinMax = Pair(4.4, 15.6),
            dateStr = "2022-07-16",
            weatherCode = WeatherCode.MODERATE_RAIN
        )
    )

    val weeklyWeatherForecastSummaryViewState = WeeklyWeatherForecastSummaryViewState(
        weatherForecasts = weatherForecasts,
        isRequiredInitialWeeklyWeatherForecastSummaryLoad = false,
        isLoadingWeeklyWeatherForecastSummary = false,
        hasError = false
    )

    WeatherForecastMainContent(
        viewModel = WeeklyForecastSummaryViewModel(
            object : WeeklyWeatherForecastRepository {
                override fun getWeeklyForecast(
                    latitude: Double,
                    longitude: Double
                ): Flow<Result<List<WeatherForecastMinMax>>> =
                    flowOf(Result.Success(weatherForecasts))
            }
        ),
        uiState = weeklyWeatherForecastSummaryViewState,
        latitude = -29.7509082,
        longitude = -51.2131746,
        onNavigateToDailyForecast = { _, _, _, _ -> }
    )
}*/

@Preview
@Composable
private fun WeeklyWeatherForecastCarouselPreview() {
    val weeklyWeatherForecastMinMaxSummaryViewState = WeeklyWeatherForecastSummaryViewState(
        weatherForecasts = mutableListOf(
            WeatherForecastMinMax(
                temperatureMinMax = Pair(12.9, 20.5),
                dateStr = "2022-09-10",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(16.2, 23.2),
                dateStr = "2022-09-11",
                weatherCode = WeatherCode.OVERCAST
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(5.5, 21.3),
                dateStr = "2022-09-12",
                weatherCode = WeatherCode.SLIGHT_OR_MODERATE_THUNDERSTORM
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(0.8, 12.1),
                dateStr = "2022-09-13",
                weatherCode = WeatherCode.FOG
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(5.2, 18.3),
                dateStr = "2022-09-14",
                weatherCode = WeatherCode.MODERATE_RAIN_SHOWERS
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(14.6, 18.8),
                dateStr = "2022-09-15",
                weatherCode = WeatherCode.SLIGHT_RAIN_SHOWERS
            ),
            WeatherForecastMinMax(
                temperatureMinMax = Pair(4.4, 15.6),
                dateStr = "2022-09-16",
                weatherCode = WeatherCode.MODERATE_RAIN
            )
        ),
        isRequiredInitialWeeklyWeatherForecastSummaryLoad = false,
        isLoadingWeeklyWeatherForecastSummary = false,
        lastLatitude = -29.7509082,
        lastLongitude = -51.2131746,
        hasError = false
    )

    WeeklyWeatherForecastCarousel(
        weeklyWeatherForecastMinMaxSummaryViewState,
        onNavigateToDailyForecast = { _, _, _, _ -> }
    )
}

@Preview
@Composable
private fun DayWeatherContentPreview() {
    DayWeatherContent(
        dateStr = "2022-09-25",
        min = 0.9,
        max = 23.7,
        backgroundColor = Blue40,
        textColor = Color.White,
        latitude = -29.7509082,
        longitude = -51.2131746,
        onNavigateToDailyForecast = { _, _, _, _ -> }
    )
}
