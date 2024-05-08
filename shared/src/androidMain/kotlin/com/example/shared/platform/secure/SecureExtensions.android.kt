package com.example.shared.platform.secure

import java.math.BigInteger
import java.security.MessageDigest

actual object Secure {
    actual fun md5(source: String)  = kotlin.runCatching {
        MessageDigest.getInstance("MD5").let { md ->
            md.update(source.toByteArray())
            BigInteger(1, md.digest()).toString(16)
                .padStart(32, '0')
        }
    }.getOrNull()
}