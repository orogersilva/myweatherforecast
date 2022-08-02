package com.orogersilva.myweatherforecast.networking.transformer

import com.orogersilva.myweatherforecast.networking.error.RemoteServiceIntegrationError
import kotlinx.serialization.SerializationException

object SerializationErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is SerializationException -> RemoteServiceIntegrationError.UnexpectedResponse
            else -> incoming
        }
}
