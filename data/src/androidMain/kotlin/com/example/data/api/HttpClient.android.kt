package com.example.data.api

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.Logging

actual val client: HttpClient
    get() = HttpClient(OkHttp) {
        install(HttpTimeout) { default() }
        install(Logging) {default() }
        install(ContentNegotiation) { default() }
        defaultRequest { default() }
    }.auth()