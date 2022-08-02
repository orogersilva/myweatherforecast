package com.orogersilva.myweatherforecast.networking.transformer

import com.orogersilva.myweatherforecast.networking.error.NetworkingError
import java.io.IOException
import java.net.ConnectException
import java.net.NoRouteToHostException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

object NetworkingErrorTransformer : ErrorTransformer {

    override suspend fun transform(incoming: Throwable): Throwable =
        when {
            (!isNetworkingError(incoming)) -> incoming
            isConnectionTimeout(incoming) -> NetworkingError.OperationTimeout
            cannotReachHost(incoming) -> NetworkingError.HostUnreachable
            else -> NetworkingError.ConnectionSpike
        }

    private fun isNetworkingError(error: Throwable): Boolean =
        isCanceledRequest(error) ||
            cannotReachHost(error) ||
            isConnectionTimeout(error)

    private fun isCanceledRequest(error: Throwable): Boolean =
        error is IOException &&
            error.message?.contentEquals("Canceled") ?: false

    private fun cannotReachHost(error: Throwable): Boolean =
        error is UnknownHostException ||
            error is ConnectException ||
            error is NoRouteToHostException

    private fun isConnectionTimeout(error: Throwable): Boolean = error is SocketTimeoutException
}
