package com.orogersilva.myweatherforecast.testing.rest

import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.rules.ExternalResource

class RestInfrastructureRule : ExternalResource() {

    lateinit var server: MockWebServer

    override fun before() {

        super.before()

        server = MockWebServer()
        server.start()
    }

    override fun after() {

        server.shutdown()
        super.after()
    }

    fun restScenario(statusCode: Int, response: String = "") {

        server.enqueue(
            MockResponse().apply {
                setResponseCode(statusCode)
                setBody(response)
            }
        )
    }
}