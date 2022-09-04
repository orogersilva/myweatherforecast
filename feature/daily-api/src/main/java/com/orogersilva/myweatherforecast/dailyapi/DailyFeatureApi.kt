package com.orogersilva.myweatherforecast.dailyapi

import com.orogersilva.myweatherforecast.featureapi.FeatureApi

interface DailyFeatureApi : FeatureApi {

    fun dailyRoute(): String
}
