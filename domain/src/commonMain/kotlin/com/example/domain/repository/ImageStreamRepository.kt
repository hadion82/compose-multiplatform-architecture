package com.example.domain.repository

interface ImageStreamRepository {
    suspend fun saveImage(url: String, dir: String, fileName: String)
}
