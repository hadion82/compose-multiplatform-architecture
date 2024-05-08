package com.example.shared.platform.media

expect interface Downloader {
    fun copyToFile(sourcePath: String, dir: String, fileName: String)
}

expect object MediaUtils