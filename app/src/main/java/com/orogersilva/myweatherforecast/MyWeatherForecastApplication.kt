package com.orogersilva.myweatherforecast

import android.app.Application
import timber.log.Timber

class MyWeatherForecastApplication : Application() {

    override fun onCreate() {

        super.onCreate()

        if (BuildConfig.IS_DEBUG_BUILD) {
            Timber.plant(Timber.DebugTree())
        }
    }
}