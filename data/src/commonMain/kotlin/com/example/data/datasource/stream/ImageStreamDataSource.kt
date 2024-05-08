package com.example.data.datasource.stream

interface ImageStreamDataSource {
    suspend fun preload(url: String)
}