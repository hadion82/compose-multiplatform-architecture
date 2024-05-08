package com.example.data.repository

interface ImageStreamRepository {
    suspend fun saveImage(url: String, dir: String, fileName: String)
}