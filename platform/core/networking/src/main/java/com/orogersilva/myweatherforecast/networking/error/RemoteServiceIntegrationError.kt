package com.orogersilva.myweatherforecast.networking.error

sealed class RemoteServiceIntegrationError : Throwable() {

    object ClientOrigin : RemoteServiceIntegrationError()
    object RemoteSystem : RemoteServiceIntegrationError()
    object UnexpectedResponse : RemoteServiceIntegrationError()

    override fun toString(): String =
        when (this) {
            ClientOrigin -> "Issue incoming from client"
            RemoteSystem -> "Issue incoming from server"
            UnexpectedResponse -> "Broken contract"
        }
}
