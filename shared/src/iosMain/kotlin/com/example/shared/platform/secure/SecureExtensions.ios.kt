package com.example.shared.platform.secure

import com.ionspin.kotlin.bignum.integer.BigInteger
import com.ionspin.kotlin.bignum.integer.Sign
import org.kotlincrypto.hash.md.MD5

actual object Secure {
    actual fun md5(source: String): String? = kotlin.runCatching {
        val md = MD5().apply { update(source.encodeToByteArray()) }
        BigInteger.fromByteArray(md.digest(), Sign.POSITIVE)
            .toString(16)
            .padStart(32, '0')
    }.getOrNull()
}