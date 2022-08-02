package com.orogersilva.myweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.orogersilva.myweatherforecast.navigation.NavGraph
import com.orogersilva.myweatherforecast.ui.theme.MyWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MyWeatherForecastTheme {
                NavGraph(fusedLocationClient)
            }
        }
    }
}
