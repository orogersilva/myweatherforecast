package com.orogersilva.myweatherforecast.weeklyapi

import com.orogersilva.myweatherforecast.featureapi.FeatureApi

interface WeeklyFeatureApi : FeatureApi {

    fun weeklyRoute(): String
}
