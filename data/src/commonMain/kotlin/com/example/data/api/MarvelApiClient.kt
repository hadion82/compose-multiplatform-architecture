package com.example.data.api

import com.example.data.model.CharacterDataWrapper
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.request
import io.ktor.http.parameters
import me.tatarka.inject.annotations.Inject

@Inject
class MarvelApiClient (
    private val client: HttpClient
): MarvelApi {
    override suspend fun getCharacters(offset: Int, limit: Int): CharacterDataWrapper =
        client.get("/v1/public/characters") {

            url {
                with(parameters) {
                    append("offset", offset.toString())
                    append("limit", limit.toString())
                }
            }
        }.body()
}