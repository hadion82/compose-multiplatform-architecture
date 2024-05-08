package com.example.data.api

import com.example.data.ApiKey
import com.example.shared.platform.secure.Secure
import io.ktor.client.HttpClient
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpSend
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.plugin
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import io.ktor.util.date.getTimeMillis
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json

expect val client: HttpClient

fun HttpTimeout.HttpTimeoutCapabilityConfiguration.default() {
    connectTimeoutMillis = 60_000
    socketTimeoutMillis = 60_000
    requestTimeoutMillis = 60_000
}

fun Logging.Config.default() {
    //Release Mode
    level = LogLevel.NONE
    //Debug Mode
    /*logger = Logger.DEFAULT
    level = LogLevel.ALL
    logger = object : Logger {
        override fun log(message: String) {
            Napier.d("[http] :\n$message")
        }
    }*/
}

@OptIn(ExperimentalSerializationApi::class)
fun ContentNegotiation.Config.default() {
    json(Json {
        prettyPrint = true
        isLenient = true
        ignoreUnknownKeys = true
        explicitNulls = false
    })
}

/**
 *
 */

fun HttpClient.auth(): HttpClient = apply {
    plugin(HttpSend).intercept { builder ->
        val urlBuilder = builder.url
        val timestamp = getTimeMillis().toString()
        val hashKey = timestamp + ApiKey.PRIVATE_KEY + ApiKey.PUBLIC_KEY
        urlBuilder.parameters.apply {
            append("ts", timestamp)
            append("apikey", ApiKey.PUBLIC_KEY)
            append("hash", Secure.md5(hashKey) ?: "")
        }
        execute(builder)
    }
}

fun DefaultRequest.DefaultRequestBuilder.default() {
    header("Content-Type", "application/json")
    url("https://gateway.marvel.com")
}