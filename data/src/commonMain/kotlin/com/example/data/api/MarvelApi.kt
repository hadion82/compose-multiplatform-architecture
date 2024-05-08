package com.example.data.api

import com.example.data.model.CharacterDataWrapper

interface MarvelApi {
    suspend fun getCharacters(
        offset: Int, limit: Int
    ): CharacterDataWrapper
}