package com.example.kmp.di.component

import com.example.data.api.MarvelApi
import com.example.data.api.MarvelApiClient
import com.example.data.api.client
import me.tatarka.inject.annotations.Provides

interface ApiComponent {
    @Provides
    fun httpClient() = client

    @Provides
    fun MarvelApiClient.bind(): MarvelApi = this
}