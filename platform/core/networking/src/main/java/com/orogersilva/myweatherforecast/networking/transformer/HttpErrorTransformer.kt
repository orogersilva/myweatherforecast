package com.orogersilva.myweatherforecast.networking.transformer

import com.orogersilva.myweatherforecast.networking.error.RemoteServiceIntegrationError
import retrofit2.HttpException

object HttpErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when (incoming) {
            is HttpException -> translateUsingStatusCode(incoming.code())
            else -> incoming
        }

    private fun translateUsingStatusCode(statusCode: Int): RemoteServiceIntegrationError =
        when (statusCode) {
            in 400..499 -> RemoteServiceIntegrationError.ClientOrigin
            else -> RemoteServiceIntegrationError.RemoteSystem
        }
}
