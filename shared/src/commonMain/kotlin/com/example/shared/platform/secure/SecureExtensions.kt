package com.example.shared.platform.secure

expect object Secure {
    fun md5(source: String): String?
}