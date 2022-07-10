package com.orogersilva.myweatherforecast.networking.transformer

interface ErrorTransformer {

    suspend fun transform(incoming: Throwable): Throwable
}