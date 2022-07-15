package com.orogersilva.myweatherforecast

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.orogersilva.myweatherforecast.navigation.NavGraph
import com.orogersilva.myweatherforecast.ui.theme.MyWeatherForecastTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        setContent {
            MyWeatherForecastTheme {
                NavGraph()
            }
        }
    }
}